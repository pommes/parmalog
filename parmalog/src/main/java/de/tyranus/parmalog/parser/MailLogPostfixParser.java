package de.tyranus.parmalog.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.tyranus.parmalog.ParmalogException;
import de.tyranus.parmalog.token.MailLogToken;
import de.tyranus.parmalog.token.MailLogToken.BodyKind;
import de.tyranus.parmalog.token.MailLogTokens;
import de.tyranus.parmalog.token.postfix.NoQueueEvent;
import de.tyranus.parmalog.token.postfix.PostfixSmtpdTokenBody;
import de.tyranus.parmalog.token.postfix.NoQueueEvent.Reason;
import de.tyranus.parmalog.token.postfix.NoQueueEvent.RejectReason;
import de.tyranus.parmalog.token.postfix.PostfixSmtpdTokenBody.EventKind;

/**
 * Postfix implementation of the {@link MailLogParser}.
 * 
 * @author tim
 * 
 */
public class MailLogPostfixParser implements MailLogParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailLogPostfixParser.class);

	@Autowired
	private MailLogSource<? extends BufferedReader> mailLogSource;

	@Autowired
	private SimpleDateFormat logfileDateFormat;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tyranus.parmalog.parser.MailLogParser#parse(de.tyranus.parmalog.parser.ParseFilter[])
	 */
	public MailLogTokens parse(List<ParseFilter> filters) {
		String lastLine = "";
		final MailLogTokens tokens = new MailLogTokens();
		try {
			while (mailLogSource.logReader().ready()) {
				final String line = mailLogSource.logReader().readLine();

				final MailLogToken token = parseHead(line);
				parseBody(token);
				tokens.add(token);

				lastLine = line;
			}
		}
		catch (IOException e) {
			throw ParmalogException.createParse(e, lastLine);
		}

		return tokens;
	}

	/**
	 * @return
	 */
	private MailLogToken parseBody(MailLogToken token) {
		switch (token.getBodyKind()) {
		case PostfixSmtpd:
			parseBodyPostfixSmtpd(token);
			break;
		default:
			LOGGER.debug("UNSUPPORTED BODY KIND: {}", token.getBodyKind());
		}
		return token;
	}

	/**
	 * @param token
	 */
	private MailLogToken parseBodyPostfixSmtpd(MailLogToken token) {
		final PostfixSmtpdTokenBody body = token.getBody(PostfixSmtpdTokenBody.class);
		final int posEventStart = token.getRawData().indexOf("]:") + 2;
		final String strEventLine = token.getRawData().substring(posEventStart);
		final String strEvent = strEventLine.trim().split(" ")[0].toUpperCase();

		switch (strEvent) {
		case EventKind.EVENT_NOQUEUE:
			body.setEventKind(EventKind.NoQueue);
			body.setEventType(NoQueueEvent.class);
			body.setEvent(new NoQueueEvent());
			parseNoQueueEvent(token);
			break;
		default:
			LOGGER.debug("UNSUPPORTED EVENT KIND: {}", body.getEventKind());
		}

		return token;
	}

	/**
	 * @param token
	 */
	private MailLogToken parseNoQueueEvent(MailLogToken token) {
		final NoQueueEvent event = token.getBody(PostfixSmtpdTokenBody.class).getEvent(
				NoQueueEvent.class);
		final int posStart = token.getRawData().indexOf(EventKind.EVENT_NOQUEUE)
				+ EventKind.EVENT_NOQUEUE.length();
		final String strLine = token.getRawData().substring(posStart);
		final String reason = strLine.split(":")[0].trim().toUpperCase();

		// Reason
		switch (reason) {
		case "REJECT":
			event.setReason(Reason.Reject);
			break;
		default:
			event.setReason(Reason.Unsupported);
			LOGGER.debug("UNSUPPORTED REASON: {}", strLine);
		}

		// RejectReason
		if (strLine.contains("Greylisted")) {
			event.setRejectReason(RejectReason.Greylisted);
		}
		else if (strLine.toUpperCase().contains("SPAM")) {
			event.setRejectReason(RejectReason.Spam);
		}
		else {
			event.setRejectReason(RejectReason.Unsupported);
			LOGGER.debug("UNSUPPORTED REJECT REASON: {}", strLine);
		}

		// From
		final String strFrom = strLine.split("from=<")[1];
		event.setFrom(strFrom.split(">")[0]);

		// To
		final String strTo = strLine.split("to=<")[1];
		event.setTo(strTo.split(">")[0]);

		return token;
	}

	/**
	 * @return
	 */
	private MailLogToken parseHead(String line) {
		final MailLogToken token = new MailLogToken();
		final int posDateTimeEnd = logfileDateFormat.toPattern().length() + 1;

		// RawData
		token.setRawData(line);

		// Timestamp
		final String strTimestamp = line.substring(0, posDateTimeEnd);
		try {
			token.setTimestamp(logfileDateFormat.parse(strTimestamp));
		}
		catch (ParseException e) {
			throw ParmalogException.createWrongDateFormat(e, logfileDateFormat);
		}

		// Hostname
		final String parts[] = line.substring(posDateTimeEnd + 1, line.length() - 1).split(" ");
		token.setHostname(parts[0]);

		// Body Kind
		// BodyType
		// Body
		final String daemonParts[] = parts[1].split("\\[");
		final String strDaemon = daemonParts[0].toUpperCase();

		switch (strDaemon) {
		case BodyKind.POSTFIX_SMTPD:
			token.setBodyKind(BodyKind.PostfixSmtpd);
			token.setBodyType(PostfixSmtpdTokenBody.class);
			token.setBody(new PostfixSmtpdTokenBody());
			break;
		default:
			token.setBodyKind(BodyKind.Unsupported);
			LOGGER.debug("UNSUPPORTED BODY KIND: {}", strDaemon);
			return token;
		}

		// ProcessId
		final String strProcessId = (daemonParts[1].split("\\]"))[0];
		token.setProcessId(Integer.parseInt(strProcessId));

		return token;
	}

}
