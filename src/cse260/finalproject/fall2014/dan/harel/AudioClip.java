package cse260.finalproject.fall2014.dan.harel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Object representation of an audio file of arbitrary length.
 * @author danharel
 *
 */
public class AudioClip implements Serializable {
	
	/** Path of the string */
	private transient String path;

	/** Name of the clip */
	private  String name;
	
	/** Clip representing the audio clip */
	private transient File file;
	
	/** Array of bytes read in */
	private transient byte[] bytes;
	
	/** List of Peaks that appear in the song. */
	private transient List<Peak> peaks;
	
	/**
	 * Creates a new AudioClip using the given file path
	 * @param filePath
	 * 		Path of the file to open
	 */
	public AudioClip(String filePath) {
		this(new File(filePath));
	}
	
	/**
	 * Creates a new AudioClip using its File object
	 * @param file
	 */
	public AudioClip(File file) {
		name = file.getName();
		this.file = file;
		path = file.getAbsolutePath();
	}
	
	/** Plays the song */
	public void play() {
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param file
	 * 		File to get the AudioFormat of
	 * @return
	 * 		AudioFormat of the given file
	 */
	private AudioFormat getAudioFormat(File file) {
		return null;
	}
	
	/**
	 * 
	 * @return
	 * 		ID number for the track.
	 */
	public int getTrackId() {
		/*Will most likely just return the hash code. May be subject to change.
		Depends on how well that works.*/
		return hashCode();
	}
	
	/**
	 * 
	 * @return
	 * 		A list of peaks in the clip
	 */
	public List<Peak> getPeaks() {
		return peaks;
	}
	
	public List<Peak> getPeaksAtTime(int time) {
		ArrayList<Peak> newPeaks = new ArrayList<Peak>();
		for (Peak peak : newPeaks) {
			if (peak.getTime() == time)
				newPeaks.add(peak);
		}
		return newPeaks;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/*private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(path);
		out.writeObject(name);
		out.writeObject(bytes);
		out.writeObject(peaks);
	}
	
	private void readObject(ObjectInputStream in)
			throws ClassNotFoundException, IOException {
		// default deserialization
		in.defaultReadObject();
		path = (String) in.readObject();
		name = (String) in.readObject();
		bytes = (byte[]) in.readObject();
		peaks = (List<Peak>) in.readObject();
		file = new File(path);
	}*/

}
