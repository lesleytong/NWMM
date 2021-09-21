package edu.ustb.sei.mde.bxcore.exceptions;

public class InternalBidirectionalTransformationError extends Exception {
	private static final long serialVersionUID = 6710977760581057829L;
	
	public InternalBidirectionalTransformationError() {
		super();
	}

	public InternalBidirectionalTransformationError(String message) {
		super(message);
	}

	public InternalBidirectionalTransformationError(Throwable cause) {
		super(cause);
	}

	public InternalBidirectionalTransformationError(String string, Throwable e) {
		super(string,e);
	}

}
