/*
 * Projekt: KiTa Gebühren
 * Autor: $Id$
 * Copyright (c): IT@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2012
 */
package de.tyranus.parmalog.token.postgrey;

import de.tyranus.parmalog.token.MailLogTokenBody;

/**
 * @author tim
 * 
 */
public class PostgreyTokenBody implements MailLogTokenBody {

	enum Action {
		Greylist;
	}

	enum Reason {
		/** Client tries to send a message to the recipient the first time. */
		New,
		/** Client tries to send a message to the recipient early after thw first one. */
		EarlyRetry,
	}

	public Action getAction() {
		return null;
	}

	public Reason getReason() {
		return null;
	}

	public String getClientName() {
		return null;
	}

	public String getClientAddress() {
		return null;
	}

	public String getSender() {
		return null;
	}

	public String getRecipient() {
		return null;
	}

}
