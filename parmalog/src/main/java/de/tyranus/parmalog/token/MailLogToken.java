package de.tyranus.parmalog.token;

import java.util.Date;

import de.tyranus.framework.data.TAbstractDataKey;

/**
 * @author tim
 * 
 */
public class MailLogToken extends TAbstractDataKey {

	public enum BodyKind {
		/** The postfix/smtpd body */
		PostfixSmtpd,
		/** A body that is not supported. */
		Unsupported;

		public static final String POSTFIX_SMTPD = "POSTFIX/SMTPD";
	}

	public void setRawData(String value) {
		setString(key(), value);
	}

	public String getRawData() {
		return getString(key());
	}

	public void setTimestamp(Date value) {
		set(key(), value);
	}

	public Date getTimestamp() {
		return get(Date.class, key());
	}

	public void setHostname(String value) {
		set(key(), value);
	}

	public String getHostname() {
		return getString(key());
	}

	public void setProcessId(int value) {
		set(key(), value);
	}

	public int getProcessId() {
		return getInt(key());
	}

	public void setBodyType(Class<? extends MailLogTokenBody> value) {
		set(key(), value);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends MailLogTokenBody> getBodyType() {
		return get(Class.class, key());
	}

	public void setBodyKind(BodyKind value) {
		set(key(), value);
	}

	public BodyKind getBodyKind() {
		return get(BodyKind.class, key());
	}

	public void setBody(MailLogTokenBody value) {
		set(key(), value);
	}

	public <T extends MailLogTokenBody> T getBody(Class<T> type) {
		return get(type, key());
	}

}
