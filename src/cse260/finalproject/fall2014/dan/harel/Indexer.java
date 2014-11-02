package cse260.finalproject.fall2014.dan.harel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Application for indexing songs.
 * @author danharel
 *
 */
public class Indexer extends JFrame {

	/** Database that stores songs */
	private final SongDatabase database;
	
	/**
	 * Creates a new JFrame for the Indexer application
	 */
	public Indexer() {
		super("Audio Demo");
		
		database = SongDatabase.getSongDatabase();
		getContentPane().setLayout(new BorderLayout());
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenu m = new JMenu("File");
		mb.add(m);
		
		JMenuItem openFile = new JMenuItem("Import File");
		m.add(openFile);
		openFile.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addSongFromFileSystem();
			}
		});
		
		JMenuItem openFolder = new JMenuItem("Import Folder");
		m.add(openFolder);
		openFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addSongFromFolder();
			}
		});

		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Creates a file chooser for the user to add a song.
	 */
	public void addSongFromFileSystem() {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	try {
	    		System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
	    		File file = chooser.getSelectedFile();
	    		addSong(file);
;	    	}
	    	catch (Exception e) {
	    		
	    	}
	    }
	}
	
	/**
	 * Creates a file chooser for the user to select a folder to add all songs 
	 * in the folder
	 */
	public void addSongFromFolder() {
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Audio File", "wav"));
		//chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select a folder to import");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);   
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File[] files = chooser.getSelectedFile().listFiles();
			for (File file : files)
				addSong(file);
		}
	}
	
	/**
	 * Creates a file chooser for the user to select a folder to add all songs
	 * in the folder, and all subfolders.
	 */
	public void addSongFromFolderRecursively() {
		
	}
	
	/**
	 * Adds an AudioClip to the index
	 * @param clip
	 */
	public void addSong(AudioClip clip) {
		database.addAudioClip(clip);
	}
	
	/**
	 * Adds an AudioClip to the index using its File object
	 * @param file
	 */
	private void addSong(File file) {
		System.out.printf("Adding file: %s\n", file.getName());
		addSong(new AudioClip(file));
	}
	
	/**
	 * Deletes an AudioClip from the index
	 * @param clip
	 */
	public void deleteSong(AudioClip clip) {
		
	}
}
