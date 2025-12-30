package com.ruby.exception;

public class DuplicateMemberException extends RuntimeException{

	private static final long serialVersionUID = 8698746671138460406L;

	public DuplicateMemberException(String msg) {
		super(msg);
	}
}
