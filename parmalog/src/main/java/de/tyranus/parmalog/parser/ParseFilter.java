package de.tyranus.parmalog.parser;

import org.springframework.stereotype.Component;

/**
 * The parse filter can be used let the {@link MailLogParser} only parse a part of the log file and
 * not the whole log file. As an example it can be used to only parse one day of the log.
 * 
 * @author tim
 * 
 */
@Component
public interface ParseFilter {

}
