package com.jsonengine.exception;

public class JEException extends Exception implements JETraceable {

	private static final long		serialVersionUID	= -7898313706109199930L;

	private final JEErrorCodeEnum	errorCode;

	private final String			message;

	private final Exception			traceException;

	public JEException( JEErrorCodeEnum errorCode ) {
		this( errorCode, null, null );
	}

	public JEException( JEErrorCodeEnum errorCode, String message ) {
		this( errorCode, message, null );
	}

	public JEException( JEErrorCodeEnum errorCode, String message, Exception traceException ) {
		this.errorCode = errorCode;

		this.message = message;

		this.traceException = traceException;
	}

	@Override
	public JEErrorCodeEnum getErrorCode() {

		return errorCode;
	}

	@Override
	public String getMessage() {

		return message;
	}

	@Override
	public Exception getTraceException() {

		return traceException;
	}

}
