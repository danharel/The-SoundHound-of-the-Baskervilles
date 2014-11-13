package cse260.finalproject.fall2014.dan.harel;

public class Match {
	
	/** Identification of the matching probes */
	private ClipIdentification id;
	
	/** Delta value corresponding to the matching probes */
	private int delta;
	
	public Match(ClipIdentification id, int delta) {
		this.id = id;
		this.delta = delta;
	}
	
	public Match(AudioClip clip, int delta) {
		this(clip.getIdentifier(), delta);
	}
	
	@Override
	public String toString() {
		return String.format("Name: %s\tDelta: %d", id.getName(), delta);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode() + delta*100; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Match))
			return false;
		Match m = (Match) obj;
		return (id.equals(m.id)) && (delta == m.delta);
	}
	
	public ClipIdentification getIdentifier() {
		return id;
	}
	
	public int getDelta() {
		return delta;
	}
}
