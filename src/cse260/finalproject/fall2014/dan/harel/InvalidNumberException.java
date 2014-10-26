package cse260.finalproject.fall2014.dan.harel;

public class InvalidNumberException extends Exception {

	public InvalidNumberException(double value) {
		super("The number \"" + value +"\" is invalid for this operation.");
	}

}
