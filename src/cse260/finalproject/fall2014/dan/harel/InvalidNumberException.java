package cse260.finalproject.fall2014.dan.harel;

/**
 * Thrown when an invalid number is passed into a method.
 * @author danharel
 *
 */
public class InvalidNumberException extends Exception {

	/**
	 * Creates a new InvalidNumberException
	 * @param value
	 * 		The invlaid value that was passed.
	 */
	public InvalidNumberException(double value) {
		super("The number \"" + value +"\" is invalid for this operation.");
	}

}
