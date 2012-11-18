package de.tyranus.parmalog.analyzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.tyranus.parmalog.parser.MailLogParser;
import de.tyranus.parmalog.parser.ParseFilter;
import de.tyranus.parmalog.token.MailLogToken;
import de.tyranus.parmalog.token.MailLogTokens;
import de.tyranus.parmalog.token.MailLogToken.BodyKind;
import de.tyranus.parmalog.token.postfix.NoQueueEvent;
import de.tyranus.parmalog.token.postfix.PostfixSmtpdTokenBody;
import de.tyranus.parmalog.token.postfix.NoQueueEvent.Reason;
import de.tyranus.parmalog.token.postfix.NoQueueEvent.RejectReason;
import de.tyranus.parmalog.token.postfix.PostfixSmtpdTokenBody.EventKind;

/**
 * @author tim
 * 
 */
public class MailLogGreylistAnalyzer implements MailLogAnalyzer {
	private final static Logger LOGGER = LoggerFactory.getLogger(MailLogGreylistAnalyzer.class);

	@Autowired
	MailLogParser mailLogParser;

	@Autowired
	List<ParseFilter> parseFilters;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tyranus.parmalog.analyzer.MailLogAnalyzer#analyze()
	 */
	public void analyze() {
		final List<String> fromToList = new ArrayList<>();
		// Go through all tokens...
		final MailLogTokens tokens = mailLogParser.parse(parseFilters);
		for (MailLogToken token : tokens) {
			// ... and look for PostfixSmtp tokens...
			if (token.getBodyKind() == BodyKind.PostfixSmtpd) {
				final PostfixSmtpdTokenBody body = token.getBody(PostfixSmtpdTokenBody.class);
				// and if they have NoQueue events ...
				if (body.getEventKind() == EventKind.NoQueue) {
					final NoQueueEvent event = body.getEvent(NoQueueEvent.class);
					// and if they are Rejected because of Greylisting
					if (event.getReason() == Reason.Reject && event.getRejectReason() == RejectReason.Greylisted) {

						// Zeige alle Greylist-Einträge, bei denen nicht später Einträge kommen, die
						// durchgehen.
						if (!fromToList.contains(event.getFrom() + event.getTo())
								&& !isPassEntryAfter(token.getTimestamp(), event.getFrom(), event.getTo(), tokens)) {
							LOGGER.info("Greylistet FROM={}, TO={}", event.getFrom(), event.getTo());
							fromToList.add(event.getFrom() + event.getTo());
						}

					}
				}
			}

		}
	}

	/**
	 * @param timestamp
	 * @param from
	 * @param to
	 * @param tokens
	 * @return
	 */
	private boolean isPassEntryAfter(Date timestamp, String from, String to, MailLogTokens tokens) {
		for (MailLogToken token : tokens) {
			if (token.getTimestamp().compareTo(timestamp) > 0
					&& token.getRawData().contains(from + "> -> <" + to + ">") && token.getRawData().contains("amavis")
					&& token.getRawData().contains("Passed")) {
				return true;
			}
		}
		return false;
	}

}
