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
		
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("dave@g4dpz.me.uk");
		mailSender.setPassword("H4les0wen");
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		mailSender.setJavaMailProperties(properties);
		
		return mailSender;
	}
	
	@Bean
	SimpleMailMessage alertMailMessage() {
		SimpleMailMessage alertMailMessage = new SimpleMailMessage();
		
		alertMailMessage.setFrom("dave@g4dpz.me.uk");
		alertMailMessage.setTo("dave@g4dpz.me.uk");
		alertMailMessage.setSubject("Alert - Exception occurred. Please investigate");
		
		return alertMailMessage;
	}
	
	@Bean
	VelocityEngineFactoryBean velocityEngine() {
		VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();
		
		velocityEngine.setResourceLoaderPath("/WEB-INF/velocity/");
		
		Properties velocityProperties = new Properties();
		velocityProperties.put("input.encoding", "utf-8");
		velocityProperties.put("output.encoding", "utf-8");
		
		velocityEngine.setVelocityProperties(velocityProperties);
		
		return velocityEngine;
	}

}
