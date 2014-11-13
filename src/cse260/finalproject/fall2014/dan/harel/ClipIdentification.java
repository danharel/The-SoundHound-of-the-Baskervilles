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
	
	/** Length in seconds of the AudioClip */
	private int length;
	
	public ClipIdentification(AudioClip clip) {
		name = clip.getName();
		songId = clip.getTrackId();
		length = (int)(clip.getSamples().length/clip.getSampleRate());
	}
	
	public String getName() {
		return name;
	}
	
	public int getTrackId() {
		return songId;
	}
	
	public String toString() {
		String sec = ""+length%60;
		if (sec.length()==1)
			sec = "0"+sec;
		else if (sec.length()==0)
			sec = "00";
		return String.format("<html>Name: %s<br>Length: %s:%s<br></html>", name, length/60, sec);
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

	public String getTimeAsString() {
		String sec = ""+length%60;
		if (sec.length()==1)
			sec = "0"+sec;
		else if (sec.length()==0)
			sec = "00";
		return length/60+":"+sec;
	}

}
