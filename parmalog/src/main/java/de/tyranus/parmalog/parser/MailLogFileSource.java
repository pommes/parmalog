package de.tyranus.parmalog.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * A special {@link MailLogSource} that uses a file as source.
 * 
 * @author tim
 * 
 */
public class MailLogFileSource implements MailLogSource<BufferedReader> {

	private BufferedReader reader;

	/**
	 * Creates a new instance.
	 * 
	 * @throws FileNotFoundException
	 *             if the passed file does not exist.
	 */
	public MailLogFileSource(File file) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(file));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tyranus.parmalog.parser.MailLogSource#logReader()
	 */
	public BufferedReader logReader() {
		return reader;
	}

}
