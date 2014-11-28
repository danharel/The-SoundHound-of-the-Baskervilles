package cse260.finalproject.fall2014.dan.harel;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Main class to be run.
 * @author danharel
 *
 */
public class Main extends JFrame {

	/** Application that identifies a clip */
	private static Identifier identifier;
	
	/** Application that handles the indexing of a song and its probes */
	private static Indexer indexer;
	
	/**
	 * Gives user the option to use the indexing application or the clip 
	 * identifying application.
	 */
	public static void main(String[] args) {
		JFileChooser fc = new JFileChooser();
		//fc.setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle("Select the directory containing your audio files");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + fc.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + fc.getSelectedFile());
		} else {
			System.out.println("In order to continue, your audio files must be located.");
		}

		identifier = new Identifier();
		indexer = new Indexer();
	}

}
