package com.mb.common.constant;

/**
 * A constant class for storing all exception's or error's messages. Use
 * exception.properties file to store and get message using key
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
public class ExceptionMessage {

	private ExceptionMessage() {
	}

	// General
	public static final String INTERNAL_SERVER_ERROR = "internal.server.error";

	// Mapper
	public static final String SOURCE_OR_DESTINATION_IS_NULL = "mapper.source.destination.is.null";

	// Validation
	public static final String VALIDATION_ERROR = "validation.error";
	public static final String INVALID_REQUEST_PARAMS = "invalid.request.params";
	
	
	
}
