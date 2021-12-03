package exceptions;

public class InvalidKeyException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidKeyException() {}
	
	public InvalidKeyException(String msg) {
		super(msg);
	}
}
