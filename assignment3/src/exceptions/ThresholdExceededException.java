package exceptions;

public class ThresholdExceededException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ThresholdExceededException() {}
	
	public ThresholdExceededException(String msg) {
		super(msg);
	}

}
