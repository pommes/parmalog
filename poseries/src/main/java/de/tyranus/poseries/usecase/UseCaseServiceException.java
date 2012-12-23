package de.tyranus.poseries.usecase;

import java.io.IOException;

public class UseCaseServiceException extends Exception {
	private static final long serialVersionUID = -8285389664862057939L;

	private UseCaseServiceException(String msg) {
		super(msg);
	}

	public final static UseCaseServiceException createReadError(IOException cause) {
		return new UseCaseServiceException(String.format("Error on reading file: %s", cause.getMessage()));
	}
}
