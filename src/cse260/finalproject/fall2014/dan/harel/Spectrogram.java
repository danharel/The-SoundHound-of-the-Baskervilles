package cse260.finalproject.fall2014.dan.harel;

import java.util.ArrayList;

/**
 * Model class for the Spectragram of an AudioClip.
 * @author danharel
 * @deprecated
 * 		User Spectra to represent an individual piece of a song rather than an
 * 		entire song.
 *
 */
@Deprecated
public class Spectrogram {

	/** Array of samples for the song that the Spectrogram represents */
	private double[] samples;
	
	/**
	 * 
	 * @param samples
	 * 		Samples that make up an AudioClip
	 */
	public Spectrogram(double[] samples) {
		
	}
	
	/**
	 * 
	 * @param samples
	 * 		Samples to analyze
	 * @return
	 * 		DFT of the samples passed in.
	 */
	public double[] getDFT(double[] samples) {
		return null;
	}
	
	public double[] getPowerArray() {
		return null;
	}
	
	/**
	 * Returns 
	 * @return
	 */
	public ArrayList<Peak> getPeaks() {
		return null;
	}
}
