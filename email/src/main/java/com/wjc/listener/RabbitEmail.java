package com.wjc.listener;

import com.wjc.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class RabbitEmail {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;


    @RabbitListener(queues = "queue_email")
    public void sendEmail(Email email){
        System.out.println("email = " + email);
        //创建一封邮箱
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //spring的邮箱包装工具
        MimeMessageHelper message = null;
        try {
            message = new MimeMessageHelper(mimeMessage,true);

            //发送者
            message.setFrom(from);
            message.setTo(email.getTo());//To普通的接收人，CC抄送人，BCC密送人
            //邮件标题
            message.setSubject(email.getSubject());
            //要http：//不然在邮箱里点不了
            message.setText(email.getText(),true);
            message.setSentDate(new Date());
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        javaMailSender.send(mimeMessage);
    }
}
