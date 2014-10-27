package cse260.finalproject.fall2014.dan.harel;

/**
 * Location of a Probe, represented by the ID of the song, as well as the time
 * at which it appears.
 * @author danharel
 *
 */
public class ProbeLocation {

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
		
	}
	
	/**
	 * 
	 * @param clip
	 * 		AudioClip in which the Probe appears
	 * @param time
	 * 		Time at which the Probe appears
	 */
	public ProbeLocation(AudioClip clip, int time) {
		
	}
	
	/**
	 * 
	 * @return
	 * 		Song ID associated with this ProbeLocation
	 */
	public int getId() {
		return -1;
	}
	
	/**
	 * 
	 * @return
	 * 		Time at which the probe appears
	 */
	public int getTime() {
		return -1;
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 * 		The difference in time between the two Probes.
	 */
	public static int getDelta(Probe p1, Probe p2) {
		return -1;
	}
 }
