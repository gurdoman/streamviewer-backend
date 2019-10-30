package com.adrianrossino.common;

@SuppressWarnings("serial")
public class InvalidTokenException extends Exception{
	public InvalidTokenException(String message){
		super(message);
	}
}
