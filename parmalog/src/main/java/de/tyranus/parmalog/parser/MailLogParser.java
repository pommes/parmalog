package de.tyranus.parmalog.parser;

import java.util.List;

import de.tyranus.parmalog.token.MailLogTokens;

/**
 * The {@link MailLogParser} parses the mail log file for the specified issues.
 * 
 * @author tim
 * 
 */
public interface MailLogParser {
	/**
	 * Parses the log file and returns {@link MailLogTokens}.
	 * 
	 * @param filters
	 *            list of {@link ParseFilter} for filtering the mail log.
	 * @return parsed {@link MailLogTokens}.
	 */
	MailLogTokens parse(List<ParseFilter> filters);
}
