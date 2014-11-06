package cse260.finalproject.fall2014.dan.harel;

import java.io.File;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 19789634L;

	/** Singleton database */
	public static SongDatabase database = null;
	
	/** Map of a Probe to its locations */
	private HashMap<Probe, Set<ProbeLocation>> probeLocations;
	
	/** Map of an AudioClip to the Probes extracted */ 
	private HashMap<ClipIdentifier, List<Probe>> clipsIndexed;
	
	/** File name in which the databases will be saved to and loaded from */
	private static final String SAVE_PATH = "database.dat";
	
	private SongDatabase() {
		probeLocations = new HashMap<Probe, Set<ProbeLocation>>();
		clipsIndexed = new HashMap<ClipIdentifier, List<Probe>>();
	}
	
	public SongDatabase(HashMap<Probe, Set<ProbeLocation>> probeLocations,
			HashMap<ClipIdentifier, List<Probe>> clipIndexes) {
		this.probeLocations = probeLocations;
		this.clipsIndexed = clipIndexes;
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
	public Set<ClipIdentifier> getSongsIndexed() {
		return clipsIndexed.keySet();
	}
	
	/**
	 * 
	 * @param clip
	 * @return
	 * 		The number of probes associated with the given AudioClip
	 */
	public int getNumProbesIndexed(AudioClip clip) {
		return clipsIndexed.get(clip.getIdentifier()).size();
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
		System.out.println("getting probes and locations!");
		Map<Probe,ProbeLocation> probesAndLocs = Extractor.getProbesAndLocation(clip);
		System.out.println("Successfully obtained probes and locations!");
		List<Probe> probes = new ArrayList<Probe>();
		System.out.println("Successfully obtained probes and locations!2");
		probes.addAll(probesAndLocs.keySet());
		System.out.println("Probes: " + probes.size());
		System.out.println("Successfully obtained probes and locations!3");
		
		// too much processing here. Find a way to cut down the runtime.
		clipsIndexed.put(clip.getIdentifier(), probes);
		System.out.println(probesAndLocs.keySet().size());
		for (Probe probe : probesAndLocs.keySet()) {
			//System.out.println(probeLocations.get(probe));
			if (!probeLocations.containsKey(probe))
				probeLocations.put(probe, new HashSet<ProbeLocation>());
			probeLocations.get(probe).add(probesAndLocs.get(probe));
		}
		System.out.println("Successfully obtained probes and locations!5");
		
	}
	
	/**
	 * 
	 * @param clip
	 * 		Clip to remove from the index
	 */
	public void removeAudioClip(AudioClip clip) {
		removeAudioClip(clip.getIdentifier());
	}
	
	public void removeAudioClip(ClipIdentifier clip) {
		/* Don't forget to remove the corresponding probe locations from
		 * probeLocations. That is, go trough the list of Probes for that
		 * AudioClip, then remove all ProbeLocations with a songID that matches
		 * the AudioClip being removed
		 */
		List<Probe> probes = clipsIndexed.get(clip);
		for (Probe probe : probes)
			probeLocations.remove(probe);
		clipsIndexed.remove(clip);
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
	 * Writes the database to a file
	 */
	public void saveDatabase() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_PATH));
			out.writeObject(probeLocations);
			out.writeObject(clipsIndexed);
			database = new SongDatabase(probeLocations, clipsIndexed);
			out.close();
		}
		catch(Exception e) {
			System.out.printf("Unable to save database\n", SAVE_PATH);
			e.printStackTrace();
			//System.out.println(e.getMessage().toString());
			database = new SongDatabase();
		}
	}
	
	/**
	 * Loads the database from a file
	 */
	private static void loadDatabase() {
		try {
			File file = new File(SAVE_PATH);
			/*
			if (!file.exists()) {
				file.createNewFile();
				database = new SongDatabase();
			}*/
			//else {
				System.out.println(file.getAbsolutePath());
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				HashMap<Probe, Set<ProbeLocation>> probeLocations = (HashMap<Probe, Set<ProbeLocation>>) in.readObject();
				HashMap<ClipIdentifier, List<Probe>> clipsIndexed = (HashMap<ClipIdentifier, List<Probe>>) in.readObject();
				database = new SongDatabase(probeLocations, clipsIndexed);
				in.close();
			//}
		}
		catch(Exception e) {
			System.out.printf("Unable to read from database file %s. Creating new one.\n", SAVE_PATH);
			e.printStackTrace();
			database = new SongDatabase();
			System.out.println(e.getCause());
			File file = new File(SAVE_PATH);
			try {
				file.createNewFile();
				System.out.println("Successfully created new file");
			}
			catch (IOException e2) {
				System.out.println("Unable to create file for unknown reason.");
			}
			catch (SecurityException e2) {
				System.out.println("Permission to create file denied. Please select a different save location.");
			}
			//e.printStackTrace();
		}
		finally {
			//database.saveDatabase();
		}
	}
}
