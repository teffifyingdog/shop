package com.wjc.controller;

import com.wjc.entity.Email;
import com.wjc.entity.ResultData;
import com.wjc.entity.User;
import com.wjc.service.ISsoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/sso")
public class SsoController {
    @Autowired
    private ISsoService ssoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;




    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/register")
    public String register(User user, Model model){
        //创建返回对象
        String error = "";

        //判断用户名是否存在,如果存在返回注册页面
        if (ssoService.checkUsername(user.getUsername())){
            error="用户名已存在";
            model.addAttribute("error",error);
            return "register";
        }

        //判断是否注册成功
        if (ssoService.register(user)){
            error="注册成功";
            model.addAttribute("error",error);
            return "login";
        }else {
            error="注册失败";
            model.addAttribute("error",error);
            return "register";
        }

    }

    @RequestMapping("/login")
    public String login(HttpServletResponse response,String username,String password,String returnUrl,Model model,@CookieValue(value = "carToken",required = false) String carToken){
        //判断用户名和密码是否为空
        if (username==null||username==""||password==null||password==""){
            return "login";
        }

        //判断返回的url
        if (returnUrl==null||returnUrl==""){
            returnUrl="http://192.168.252.1:60000/";
        }

        //判断用户是否存在
        User user=ssoService.login(username,password);

        if (user!=null){
            //将用户存入redis7天
            String token=UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(token,user);
            redisTemplate.expire(token,7,TimeUnit.DAYS);

            //将uuid写入cookie7天
            Cookie login_token = new Cookie("login_token", token);
            login_token.setMaxAge(604800);
            login_token.setPath("/");
            response.addCookie(login_token);

            //如果cookie中有购物车，则合并购物车
            if (carToken!=null||carToken!=""){
                try {
                    System.out.println("redirect:http://192.168.252.1:60000/car/merge?returnUrl="+ URLEncoder.encode(returnUrl,"utf-8"));
                    return "redirect:http://192.168.252.1:60000/car/merge?returnUrl="+ URLEncoder.encode(returnUrl,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            //跳转页面
            return "redirect:"+returnUrl;
        }

        return "login";
    }

    @RequestMapping("/isLogin")
    @ResponseBody
    public ResultData<User> isLogin(@CookieValue(value = "login_token",required = false)String token, HttpServletResponse response){
        //设置响应头，允许跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");

        //判断是否登陆
        if (token!=null){
            //从redis中获得用户对象
            User user = (User) redisTemplate.opsForValue().get(token);

            //如果登陆返回信息
            if (user!=null){
                return new ResultData<User>().setType("0000").setContext("已登录").setData(user);
            }
        }
        return new ResultData<User>().setType("2001").setContext("未登录");
    }

    @RequestMapping("/logout")
    public String logout(@CookieValue(value = "login_token",required = false)String token, HttpServletResponse response){
        //如果token不为空
        if (token!=null){
            //删除redis中的用户信息
            redisTemplate.delete(token);
            //删除cookie
            Cookie login_token = new Cookie("login_token", token);
            login_token.setMaxAge(0);
            login_token.setPath("/");
            response.addCookie(login_token);
        }
        return "login";
    }

    @RequestMapping("/toForgetPassword")
    public String toForgotPassword(){
        return "forgetpassword";
    }

    @RequestMapping("/findPassword")
    @ResponseBody
    public ResultData findPassword(String username){
        System.out.println("username = " + username);

        //通过用户名找到邮箱,如果没找到返回错误信息
        String email="";
        email=ssoService.getEmailByUsername(username);
        if (email==null||email==""){
            ResultData<Object> resultData = new ResultData<>();
            return resultData.setContext("用户不存在").setType("1000");
        }

        // 发送找回密码的邮件
        //创建随机的uuid
        String token=UUID.randomUUID().toString();
        //将用户名和uuid存入redis
        stringRedisTemplate.opsForValue().set(token,username);
        stringRedisTemplate.expire(token,3, TimeUnit.MINUTES);
        //发送邮件
        String url="http://192.168.252.1:60000/sso/toUpdatePassword?token="+token;
        String content="点击链接重置密码<a href="+url+">"+url+"</a>";
        Email mail = new Email().setSubject("密码找回").setTo(email)
                .setText(content);
        System.out.println("mail = " + mail);
        rabbitTemplate.convertAndSend("exchange_email","",mail);

        //将用户的邮箱和应该登陆的那个邮箱的地址返回
        Map<String,String> data = new HashMap<>();
        data.put("emailInfo",email.replace(email.substring(3,email.indexOf("@")),"******"));
        data.put("emailUrl","http://mail."+email.substring(email.indexOf("@")+1));
        return new ResultData().setType("0000").setContext("邮件发送成功").setData(data);
    }

    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(){
        return "updatepassword";
    }

    @RequestMapping("/updatePassword")
    public String updatePassword(String newpassword,String token){
        String username = stringRedisTemplate.opsForValue().get(token);
        if (username==null||username==""){
            return null;
        }
        Integer res=ssoService.updatePassword(username,newpassword);
        if (res==1){
            stringRedisTemplate.delete(token);
            return "login";
        }else {
            System.out.println("shit");
        }
        return null;
    }
}
