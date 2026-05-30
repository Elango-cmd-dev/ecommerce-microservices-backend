package com.elan.email_management_service.util;

public class EmailSendException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmailSendException(String message) {
        super(message);
    }
}

