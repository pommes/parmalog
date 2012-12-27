package de.tyranus.parmalog.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.tyranus.parmalog.Parmalog;
import de.tyranus.parmalog.ParmalogException;
import de.tyranus.parmalog.analyzer.MailLogAnalyzer;
import de.tyranus.parmalog.analyzer.MailLogGreylistAnalyzer;
import de.tyranus.parmalog.parser.MailLogFileSource;
import de.tyranus.parmalog.parser.MailLogParser;
import de.tyranus.parmalog.parser.MailLogPostfixParser;
import de.tyranus.parmalog.parser.MailLogSource;
import de.tyranus.parmalog.parser.ParseDayFilter;
import de.tyranus.parmalog.parser.ParseFilter;

/**
 * @author tim
 * 
 */
@Configuration
public class ParmalogConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParmalogConfig.class);

	@Bean
	public SimpleDateFormat logfileDateFormat() {
		return new SimpleDateFormat("MMM d HH:mm:ss");
	}

	@Bean
	public MailLogSource<? extends BufferedReader> mailLogSource() {
		final String filename = ClassLoader.getSystemResource("mail.log").getFile();
		LOGGER.debug("*** {}", filename);
		try {
			return new MailLogFileSource(new File(filename));
		}
		catch (FileNotFoundException e) {
			throw ParmalogException.createFileNotFound(e, filename);
		}
	}

	@Bean
	public Parmalog parmalog() {
		return new Parmalog();
	}

	@Bean
	public ParseFilter parseFilter() {
		return new ParseDayFilter();
	}

	@Bean
	public List<ParseFilter> parseFilters() {
		List<ParseFilter> list = new ArrayList<ParseFilter>();
		list.add(new ParseDayFilter());
		return list;
	}

	@Bean
	public MailLogParser mailLogParser() {
		return new MailLogPostfixParser();
	}

	@Bean
	public MailLogAnalyzer mailLogAnalyzer() {
		return new MailLogGreylistAnalyzer();
	}

}