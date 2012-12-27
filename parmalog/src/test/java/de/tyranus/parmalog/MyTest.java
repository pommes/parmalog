/*
 * Projekt: KiTa Gebühren
 * Autor: $Id$
 * Copyright (c): IT@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2012
 */
package de.tyranus.parmalog;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import de.tyranus.parmalog.config.ParmalogConfig;
import de.tyranus.parmalog.token.MailLogToken;

/**
 * @author tim
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ParmalogConfig.class }, loader = AnnotationConfigContextLoader.class)
public class MyTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyTest.class);
	
	@Test
	public void testTokens() {
		MailLogToken token = new MailLogToken();
		token.setRawData("very raw data");
		token.setTimestamp(new Date());
		LOGGER.info(token.toString());
				
	}
	
}
