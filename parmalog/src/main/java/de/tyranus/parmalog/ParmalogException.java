/*
 * Projekt: KiTa Gebühren
 * Autor: $Id$
 * Copyright (c): IT@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2012
 */
package de.tyranus.parmalog;

import java.text.SimpleDateFormat;

/**
 * @author tim
 * 
 */
public class ParmalogException extends RuntimeException {
	private static final long serialVersionUID = -4583071390404044100L;

	public static final int REASON_UNDEFINED = 1001;
	public static final int REASON_BEAN_CREATION = 1002;
	public static final int REASON_FILE_NOT_FOUND = 1101;
	public static final int REASON_PARSE_ERROR = 1102;
	public static final int REASON_WRONG_DATE_FORMAT = 1103;

	private int _reason;

	/**
	 * @param arg0
	 * @param arg1
	 */
	private ParmalogException(int reason, String arg0, Throwable arg1) {
		super(arg0, arg1);
		_reason = reason;
	}

	/**
	 * @param arg0
	 */
	private ParmalogException(int reason, String arg0) {
		super(arg0);
		_reason = reason;
	}

	/**
	 * @param arg0
	 */
	private ParmalogException(int reason, Throwable arg0) {
		super(arg0);
		_reason = reason;
	}

	public int getReason() {
		return _reason;
	}

	/**
	 * Unable to to open file '%s'
	 * 
	 * @param t
	 *            The cause
	 * @param filename
	 *            The filename that could not be opened
	 * @return the exception
	 */
	public static ParmalogException createFileNotFound(Throwable t, String filename) {
		return new ParmalogException(REASON_FILE_NOT_FOUND, String.format(
				"Unable to to open file '%s'", filename), t);
	}

	/**
	 * Unable to parse logfile. Last successful line was '%s'
	 * 
	 * @param t
	 *            The cause
	 * @param line
	 *            The last line that could be parsed.
	 * @return the exception
	 */
	public static ParmalogException createParse(Throwable t, String line) {
		return new ParmalogException(REASON_PARSE_ERROR, String.format(
				"Unable to parse logfile. Last successful line was '%s'", line), t);
	}

	/**
	 * The configured date format '%s' does not match the date format in the logfile.
	 * 
	 * @param t
	 *            The cause
	 * @param logfileDateFormat
	 *            The date format object.
	 */
	public static ParmalogException createWrongDateFormat(Throwable t,
			SimpleDateFormat logfileDateFormat) {
		return new ParmalogException(REASON_WRONG_DATE_FORMAT, String.format(
				"The configured date format '%s' does not match the date format in the logfile.",
				logfileDateFormat.toPattern()), t);
	}
}
