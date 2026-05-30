package com.shopease.paymentservice.exception;

public class PaymentAlreadyExistsException extends RuntimeException {

	public PaymentAlreadyExistsException(String message) {

		super(message);
	}

}
