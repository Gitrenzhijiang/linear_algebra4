package com.linear.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;


public class SendMail {
	private  String host = "smtp.126.com";//主机
	private  String uname = "ren_rzj";
	private  String psw = "19980710rzj";
	private  String from = "ren_rzj@126.com";
	private  String to = "";
	private  String subject = "";
	private  String body = "";
	
	public  String getBody(){
		return body;
	}
	public  void setBody(String _body){
		body = _body;
	}
	
	
	 public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}


	{
		//从配置文件中加载
		Properties p = new Properties();
		try {
			
			p.load(SendMail.class.getResourceAsStream("/mail.properties"));
			host = p.getProperty("host");
			uname = p.getProperty("uname");
			psw = p.getProperty("psw");
			from = p.getProperty("from");
			to = p.getProperty("to");
			subject = p.getProperty("subject");
			body = p.getProperty("body");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	//发送邮件
	public void mailsend() throws MessagingException 
	{
		//1.得到session
		Properties props = new Properties();
		props.setProperty("mail.host",host);//设置主机
		props.setProperty("mail.smtp.auth", "true");
			//账号密码
		Authenticator auth = new Authenticator() {
			
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(uname,psw);
			}

		};
		
		Session session = Session.getInstance(props, auth);
		
		//2.创建MimeMessage
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(RecipientType.TO, to);//收件人
		
		msg.setSubject(subject);
		msg.setContent(body, "text/html;charset=utf-8");
		
		//3.发
		Transport.send(msg);
	}
}

