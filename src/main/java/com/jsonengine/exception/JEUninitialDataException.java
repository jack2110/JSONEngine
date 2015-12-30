package com.jsonengine.exception;

public class JEUninitialDataException extends JERuntimeException {
	
	private static final long serialVersionUID = 8402978597701096133L;

	public JEUninitialDataException() {
		this( null, null );
	}

	public JEUninitialDataException( String message ) {
		this( message, null );
	}

	public JEUninitialDataException( String message, Exception traceException ) {
		super( JEErrorCodeEnum.UNINITIAL_DATA, message, traceException );
	}
}
