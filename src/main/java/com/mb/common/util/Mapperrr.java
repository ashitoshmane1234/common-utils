package com.mb.common.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.exception.CustomException;

import lombok.RequiredArgsConstructor;

/**
 * Convert object of any class to another. Ex. Object to list, object to object
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
@Component
@RequiredArgsConstructor
public class Mapperrr {

	private final Environment environment;
	private final ModelMapper modelMapper;

	/**
	 * Generic method to map source object to target class
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param <T>
	 * @param srcObj
	 * @param targetClass
	 * @return T class object
	 */
	public <T> T convert(Object srcObj, Class<T> targetClass) {
		try {

			return modelMapper.map(srcObj, targetClass);

		} catch (IllegalArgumentException argumentException) {

			throw new CustomException(environment.getProperty(ExceptionMessage.SOURCE_OR_DESTINATION_IS_NULL));

		} catch (MappingException | ConfigurationException eRuntimeException) {

			throw new CustomException(environment.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR),
					eRuntimeException.getMessage());
		}
	}

	/**
	 * Generic method to map source object list to list of target class
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param <S>
	 * @param <T>
	 * @param srcList
	 * @param targetClass
	 * @return T class objects list
	 */
	public <S, T> List<T> convertToList(List<S> srcList, Class<T> targetClass) {
		List<T> response = new ArrayList<>();

		if (srcList != null) {
			srcList.stream().forEach(source -> response.add(convert(source, targetClass)));
		}

		return response;
	}

}
