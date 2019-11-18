/**
 *
 */
package com.mk.postsandcomments.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mk.postsandcomments.model.Response;

/**
 * @author muffa
 *
 */
public class CommonUtil {

	/**
	 * Generates response entity
	 * 
	 * @param <T>      type of {@link Response}
	 * @param response {@link ResponseEntity}
	 * @return
	 */
	public static <T extends Response> ResponseEntity<T> generateResponseEntity(T response) {
		ResponseEntity<T> responseEntity;
		if (response.isSuccess()) {
			responseEntity = new ResponseEntity<T>(response, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<T>(response, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
}
