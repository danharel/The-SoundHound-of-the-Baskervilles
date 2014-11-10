package cse260.finalproject.fall2014.dan.harel;

import java.io.Serializable;

/** Object representation of a probe. Contains the two frequencies as well as
 * the amount of time between them. 
 * @author danharel
 *
 */
public class Probe implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7169693880595399633L;

	/** First frequency */
	private int f1;
	
	/** Second frequency */
	private int f2;
	
	/** Time dfference between frequencies */ 
	private int time;
	
	/**
	 * 
	 * @param f1
	 * 		First frequency
	 * @param f2
	 * 		Second frequency
	 * @param time
	 * 		Difference in time between the two frequencies
	 */
	public Probe(int f1, int f2, int time) {
		this.f1 = f1;
		this.f2 = f2;
		this.time = time;
	}
	
	public Probe(Peak p1, Peak p2) {
		this(p1.getFrequency(), p2.getFrequency(), p2.getTime()-p1.getTime());
	}
	
	public String toString() {
		return String.format("Frequency1: %d\tFrequency2: %d\tTime: %d", f1, f2, time);
	}
	
	@Override
	public int hashCode() {
		return f1*500 + f2 + time;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Probe))
			return false;
		Probe p = (Probe)obj;
		return (f1 == p.f1) && (f2 == p.f2) && (time == p.time); 
	}
}
