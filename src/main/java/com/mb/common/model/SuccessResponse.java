package com.mb.common.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Custom http success response model to return any type of generic data with
 * status code and message
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
@JsonInclude(Include.NON_NULL)
@Data
public class SuccessResponse<T> {

	private String message;

	private Integer status;

	private T data;

	private Date timestamp;

}
