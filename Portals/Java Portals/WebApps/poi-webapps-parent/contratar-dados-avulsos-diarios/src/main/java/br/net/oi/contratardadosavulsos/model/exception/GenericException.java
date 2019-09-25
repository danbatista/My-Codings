/**
 * 
 */
package br.net.oi.contratardadosavulsos.model.exception;

/**
 * Exception class for Generic Error, Service Unavailable, etc.
 * @author mark.gary.m.lalap
 */
public class GenericException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public GenericException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * @param message
	 * @param cause
	 */
	public GenericException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message
	 */
	public GenericException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message of 
	 * (cause==null ? null : cause.toString()) (which typically contains the class 
	 * and detail message of cause).
	 * @param cause
	 */
	public GenericException(final Throwable cause) {
		super(cause);
	}

}
