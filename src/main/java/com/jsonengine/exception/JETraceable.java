package com.jsonengine.exception;

public interface JETraceable {

	JEErrorCodeEnum getErrorCode();

	String getMessage();

	Exception getTraceException();

}
