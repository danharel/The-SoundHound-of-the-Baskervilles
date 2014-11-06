package cse260.finalproject.fall2014.dan.harel;

import java.io.Serializable;

public class ClipIdentifier implements Serializable {
	
	/** Name of the clip */
	private String name;
	
	/** ID number of the clip */
	private int songId;
	
	public ClipIdentifier(AudioClip clip) {
		name = clip.getName();
		songId = clip.getTrackId();
	}
	
	public String getName() {
		return name;
	}
	
	public int getTrackId() {
		return songId;
	}
	
	public String toString() {
		return name;
	}

}
