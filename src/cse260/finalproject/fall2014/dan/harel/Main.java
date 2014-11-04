package cse260.finalproject.fall2014.dan.harel;

/**
 * Main class to be run.
 * @author danharel
 *
 */
public class Main {

	/** Application that identifies a clip */
	private static Identifier identifier;
	
	/** Application that handles the indexing of a song and its probes */
	private static Indexer indexer;
	
	/**
	 * Gives user the option to use the indexing application or the clip 
	 * identifying application.
	 */
	public static void main(String[] args) {
		identifier = new Identifier();
		//indexer = new Indexer();
	}

}
