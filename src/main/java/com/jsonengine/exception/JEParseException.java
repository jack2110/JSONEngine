package com.jsonengine.exception;

public class JEParseException extends JERuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7890751363812988726L;

	public JEParseException() {
		this( null, null );
	}

	public JEParseException( String message ) {
		this( message, null );
	}

	public JEParseException( String message, Exception traceException ) {
		super( JEErrorCodeEnum.PARSE_FAILURE_ERROR, message, traceException );
	}

}
