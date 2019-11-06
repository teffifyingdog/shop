$.ajax({
    type:"POST",
    url:"http://192.168.252.1:60000/sso/isLogin",
    success:function (data) {
        if (data.type=="0000"){
            $("#pid").html(data.data.username+"您好，欢迎来到<b>ShopCZ商城</b>  <a href='/sso/logout'>注销</a>");
        }else {
            $("#pid").html(
                "[<a onclick=\"mylogin();\">登录</a>]" +
                "[<a href=\"http://192.168.252.1:60000/sso/toRegister\">注册</a>]");
        }
    },
    dataType:"json"
});

function mylogin() {
    //获得地址栏的地址
    var returnUrl=location.href;
    //编码
    returnUrl=encodeURIComponent(returnUrl);
    //跳转到登陆页面
    location.href="http://192.168.252.1:60000/sso/toLogin?returnUrl=" + returnUrl;
}