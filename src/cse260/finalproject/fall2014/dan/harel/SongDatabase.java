package cse260.finalproject.fall2014.dan.harel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Database of songs and probes.
 * @author danharel
 *
 */
public class SongDatabase implements Serializable {

	/** Singleton database */
	public static SongDatabase database = null;
	
	/** Map of a Probe to its locations */
	HashMap<Probe, ArrayList<ProbeLocation>> probeLocations;
	
	/** Songs that have been indexed */
	HashSet<AudioClip> songs;
	
	
	
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
	 * @param probe
	 * @return
	 * 		List of possible locations of a given Probe
	 */
	public ArrayList<ProbeLocation> getProbeLocations(Probe probe) {
		return null;
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
