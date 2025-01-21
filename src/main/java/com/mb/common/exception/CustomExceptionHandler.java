package com.mb.common.exception;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.model.ErrorResponse;
import com.mb.common.model.ValidationErrorModel;
import com.mb.common.util.CustomResponseBuilder;

import lombok.RequiredArgsConstructor;

/**
 * A custom exception handler class to handle exception thrown by application.
 * It intercepts the final step for creating the response entity object. Added
 * different exception handler methods to catch and throw exception response.
 * Uses response builder util class to build response entity class.
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private final Environment environment;
	private final CustomResponseBuilder responseBuilder;

	/**
	 * Custom exception handler
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param <T>
	 * @param customException
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler(CustomException.class)
	public <T> ResponseEntity<ErrorResponse<T>> customExceptionHandler(CustomException customException) {

		return responseBuilder.buildErrorResponse(customException.getMessage(), customException.getDetail(),
				customException.getHttpStatus(), null);
	}

	/**
	 * Null pointer exception handler
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param nullPointerException
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler(NullPointerException.class)
	public <T> ResponseEntity<ErrorResponse<T>> nullPointerExceptionHandler(NullPointerException nullPointerException) {

		return responseBuilder.buildErrorResponse(environment.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR), null,
				HttpStatus.INTERNAL_SERVER_ERROR, null);
	}

	/**
	 * Validation exception response handler
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return {@link ResponseEntity}
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ValidationErrorModel> validationErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldErr -> ValidationErrorModel.builder().property(fieldErr.getField())
						.rejectedValue(fieldErr.getRejectedValue()).message(fieldErr.getDefaultMessage()).build())
				.toList();

		return responseBuilder.buildValidationErrorResponse(environment.getProperty(ExceptionMessage.VALIDATION_ERROR),
				environment.getProperty(ExceptionMessage.INVALID_REQUEST_PARAMS), validationErrors);
	}

}
