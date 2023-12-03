package com.tcs.exception;

public class DuplicateMovieException extends RuntimeException {
	

	private static final long serialVersionUID = 1L;

	public DuplicateMovieException(String msg) {
		super(msg);
	}
}