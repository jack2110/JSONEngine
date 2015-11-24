package com.jsonengine.exception;

public class JERuntimeException extends RuntimeException implements JETraceable {

	private static final long		serialVersionUID	= -3392527146214881256L;

	private final JEErrorCodeEnum	errorCode;

	private final String			message;

	private final Exception			traceException;

	public JERuntimeException( JEErrorCodeEnum errorCode ) {
		this( errorCode, null, null );
	}

	public JERuntimeException( JEErrorCodeEnum errorCode, String message ) {
		this( errorCode, message, null );
	}

	public JERuntimeException( JEErrorCodeEnum errorCode, String message, Exception traceException ) {
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
