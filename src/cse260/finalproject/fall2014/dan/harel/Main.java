package cse260.finalproject.fall2014.dan.harel;

import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
		
		JFileChooser fc = new JFileChooser();
		//fc.setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle("Select the directory containing your audio files");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int selectVal = fc.showOpenDialog(null);
		if (selectVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + fc.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + fc.getSelectedFile());
			audioFileLocation = fc.getSelectedFile().toURI();
			System.out.println("Absolute path of audio file location: " + audioFileLocation);
		} else {
			JOptionPane.showMessageDialog(null, "In order to continue, your audio files must be located.");
			System.exit(-1);
		}
		

		identifier = new Identifier();
		indexer = new Indexer();
	}
	
	public static URI getAudioFileLocation() {
		return audioFileLocation;
	}

}
