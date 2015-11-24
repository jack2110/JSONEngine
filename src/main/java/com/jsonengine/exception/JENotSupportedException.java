package com.jsonengine.exception;

public class JENotSupportedException extends JERuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3301402041184489939L;

	public JENotSupportedException() {
		this( null, null );
	}

	public JENotSupportedException( String message ) {
		this( message, null );
	}

	public JENotSupportedException( String message, Exception traceException ) {
		super( JEErrorCodeEnum.NOT_SUPPORTED_METHOD_ERROR, message, traceException );
	}

}
