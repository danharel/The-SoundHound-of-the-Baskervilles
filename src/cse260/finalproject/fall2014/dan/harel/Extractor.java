package cse260.finalproject.fall2014.dan.harel;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Extractor {
	
	/** Difference in time required for two peaks to get paired into a Probe */
	private static int timeDiff = 2;
	
	public static List<Peak>[] getPeaks(AudioClip clip) {
		double[] samples = clip.getSamples();
		List<Peak>[] peaks = (List<Peak>[]) new ArrayList[samples.length/Spectra.spectraInterval];
		peaks[0] = new ArrayList<Peak>();
		for (int i = 1; i < peaks.length; i++) {
			Spectra s = new Spectra(i, Arrays.copyOfRange(
					samples, 
					i*Spectra.spectraInterval - Spectra.samplesPerSpectra/2, 
					i*Spectra.spectraInterval + Spectra.samplesPerSpectra/2
					));
			peaks[i] = s.getPeaks();
			//System.out.println(peaks[i]);
		}
		return peaks;
	}
	
	public static List<Probe> getProbes(AudioClip clip) {
		List<Probe> probes = new ArrayList<Probe>();
		
		List<Peak>[] peakLists = getPeaks(clip);
		for (int i = 0; i < peakLists.length - 1; i++) {
			for (Peak peak1 : peakLists[i]) {
				for (Peak peak2 : peakLists[i+(AudioClip.samplesPerSecond*timeDiff)/Spectra.spectraInterval]) {
					probes.add(new Probe (peak1, peak2));
				}
			}
		}
		return probes;
	}
	
	public static List<AbstractMap.SimpleEntry<Probe, ProbeLocation>> getProbesAndLocations(AudioClip clip) {
		
		List<AbstractMap.SimpleEntry<Probe, ProbeLocation>> probes = new ArrayList<AbstractMap.SimpleEntry<Probe, ProbeLocation>>();
		
		List<Peak>[] peakLists = clip.getPeaks();
		for (int i = 0; i < peakLists.length - AudioClip.samplesPerSecond*timeDiff/Spectra.spectraInterval; i++) {
			for (Peak peak1 : peakLists[i]) {
				for (Peak peak2 : peakLists[i+(AudioClip.samplesPerSecond*timeDiff/Spectra.spectraInterval)]) {
					probes.add(
							new AbstractMap.SimpleEntry<Probe, ProbeLocation>(
									new Probe (peak1, peak2), 
									new ProbeLocation(clip.getIdentifier(),i*Spectra.spectraInterval)
									)
							);
				}
			}
		}
		return probes;
	}

}
