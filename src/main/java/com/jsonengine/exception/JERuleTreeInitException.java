package com.jsonengine.exception;

public class JERuleTreeInitException extends JEException {

	private static final long serialVersionUID = -2476591431658859226L;

	public JERuleTreeInitException( JEErrorCodeEnum errorCode ) {
		this( errorCode, null, null );
	}

	public JERuleTreeInitException( JEErrorCodeEnum errorCode, String message ) {
		this( errorCode, message, null );
	}

	public JERuleTreeInitException( JEErrorCodeEnum errorCode, String message, Exception traceException ) {
		super( errorCode, message, traceException );
	}

}
