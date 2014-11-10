package cse260.finalproject.fall2014.dan.harel;

import java.io.Serializable;

/**
 * Object representation of a spectral peak. Contains the time at which the
 * peak appears along with the value of the peak.
 * @author danharel
 *
 */
public class Peak implements Serializable {

	/** Time at which a peak appears */
	private int time;
	
	/** Frequency of the peak */
	private int frequency;
	
	/**
	 * 
	 * @param time
	 * 		Time at which the Peak appears
	 * @param value
	 * 		Frequency represented by the Peak
	 */
	public Peak(int time, int frequency) {
		this.time = time;
		this.frequency = frequency;
	}
	
	/**
	 * 
	 * @return
	 * 		Time at which the Peak appears
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * 
	 * @return
	 * 		The frequency represented by the Peak;
	 */
	public int getFrequency() {
		return frequency;
	}
	
}
