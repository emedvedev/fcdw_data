// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service("mailService")
public class MailService {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private SimpleMailMessage alertMailMessage;
	@Autowired
	VelocityEngine velocityEngine;

	public void sendMail(String from, String to, String subject, String body) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);

	}

	public void sendAlertMail(String alert) {

		SimpleMailMessage mailMessage = new SimpleMailMessage(alertMailMessage);
		mailMessage.setText(alert);
		mailSender.send(mailMessage);

	}

	public void sendUsingTemplate(String toAddress, Map<String, Object> emailTags, String template) {

		String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template + ".vm", "utf-8", emailTags);

		sendMail("dave@g4dpz.me.uk", toAddress, "FUNcube One Orbit No Show", body);

	}
}
