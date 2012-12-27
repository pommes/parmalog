/*
 * Projekt: KiTa Gebühren
 * Autor: $Id$
 * Copyright (c): IT@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2012
 */
package de.tyranus.parmalog.token.postfix;

import de.tyranus.framework.data.TAbstractDataKey;
import de.tyranus.parmalog.token.MailLogTokenBody;

/**
 * @author tim
 * 
 */
public class PostfixSmtpdTokenBody extends TAbstractDataKey implements MailLogTokenBody {

	public enum EventKind {
		/** The NOQUEUE event */
		NoQueue,
		/** All events that are not supported. */
		Unsupported;

		public static final String EVENT_NOQUEUE = "NOQUEUE:";
	}

	public void setEventType(Class<? extends PostfixSmtpdTokenBodyEvent> value) {
		set(key(), value);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends PostfixSmtpdTokenBodyEvent> getEventType() {
		return get(Class.class, key());
	}

	public void setEventKind(EventKind value) {
		set(key(), value);
	}

	public EventKind getEventKind() {
		return get(EventKind.class, key());
	}

	public void setEvent(PostfixSmtpdTokenBodyEvent value) {
		set(key(), value);
	}

	public <T extends PostfixSmtpdTokenBodyEvent> T getEvent(Class<T> type) {
		return get(type, key());
	}
}
