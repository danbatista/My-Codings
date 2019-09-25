/**
 * 
 */
package br.net.oi.contratardadosavulsos.model.exception;

/**
 * Exception class for ineligible terminal
 * @author mark.gary.m.lalap
 */
public class ClienteInelegivelException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public ClienteInelegivelException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * @param message
	 * @param cause
	 */
	public ClienteInelegivelException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message
	 */
	public ClienteInelegivelException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message of 
	 * (cause==null ? null : cause.toString()) (which typically contains the class 
	 * and detail message of cause).
	 * @param cause
	 */
	public ClienteInelegivelException(final Throwable cause) {
		super(cause);
	}

}
