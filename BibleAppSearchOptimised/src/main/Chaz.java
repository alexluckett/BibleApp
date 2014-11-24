package main;

public class Chaz extends Exception {
	private String errorMsg;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Chaz(String errorMsg) {
	 this.errorMsg = errorMsg;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return errorMsg;
	} 

}
