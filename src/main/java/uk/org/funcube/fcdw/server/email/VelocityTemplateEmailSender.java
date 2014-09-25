// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.email;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class VelocityTemplateEmailSender implements TemplateEmailSender {

	private static final String EMAIL_CONTENT_ENCODING = "UTF-8";

	private static final Logger LOGGER = Logger.getLogger(VelocityTemplateEmailSender.class);

	private final VelocityEngine velocityEngine;
	private final JavaMailSender mailSender;

	public VelocityTemplateEmailSender(final VelocityEngine velocityEngine, final JavaMailSender mailSender) {
		this.velocityEngine = velocityEngine;
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmailUsingTemplate(final String fromAddress, final String toAddress, final String[] bccAddresses, final String subject,
			final String templateLocation, final Map<String, Object> model) {

		final Map<String, Object> augmentedModel = new HashMap<String, Object>(model);
		augmentedModel.put("dateTool", new DateTool());
		augmentedModel.put("numberTool", new NumberTool());
		augmentedModel.put("mathTool", new MathTool());

		final Writer writer = new StringWriter();
		VelocityEngineUtils.mergeTemplate(velocityEngine, templateLocation, augmentedModel, writer);
		final String emailBody = writer.toString();

		final MimeMessagePreparator prep = new MimeMessagePreparator() {
			@Override
			public void prepare(final MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, EMAIL_CONTENT_ENCODING);
				message.setTo(toAddress);
				message.setFrom(fromAddress);
				message.setSubject(subject);
				message.setText(emailBody);

				if (!ArrayUtils.isEmpty(bccAddresses)) {
					message.setBcc(bccAddresses);
				}
			}
		};

		try {
			mailSender.send(prep);
			LOGGER.debug(String.format("Sent %3$s email To: %1$s, Bcc: %2$s", toAddress, ArrayUtils.toString(bccAddresses, "None"),
					templateLocation));
		} catch (final MailException e) {
			LOGGER.error("Could not send email " + subject, e);
			throw e;
		}
	}

}
