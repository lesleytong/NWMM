package edu.ustb.sei.mde.bxcore.exceptions;

public class NothingReturnedException extends Exception {
	private static final long serialVersionUID = 8665961028349508160L;

	public NothingReturnedException() {
	}

	public NothingReturnedException(String message) {
		super(message);
	}

	public NothingReturnedException(Throwable cause) {
		super(cause);
	}

	public NothingReturnedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NothingReturnedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
