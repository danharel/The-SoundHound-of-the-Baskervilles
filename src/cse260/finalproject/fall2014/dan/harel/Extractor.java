package cse260.finalproject.fall2014.dan.harel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Extractor {
	
	/** Difference in time required for two peaks to get paired into a Probe */
	private static int timeDiff = 1; 
	
	/** AudioClip to be extracted */
	private static AudioClip clip;
	
	public static List<Probe> getProbes(AudioClip clip) {
		Extractor.clip = clip;
		for (Peak peak : clip.getPeaks()) {
			
		}
		return null;
	}
	
	public static Map<Probe, ProbeLocation> getProbesAndLocation(AudioClip clip) {
		return new HashMap<Probe, ProbeLocation>();
	}

}
