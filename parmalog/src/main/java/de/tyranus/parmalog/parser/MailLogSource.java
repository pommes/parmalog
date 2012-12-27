package de.tyranus.parmalog.parser;

import java.io.BufferedReader;

import org.springframework.stereotype.Component;

/**
 * A mail log source is the reference to the log.
 * 
 * @author tim
 * 
 */
@Component
public interface MailLogSource<T extends BufferedReader> {

	/**
	 * Returns the reader.
	 * 
	 * @return the reader.
	 */
	T logReader();
}
