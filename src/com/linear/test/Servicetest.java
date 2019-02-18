package com.linear.test;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.junit.Test;

import com.linear.utils.SendMail;

public class Servicetest {
	@Test
	public void mailtest() {
		int checknum = Math.abs(new Random().nextInt(999999));
		SendMail sendMail = new SendMail();
		sendMail.setTo("ren_rzj@126.com");//
		System.out.println(checknum);
		sendMail.setBody("[线性代数学习]验证码:" + checknum);
		try {
			sendMail.mailsend();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void valiadmail() {
		String p = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
		Pattern pattern = Pattern.compile(p);
		String mailstr = "ren_rzj_ren@163.com";
		Matcher matcher = pattern.matcher(mailstr);
		System.out.println(matcher.matches());
	}
}
