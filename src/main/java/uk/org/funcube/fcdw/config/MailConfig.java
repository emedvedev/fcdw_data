// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
public class MailConfig {
	
	@Bean
	JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.googlemail.com");
		mailSender.setPort(465);
		mailSender.setUsername("operations@funcube.net");
		mailSender.setPassword("H4les0wen1234");
		
		Properties mailProperties = new Properties();
		mailProperties.put("mail.transport.protocol", "smtp");
		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.debug", "true");
		
		mailSender.setJavaMailProperties(mailProperties);
		
		return mailSender;
	}
	
	/*
	 *  <!-- Use SMTP transport protocol -->
                <prop key="mail.transport.protocol">smtp</prop>
                <!-- Use SMTP-AUTH to authenticate to SMTP server -->
                <prop key="mail.smtp.auth">true</prop>
                <!-- Use TLS to encrypt communication with SMTP server -->
                <prop key="mail.smtp.starttls.enable">true</prop>
                <prop key="mail.debug">true</prop>
	 */
	
	@Bean
	VelocityEngineFactoryBean velocityEngine() {
		VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean();
		
		velocityEngineFactoryBean.setResourceLoaderPath("/WEB-INF/velocity/");
		
		Properties velocityProperties = new Properties();
		velocityProperties.put("input.encoding", "utf-8");
		velocityProperties.put("output.encoding", "utf-8");
		velocityEngineFactoryBean.setVelocityProperties(velocityProperties);
		
		return velocityEngineFactoryBean;
	}
	
	@Bean
	SimpleMailMessage alertMailMessage() {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("dave@g4dpz.me.uk");
		mailMessage.setTo("dave@g4dpz.me.uk");
		mailMessage.setSubject("Alert - Exception occurred. Please investigate");
		return mailMessage;
	}

}
