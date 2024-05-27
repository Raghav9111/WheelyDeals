package com.wheelyDeals.services;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService 
{
	private JavaMailSenderImpl mailSender;
	private Properties props;
	
	public MailService() 
	{
		mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);	    
	    mailSender.setUsername("priyanshumandlik444@gmail.com");
	    mailSender.setPassword("brdgqnccvspudprw");
	    
	    props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	}
	
	public void verificationMail(String mail,String name) throws Exception 
	{
			MimeMessage mimeMessage = mailSender.createMimeMessage();
		
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			String htmlMsg = 
					"<html>"
	                + "<head>"
	                + "<style>"
	                + "body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; text-align: center; margin: 0; padding: 0; }"
	                + "h1 { color: #d9534f; }"
	                + "hr { border-color: #d9534f; margin-top: 20px; }"
	                + "p { color: #333; margin: 20px 0; }"
	                + "a { display: inline-block; padding: 10px 20px; background-color: #d9534f; color: #fff; text-decoration: none; border-radius: 5px; }"
	                + "</style>"
	                + "</head>"
	                + "<body>"
	                + "<h1>Welcome, " + name + "!</h1>"
	                + "<hr>"
	                + "<p>Your account is created successfully. Please verify your account via the link below:</p>"
	                + "<a href='http://localhost:8080/web/verify/" + mail + "'>Verify Account</a>"
	                + "</body>"
	                + "</html>";
			
			mimeMessage.setContent(htmlMsg, "text/html"); 
			helper.setText(htmlMsg, true); 
			helper.setTo(mail);
			helper.setSubject("Verification Mail");
			helper.setFrom("priyanshumandlik444@gmail.com");
			mailSender.send(mimeMessage);
	}
}