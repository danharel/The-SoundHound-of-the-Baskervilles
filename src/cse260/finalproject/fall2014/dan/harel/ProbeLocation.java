package cse260.finalproject.fall2014.dan.harel;

import java.io.Serializable;

/**
 * Location of a Probe, represented by the ID of the song, as well as the time
 * at which it appears.
 * @author danharel
 *
 */
public class ProbeLocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6682670273805108759L;

	/** ID number of the song that a particular probe appears in */
	private int songId;
	
	/** Time at which the probe appears in the song */  
	private int time;
	
	/**
	 * 
	 * @param songId
	 * 		ID number of the song that the Probe appears in
	 * @param time
	 * 		Rime at which the Probe appears
	 */
	public ProbeLocation(int songId, int time) {
		this.songId = songId;
		this.time = time;
	}
	
	/**
	 * 
	 * @param clip
	 * 		AudioClip in which the Probe appears
	 * @param time
	 * 		Time at which the Probe appears
	 */
	public ProbeLocation(AudioClip clip, int time) {
		this(clip.getTrackId(), time);
	}
	
	/**
	 * 
	 * @return
	 * 		Song ID associated with this ProbeLocation
	 */
	public int getId() {
		return songId;
	}
	
	/**
	 * 
	 * @return
	 * 		Time at which the probe appears
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 * 		The difference in time between the two Probes.
	 */
	public static int getDelta(ProbeLocation p1, ProbeLocation p2) {
		return Math.abs(p1.getTime() - p2.getTime());
	}
	
	public String toString() {
		return String.format("Song ID: %d\tTime: %d", songId, time);
	}
 }
