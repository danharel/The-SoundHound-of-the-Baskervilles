package cse260.finalproject.fall2014.dan.harel;

import java.util.ArrayList;

import javax.swing.JFrame;

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
		database = null;
	}
	
	/**
	 * Creates a file chooser for the user to add a song.
	 */
	public void addSongFromFileSystem() {
		
	}
	
	/**
	 * Creates a file chooser for the user to select a folder to add all songs 
	 * in the folder
	 */
	public void addSongFromFolder() {
		
	}
	
	/**
	 * Creates a file chooser for the user to select a folder to add all songs
	 * in the folder, and all subfolders.
	 */
	public void addFolderFromFolderRecursively() {
		
	}
	
	/**
	 * Adds an AudioClip to the index
	 * @param clip
	 */
	public void addSong(AudioClip clip) {
		
	}
	
	/**
	 * Deletes an AudioClip from the index
	 * @param clip
	 */
	public void deleteSong(AudioClip clip) {
		
	}
	
}
