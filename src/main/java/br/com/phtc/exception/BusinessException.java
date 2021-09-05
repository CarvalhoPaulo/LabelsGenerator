package br.com.phtc.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 4030058030498134810L;

	public BusinessException(String message) {
		super(message);
	}
}
