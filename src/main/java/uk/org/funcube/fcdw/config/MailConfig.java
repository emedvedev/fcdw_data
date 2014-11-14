/*
	This file is part of the FUNcube Data Warehouse
	
	Copyright 2013,2014 (c) David A.Johnson, G4DPZ, AMSAT-UK

    The FUNcube Data Warehouse is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 2 of the License, or
    (at your option) any later version.

    The FUNcube Data Warehouse is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with The FUNcube Data Warehouse.  If not, see <http://www.gnu.org/licenses/>.
*/

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
