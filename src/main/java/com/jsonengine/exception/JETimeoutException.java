package com.jsonengine.exception;

public class JETimeoutException extends JERuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5967053833695638186L;

	public JETimeoutException() {
		this( null, null );
	}

	public JETimeoutException( String message ) {
		this( message, null );
	}

	public JETimeoutException( String message, Exception traceException ) {
		super( JEErrorCodeEnum.TIME_OUT_ERROR, message, traceException );
	}

}
