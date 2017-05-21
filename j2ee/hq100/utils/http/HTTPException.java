package com.up72.hq.utils.http;

/**
 * Created by Administrator on 2015/5/27.
 */
public class HTTPException extends RuntimeException {

	public HTTPException(String message) {
		super(message);
	}

	public HTTPException(Exception e) {
		super(e);
	}

	public HTTPException(String message, Exception e) {
		super(message, e);
	}
}