package com.jsonengine.exception;

public enum JEErrorCodeEnum {
	TIME_OUT_ERROR( CCError.TIME_OUT, "A service or async call is time out" ),

	PROVIDER_SEARCH_FAILURE_ERROR( CCError.PROVIDER_SEARCH_FAILURE, "Search on single provider failed" ),

	NOT_SUPPORTED_METHOD_ERROR( CCError.NOT_SUPPORTED_METHOD, "Calling function is not implemented." ),

	BAD_SERVICE_RESPONSE_ERROR( CCError.BAD_SERVICE_RESPONSE, "Get non-success service response" ),

	PARSE_FAILURE_ERROR( CCError.PARSE_FAILURE, "Fail to parse JSON or other formatted objects" ),

	BAD_CONFIGURATION( CCError.BAD_CONFIGURATION, "Some critical configuration info is missed or mis-set." ),

	FILE_READ_FAILURE( CCError.FILE_READ_FAILURE, "Fail to load file due to IO exception" ),

	NO_HANDLER_ERROR( CCError.NO_HANDLER, "A service doesn't have handler register to handle such type of request" ),

	INVALID_PARAMETERS( CCError.INVALID_PARAMETERS, "Some or one of required parameters are missed or invalid" ),

	INTERNAL_ERROR( CCError.INTERNAL_ERROR, "Operation failes due to a unknown internal error" );

	JEErrorCodeEnum( String code, String description ) {
		this.code = code;

		this.description = description;
	}

	public String getCode() {

		return code;
	}

	public String getDescription() {

		return description;
	}

	private final String	code;

	private final String	description;

}

interface CCError {

	String	TIME_OUT				= "timeoutError";

	String	NO_HANDLER				= "noHandlerAvailable";

	String	NOT_SUPPORTED_METHOD	= "notSupportedMethod";

	String	PROVIDER_SEARCH_FAILURE	= "providerSearchFailure";

	String	BAD_SERVICE_RESPONSE	= "badServiceResponse";

	String	PARSE_FAILURE			= "parseFailure";

	String	BAD_CONFIGURATION		= "badConfiguration";

	String	FILE_READ_FAILURE		= "fileReadFailure";

	String	INVALID_PARAMETERS		= "invalidParameters";

	String	INTERNAL_ERROR			= "internalError";

}
