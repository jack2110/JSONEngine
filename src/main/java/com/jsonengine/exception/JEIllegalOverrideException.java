package com.jsonengine.exception;

public class JEIllegalOverrideException extends JERuntimeException {

	private static final long serialVersionUID = -2612662692426419586L;

	public JEIllegalOverrideException() {
		this( null, null );
	}

	public JEIllegalOverrideException( String message ) {
		this( message, null );
	}

	public JEIllegalOverrideException( String message, Exception traceException ) {
		super( JEErrorCodeEnum.ILLEGAL_OVERRIDE, message, traceException );
	}
}
