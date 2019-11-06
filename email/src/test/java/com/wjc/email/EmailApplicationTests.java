package com.wjc.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@SpringBootTest
class EmailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void contextLoads() throws MessagingException {
        //创建一封邮箱
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //spring的邮箱包装工具
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);

        //邮件标题
        message.setSubject("title");
        message.setFrom("2860019549@qq.com");//发送者
        message.setTo("2860019549@qq.com");//To普通的接收人，CC抄送人，BCC密送人
        //要http：//不然在邮箱里点不了
        message.setText("123654789<a href=\"http://www.aliyun.com\">阿里巴巴</a>",true);
        message.setSentDate(new Date());
        message.addAttachment("???.jpg", new File("D:\\DESK\\picture\\computer\\wallhaven-2e3e39_1920x1080.png"));

        javaMailSender.send(mimeMessage);
    }

}
