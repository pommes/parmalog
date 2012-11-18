/*
 * Projekt: KiTa Gebühren
 * Autor: $Id$
 * Copyright (c): IT@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2012
 */
package de.tyranus.parmalog.token.postfix;

import de.tyranus.framework.data.TAbstractDataKey;

/**
 * @author tim
 * 
 */
public class NoQueueEvent extends TAbstractDataKey implements PostfixSmtpdTokenBodyEvent {

	public enum Reason {
		Reject, Unsupported
	}

	public enum RejectReason {
		Greylisted, Spam, Unsupported
	}

	public void setReason(Reason value) {
		set(key(), value);
	}

	public Reason getReason() {
		return get(Reason.class, key());
	}

	public void setRejectReason(RejectReason value) {
		set(key(), value);
	}

	public RejectReason getRejectReason() {
		return get(RejectReason.class, key());
	}

	public void setFrom(String value) {
		setString(key(), value);
	}

	public String getFrom() {
		return getString(key());
	}

	public void setTo(String value) {
		setString(key(), value);
	}

	public String getTo() {
		return getString(key());
	}
}
