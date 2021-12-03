package exceptions;

public class EmptyTreeException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptyTreeException() {}
	
	public EmptyTreeException(String msg) {
		super(msg);
	}
}
