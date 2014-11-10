package cse260.finalproject.fall2014.dan.harel;

import java.io.Serializable;

public class ClipIdentification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5395400616900461978L;

	/** Name of the clip */
	private String name;
	
	/** ID number of the clip */
	private int songId;
	
	public ClipIdentification(AudioClip clip) {
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
	
	@Override
	public int hashCode() {
		return name.hashCode() + songId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ClipIdentification))
			return false;
		ClipIdentification ci = (ClipIdentification)obj;
		return (name.equals(ci.name)) && (songId == ci.songId);
	}

}
