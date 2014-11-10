package cse260.finalproject.fall2014.dan.harel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Application for indexing songs.
 * @author danharel
 *
 */
public class Indexer extends JFrame {

	/** Database that stores songs */
	private final SongDatabase database;
	
	private WaveformPanel waveform;
	
	private SongListPanel songList;
	
	/**
	 * Creates a new JFrame for the Indexer application
	 */
	public Indexer() {
		super("Indexer");
		
		database = SongDatabase.getSongDatabase();
		songList = new SongListPanel(database);
		
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
		
		JMenuItem play = new JMenuItem("Play Song");
		m.add(play);
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//playSelectedSong();
				System.out.println(":^)");
			}
		});
		
		JMenuItem delete = new JMenuItem("Delete Songs");
		m.add(delete);
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeSelectedSongs();
			}
		});

		add(new JScrollPane(songList));
		songList.setVisible(true);
		
		setSize(800,600);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pack();
		
		WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                database.saveDatabase();
            	System.exit(0);
            }
        };
        addWindowListener(exitListener);
		
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
	    	}
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
		System.out.println("Adding song to visual list!");
		database.addAudioClip(clip);
		System.out.println("Adding song to database!");
		songList.addSong(clip);
		System.out.println("Done adding the song!");
	}
	
	/**
	 * Adds an AudioClip to the index using its File object
	 * @param file
	 */
	private void addSong(File file) {
		System.out.printf("Adding file: %s\n", file.getName());
		addSong(new AudioClip(file));
		songList.repaint();
	}
	
	/**
	 * Deletes an AudioClip from the index
	 * @param clip
	 */
	public void deleteSong(AudioClip clip) {
		deleteSong(clip.getIdentifier());
	}
	
	/**
	 * Plays the currently selected song
	 */
	/*public void playSelectedSong() {
		List<AudioClip> selected = songList.getSelectedValuesList();
		if (selected.size() > 1)
			JOptionPane.showMessageDialog(songList, "Must select only one song!");
		else
			selected.get(0).play();
	}*/
	
	public void removeSelectedSongs() {
		for (ClipIdentifier clip : songList.getSelectedValuesList())
			deleteSong(clip);
	}

	private void deleteSong(ClipIdentifier clip) {
		database.removeAudioClip(clip);
		songList.removeSong(clip);
	}
}
