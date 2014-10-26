package cse260.finalproject.fall2014.dan.harel;

import java.io.File;

import javax.sound.sampled.AudioFormat;

public class AudioClip {

	/** File representing the audio clip */
	File audioFile;
	
	/** Array of bytes read in */
	byte[] bytes;
	
	/**
	 * Creates a new AudioClip using the given file path
	 * @param filePath
	 * 		Path of the file to open
	 */
	public AudioClip(String filePath) {
		
	}
	
	/** Plays the song */
	public void play() {
		
	}
	
	/**
	 * 
	 * @param file
	 * 		File to get the AudioFormat of
	 * @return
	 * 		AudioFormat of the given file
	 */
	private AudioFormat getAudioFormat(File file) {
		return null;
	}
}
