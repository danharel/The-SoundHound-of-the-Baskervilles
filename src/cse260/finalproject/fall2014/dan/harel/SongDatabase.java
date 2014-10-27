package cse260.finalproject.fall2014.dan.harel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Database of songs and probes.
 * @author danharel
 *
 */
public class SongDatabase implements Serializable {

	/** Singleton database */
	public static SongDatabase database = null;
	
	/** Map of a Probe to its locations */
	HashMap<Probe, Set<ProbeLocation>> probeLocations;
	
	/** Map of an AudioClip to the Probes extracted */ 
	HashMap<AudioClip, List<Probe>> clipIndexes;
	
	/** File name in which the databases will be saved to and loaded from */
	private final String FILE_NAME = "database.dat";
	
	/**
	 * 
	 * @return
	 * 		A singleton instance of the SongDatabase.
	 */
	public static SongDatabase getSongDatabase() {
		if (database == null) {
			loadDatabase();
		}
		return database;
		
	}
	
	/**
	 * 
	 * @return
	 * 		Set of all songs that have been indexed.
	 */
	public Set<AudioClip> getSongsIndexed() {
		return null;
	}
	
	/**
	 * 
	 * @param probe
	 * 		Probe that can be matched to a song.
	 * @return
	 * 		List of locations of Probes that match the given Probe
	 */
	public Set<ProbeLocation> getMatches(Probe probe) {
		return null;
	}
	
	/**
	 * Adds an AudioClip to the map of AudioClips to its Probes, and adds those
	 * Probes to the map of Probes to their ProbeLocation
	 * @param clip
	 * 		Clip to add
	 */
	public void addAudioClip(AudioClip clip) {
		
	}
	
	/**
	 * 
	 * @param clip
	 * 		Clip to remove from the index
	 */
	public void removeAudioClip(AudioClip clip) {
		/* Don't forget to remove the corresponding probe locations from
		 * probeLocations. That is, go trough the list of Probes for that
		 * AudioClip, then remove all ProbeLocations with a songID that matches
		 * the AudioClip being removed
		 */
	}
	
	/**
	 * Adds a probe to the database
	 * @param probe
	 */
	public void addProbe(Probe probe, ProbeLocation location) {
		
	}
	
	/**
	 * Writes the database to a file
	 */
	public static void saveDatabase() {
		
	}
	
	/**
	 * Loads the database from a file
	 */
	private static void loadDatabase() {
		
	}
	
	
}
