package cse260.finalproject.fall2014.dan.harel;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;

/**
 * Object representation of an audio file of arbitrary length.
 * @author danharel
 *
 */
public class AudioClip {

	/** Name of the clip */
	private String name;
	
	/** Clip representing the audio clip */
	private Clip audio;
	
	/** Array of bytes read in */
	private byte[] bytes;
	
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
	
	/**
	 * 
	 * @return
	 * 		ID number for the track.
	 */
	public int getTrackId() {
		/*Will most likely just return the hash code. May be subject to change.
		Depends on how well that works.*/
		return 0;
	}
}
