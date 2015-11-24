package com.jsonengine.exception;

public class JEInitException extends JERuntimeException {

	private static final long serialVersionUID = -6325138942975838178L;

	public JEInitException( JEErrorCodeEnum errorCode ) {
		this( errorCode, null, null );
	}

	public JEInitException( JEErrorCodeEnum errorCode, String message ) {
		this( errorCode, message, null );
	}

	public JEInitException( JEErrorCodeEnum errorCode, String message, Exception traceException ) {
		super( errorCode, message, traceException );
	}

}
