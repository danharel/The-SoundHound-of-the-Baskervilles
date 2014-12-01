package cse260.finalproject.fall2014.dan.harel;

import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	
	private static URI audioFileLocation;
	
	/**
	 * Gives user the option to use the indexing application or the clip 
	 * identifying application.
	 */
	public static void main(String[] args) {
		//JOptionPane.showMessageDialog(null, "Please locate the directory of the audio files.");
		
		JFileChooser jc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Database file", "dat");
		jc.setFileFilter(filter);
		jc.setDialogTitle("Please select the database file");
		int returnVal = jc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			SongDatabase.setDatabasePath(jc.getSelectedFile().getAbsolutePath());
		}
		else {
			System.out.println("using default database location.");
		}

		identifier = new Identifier();
		indexer = new Indexer();
	}
	
	public static URI getAudioFileLocation() {
		return audioFileLocation;
	}

}
