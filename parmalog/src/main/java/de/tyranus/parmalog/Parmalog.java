package de.tyranus.parmalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import de.tyranus.parmalog.analyzer.MailLogAnalyzer;
import de.tyranus.parmalog.config.ParmalogConfig;

/**
 * Main class of <b>permalog</b>.
 * 
 */
@Component
public class Parmalog {
	private static final Logger LOGGER = LoggerFactory.getLogger(Parmalog.class);

	@Autowired
	MailLogAnalyzer mailLogAnalyzer;

	public void start() {
		mailLogAnalyzer.analyze();
	}

	public static void main(String[] args) {
		LOGGER.info("Starting up Parmalog.");

		int reason = 0;
		try {
			final ApplicationContext context = new AnnotationConfigApplicationContext(ParmalogConfig.class);
			context.getBean(Parmalog.class).start();
		}
		catch (ParmalogException | BeanCreationException e) {
			reason = handleException(e);
		}

		LOGGER.info("Shutting down Parmalog with reason code {}.", reason);
		System.exit(reason);
	}

	/**
	 * Exception handler
	 * 
	 * @param e
	 *            the exception.
	 */
	private static int handleException(Exception e) {
		e.printStackTrace();
		LOGGER.error("UNCAUGHT EXCEPTION: {}", e.getMessage());

		final int reason;
		if (e instanceof ParmalogException) {
			reason = ((ParmalogException) e).getReason();
		}
		else if (e instanceof BeanCreationException) {
			reason = ParmalogException.REASON_BEAN_CREATION;
		}
		else {
			reason = 1;
		}

		return reason;
	}
}
