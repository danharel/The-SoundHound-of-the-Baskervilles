package cse260.finalproject.fall2014.dan.harel;

import java.util.List;

/**
 * Model for an individual Spectra.
 * @author danharel
 *
 */
public class Spectra {
	
	/** Size of each spectra */
	public static final int N = 8000;
	
	/** Multiplier. Surrounding values must be less than this factor for a
	 * value to be considered a peak;
	 */
	private final double differential = .2;

	/** Samples represented by the Spectra */
	private double[] samples;
	
	/**
	 * 
	 * @param samples
	 * 		Samples to represent by the Spectra
	 */
	public Spectra(double[] samples) {
		this.samples = samples;
	}
	
	/** 
	 * 
	 * @return
	 * 		DFT of the Spectea
	 */
	public double[] getDFT() {
		return null;
	}
	
	/**
	 * 
	 * @return
	 * 		List of Peaks of the Spectra
	 */
	public List<Peak> getPeaks() {
		return null;
	}
	
}
