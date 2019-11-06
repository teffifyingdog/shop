package com.wjc.aop;

import com.wjc.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

@Aspect
@Component
public class LoginAop {
    @Autowired
    private RedisTemplate redisTemplate;

    //@annotation表示用了@islogin的方法都被这个增强
    @Around("@annotation(IsLogin)")
    public Object loginaop(ProceedingJoinPoint joinPoint){
        //获得request
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //从request获得cookie
        String loginToken=null;
        Cookie[] cookies = request.getCookies();
        //从cookie中获得token的值
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login_token")){
                    loginToken=cookie.getValue();
                    break;
                }
            }
        }

        //如果token存在，从redis中获得用户对象
        User user=null;
        if (loginToken!=null){
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }

        //如果用户不存在
        if (user==null){
            //获得方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获得方法的反射对象
            Method method = signature.getMethod();
            //获得方法上的注解
            IsLogin isLogin = method.getAnnotation(IsLogin.class);
            boolean flag = isLogin.mustLogin();
            //是否必须登陆，是的话返回登陆页面
            if (flag){
                //获得去登陆页面前的url
                String url = request.getRequestURL().toString();
                String queryString = request.getQueryString();
                if (queryString!=null){
                    url=url+"?"+queryString;
                }
                //编码
                try {
                    url= URLEncoder.encode(url,"utf-8");
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                //重定向到登录页
                return "redirect:http://192.168.252.1:60000/sso/toLogin?returnUrl="+url;
            }

        }
        //如果用户存在，替换当前方法中空的用户对象
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i]!=null&&args[i].getClass()==User.class){
                args[i]=user;
                break;
            }
        }

        //跟新参数
        Object result=null;
        try {
            result=joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
