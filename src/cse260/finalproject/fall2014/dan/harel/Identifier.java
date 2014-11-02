package cse260.finalproject.fall2014.dan.harel;

import javax.swing.JFrame;

/**
 * Application used to identify the identity of an audio file.
 * @author danharel
 *
 */
public class Identifier extends JFrame {
	
	/** Current clip loaded */
	private AudioClip clip;
	
	/** JPanel representing the Waveform */
	private WaveformPanel waveform;
	
	/** JPanel representing the Spectrogram */
	private SpectrogramPanel spectrogram;
	
	/** Database of songs and probes for comparisons */
	private SongDatabase database;

	/**
	 * Creates new Identifier object
	 */
	public Identifier() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}

	/**
	 * Allows the user to load a song into the application to be identified
	 */
	private void loadSong() {
		
	}
	
	/**
	 * Plays the currently loaded song, blocking the application until the song
	 * is finished playing.
	 */
	private void playSong() {
		
	}
	
	/**
	 * Plays the currently loaded song n the background without blocking the 
	 * application.
	 */
	private void playSongInBackground() {
		
	}
	
	/**
	 * Finds the closest match of an AudioClip to another AudioClip in the
	 * database.
	 * @param clip
	 * 		Clip to match.
	 */
	private void findMatch(AudioClip clip) {
		
	}
	
	private void quit() {
		
	}
	
	private void save() {
		
	}

}
