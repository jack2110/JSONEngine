package com.jsonengine.exception;

public class JERuleHandlerInitException extends JERuntimeException {

	private static final long serialVersionUID = -7583372663784269038L;

	public JERuleHandlerInitException( JEErrorCodeEnum errorCode ) {
		this( errorCode, null, null );
	}

	public JERuleHandlerInitException( JEErrorCodeEnum errorCode, String message ) {
		this( errorCode, message, null );
	}

	public JERuleHandlerInitException( JEErrorCodeEnum errorCode, String message, Exception traceException ) {
		super( errorCode, message, traceException );
	}

}
