package cse260.finalproject.fall2014.dan.harel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	private static final String FILE_NAME = "database.dat";
	
	private SongDatabase() {
		probeLocations = new HashMap<Probe, Set<ProbeLocation>>();
		clipIndexes = new HashMap<AudioClip, List<Probe>>();
	}
	
	public SongDatabase(HashMap<Probe, Set<ProbeLocation>> probeLocations,
			HashMap<AudioClip, List<Probe>> clipIndexes) {
		this.probeLocations = probeLocations;
		this.clipIndexes = clipIndexes;
	}

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
	 * @param clip
	 * @return
	 * 		The number of probes associated with the given AudioClip
	 */
	public int getNumProbesIndexed(AudioClip clip) {
		return clipIndexes.get(clip).size();
	}
	
	/**
	 * 
	 * @param probe
	 * 		Probe that can be matched to a song.
	 * @return
	 * 		List of locations of Probes that match the given Probe
	 */
	public Set<ProbeLocation> getMatches(Probe probe) {
		return probeLocations.get(probe);
	}
	
	/**
	 * Adds an AudioClip to the map of AudioClips to its Probes, and adds those
	 * Probes to the map of Probes to their ProbeLocation
	 * @param clip
	 * 		Clip to add
	 */
	public void addAudioClip(AudioClip clip) {
		Map<Probe,ProbeLocation> probesAndLocs = Extractor.getProbesAndLocation(clip);
		List<Probe> probes = new ArrayList<Probe>();
		probes.addAll(probesAndLocs.keySet());
		clipIndexes.put(clip, probes);
		for (Probe probe : probesAndLocs.keySet()) {
			probeLocations.get(probe).add(probesAndLocs.get(probe));
		}
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
	 * Remove all Probes from the database that have a ProbeLocation that 
	 * matches the AudioClip's id
	 * id
	 * @param probe
	 * 		Probe to remove
	 * @param clip
	 * 		AudioClip which the Probe originates from
	 */
	public void removeProbeFromClip(Probe probe, AudioClip clip) {
		//Implemented because I know I'll forget how to do it later.
		for (ProbeLocation loc : probeLocations.get(probe))
			if (loc.getId() == clip.getTrackId())
				probeLocations.remove(loc);
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
	public void saveDatabase() {
		try {
			ObjectOutputStream in = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
			in.writeObject(probeLocations);
			in.writeObject(clipIndexes);
			database = new SongDatabase(probeLocations, clipIndexes);
			in.close();
		}
		catch(Exception e) {
			System.out.printf("Database file %s not found. Creating new one.\n", FILE_NAME);
			database = new SongDatabase();
		}
	}
	
	/**
	 * Loads the database from a file
	 */
	private static void loadDatabase() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
			HashMap<Probe, Set<ProbeLocation>> probeLocations = (HashMap<Probe, Set<ProbeLocation>>) in.readObject();
			HashMap<AudioClip, List<Probe>> clipIndexes = (HashMap<AudioClip, List<Probe>>) in.readObject();
			database = new SongDatabase(probeLocations, clipIndexes);
			in.close();
		}
		catch(Exception e) {
			System.out.printf("Database file %s not found. Creating new one.\n", FILE_NAME);
			database = new SongDatabase();
		}
		finally {
			database.saveDatabase();
		}
	}
	
}
