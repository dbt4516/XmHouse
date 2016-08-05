package com.cnc.xmhouse.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Created by zhuangjy on 2016/8/1.
 */
@Service
public class MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 发送邮件
     * @param subject 邮件主题
     * @param text 内容
     * @param target 邮件接受者
     * @param from 发件人（填写null，则不显示发件人)
     */
    public void sendEmails(String subject, String text,List<String> target,String from) {
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper smm = new MimeMessageHelper(mailMessage, true);
            smm.setFrom(mailSender.getUsername());
            smm.setSubject(subject);
            if(StringUtils.isNotEmpty(from))
                text = "<p>发件人:<span style=\"color:red\"> " + from + "</span></p>\n" + text;
            smm.setText(text, true);
            String[] receives = target.stream().toArray(String[]::new);
            smm.setTo(receives);
            new Thread(() -> mailSender.send(mailMessage)).start();
        } catch (MessagingException e) {
            LOGGER.error("发送邮件失败", e);
        }
    }



}
