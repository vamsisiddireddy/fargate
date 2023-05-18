package com.motivity.hospital.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class EmailNotificationService {
	@Autowired
	JavaMailSender javaMailSender;
	boolean html =false;
	public void sendEmail(String to, String subject, String text) {
//		SimpleMailMessage message = new SimpleMailMessage();
//    	message.setFrom("motivityhospital@gmail.com");
//    	message.setTo(to);
//    	message.setSubject(subject);
//    	message.setText(text);
//    	System.out.println("----------");
//    	javaMailSender.send(message);
//    	System.out.println("email send");
		try{
            MimeMessage mail=javaMailSender.createMimeMessage();
            MimeMessageHelper message=new MimeMessageHelper(mail);
            html=true;
            message.setFrom("motivityhospital@gmail.com","OCS-java trainers");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text,html);
            message.setCc("veera.rangina@motivitylabs.com");
            javaMailSender.send(mail);
        }
        catch(Exception e){
            
        }    
	}
}
