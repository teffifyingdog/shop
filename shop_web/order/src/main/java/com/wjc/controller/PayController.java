package com.wjc.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.wjc.entity.Orders;
import com.wjc.service.IOrderService;
import com.wjc.util.AliPayUilt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/alipay")
    public void alipay(HttpServletResponse response,String oid,String title){
        Orders order = orderService.getOrderByOid(oid);
        //通过公共方法获得支付宝的核心
        AlipayClient alipayClient = AliPayUilt.getAliPayClient();
        //支付接口
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://192.168.252.1:60000");//支付完成后跳转的页面
        alipayRequest.setNotifyUrl("http://zni6td.natappfree.cc/pay/getback?oid="+oid);//支付成功后，异步通知商家支付结果
        alipayRequest.setBizContent("{" +
                //必选，订单号
                "    \"out_trade_no\":\""+oid+"\"," +
                //必选，商品码，现在只能写这个
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                //必选，订单总金额，0.01-1亿
                "    \"total_amount\":"+ order.getTotalPrice() +"," +
                //必选，订单标题
                "    \"subject\":\""+title+"\"," +
                //可选，订单详情
                "    \"body\":\"Iphone6 16G\"," +
                //可选，回传参数，需要经过编码
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        try {
            response.setContentType("text/html;charset=" + "utf-8");
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getback")
    @ResponseBody
    public void getBack(HttpServletRequest request,String sign_type,String oid){
        // TODO 获得支付结果集合和订单id
        Map<String, String[]> parameterMap = request.getParameterMap();
        // TODO 将结果转化成 Map<String,String>类型
        Map<String,String> map =new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            map.put(entry.getKey(),entry.getValue()[0]);
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey()+","+entry.getValue());
        }
        // TODO 移除自带的商品id
        map.remove("oid");
        try {
            //调用SDK验证是否为支付宝请求
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    //所有参数
                    map,
                    //支付宝公钥
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArTjjMHUaUWby9XGCcpyRFXJ9g6IiVL6Vh2yLCXv5MMiJnu1LgAUxmEax5W7c+ntShRtMlwotOIsAemRnuNUaxTbF8cRwnoe75va5bC71przQSCp+/42oOVzEtdyj/xcdkE5blTVrUbg9zdyBfvDhD16sc/sc5o9qw6c4N/Ga2eGUWEkCp39sLqXZtQg+NkQ1RXnTy8tirFoAQil0nxI1x0/ZGOGt9PjzKQ7am5fojR7HFdAq9cBNA80y23YYs7KYZ12A/NXObKUstoi5ksqa7qxPpoBvc7u0qLzJrj9vPTKA86+3i2PicoGtDPCjnJ/9Cny4vBChzuqp8tyFQnnWtwIDAQAB",
                    //字符编码
                    "utf-8",
                    //调用SDK验证签名
                    sign_type
            ) ;
            if(signVerified){
                // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
                System.out.println("true");
                int res=orderService.modifyStatus(oid);
                if (res==1) {
                    System.out.println("oid 已支付= " + oid);
                }
            }else{
                // TODO 验签失败则记录异常日志，并在response中返回failure.
                System.out.println("false");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/toreturn")
    public String toreturn(String oid, Model model) {
        int res=orderService.modifyStatusByOid(oid,7);
        if (res==1) {
            model.addAttribute("mes", "退款");
            return "pay";
        }
        else
            return "error";
    }

    @RequestMapping("/return")
    @ResponseBody
    public String returnMoney(@RequestParam("oid") String oid, @RequestParam("totalPrice") BigDecimal totalPrice) {
        //通过公共方法获得支付宝核心
        AlipayClient alipayClient =AliPayUilt.getAliPayClient();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        //设置发送退款的信息
        request.setBizContent("{" +
                //特殊可选，订单id和交易id必须至少有一个
                "    \"out_trade_no\":\""+oid+"\"," +
                //特殊可选，交易id
                //"    \"trade_no\":\"2014112611001004680073956707\"," +
                //必选，退款金额
                "    \"refund_amount\":"+totalPrice+"," +
                //可选，退款原因
                "    \"refund_reason\":\"正常退款\"," +
                //特殊可选，标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
                "    \"out_request_no\":\"HZ01RF001\"," +
                //可选，商户的操作员编号
                "    \"operator_id\":\"OP001\"," +
                //可选，商户门店编号
                "    \"store_id\":\"NJ_S_001\"," +
                //可选，商户的终端编号
                "    \"terminal_id\":\"NJ_T_001\"" +
                "  }");
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()) {
            System.out.println("调用成功");
            int i = orderService.modifyStatusByOid(oid, 8);
            if (i == 1){
                System.out.println("ok");
                return "1";
            }else {
                System.out.println("not ok");
                return "0";
            }
        } else {
            System.out.println("调用失败");
            return "0";
        }
    }
}
