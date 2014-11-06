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
// Later implement functionality to return an iterator for the samples rather than the entire array
public class AudioClip implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4539836400380219095L;
	
	/** Sample rate of a clip. Figure out how to convert a song to this bitrate */
	public static final int samplesPerSecond = 8000;

	/** Path of the string */
	private String path;

	/** Name of the clip */
	private String name;
	
	/** Clip representing the audio clip */
	private File file;
	
	/** Array of bytes read in */
	private double[] samples;
	
	/** List of Peaks that appear in the song. */
	private List<Peak>[] peaks;
	
	/** Identifies the clip */
	private ClipIdentifier identifier;
	
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
		
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(file);
			AudioFormat format = in.getFormat();
			
			//WRITTEN BY PROFESSOR STARK. REMOVE LATER
			// Verify that the input stream has the format we need.
			// Right now this is 16-bit signed PCM format.
			if(format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
				throw new IllegalArgumentException("Signed PCM format required.");
			int channels = format.getChannels();
			if(channels != 1 && channels != 2)
				throw new IllegalArgumentException("Mono or stereo required.");
			int bytesPerFrame = format.getFrameSize();
			int bytesPerSample = bytesPerFrame/channels;
			if(bytesPerSample != 2)
				throw new IllegalArgumentException("16-bit samples required.");

			boolean bigEndian = format.isBigEndian();
			long length = in.getFrameLength();
			if(length > Integer.MAX_VALUE)
				throw new IllegalArgumentException("Clip too long");

			// Convert the samples in the original stream to floating point.
			// Stereo is reduced to mono by averaging the channel values.
			samples = new double[(int)length];
			byte[] buf = new byte[bytesPerFrame];
			for(int i = 0; i < length; i++) {
				double v = 0.0;
				in.read(buf);
				for(int j = 0; j < channels; j++) {
					byte b1 = buf[2*j];
					byte b2 = buf[2*j+1];
					if(!bigEndian) {
						byte tmp = b1;
						b1 = b2;
						b2 = tmp;
					}
					int s = ((b1<<8) | (b2&0xff));
					v += (s/32768.0);
				}
				v /= channels;
				samples[i] = v;
				//System.out.println(v);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		peaks = Extractor.getPeaks(this);
		
		identifier = new ClipIdentifier(this);
		
	}
	
	/** Plays the song */
	public void play() {
		
		try {
			File f = new File(path);
			AudioInputStream stream = AudioSystem.getAudioInputStream(f);
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
	
	public double[] getSamples() {
		return samples;
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
	public List<Peak>[] getPeaks() {
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
	
	public String getName() {
		return name;
	}
	
	public ClipIdentifier getIdentifier() {
		return identifier;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(path);
		out.writeObject(name);
		out.writeObject(samples);
		out.writeObject(peaks);
	}
	
	private void readObject(ObjectInputStream in)
			throws ClassNotFoundException, IOException {
		System.out.println("Reading in file" + name);
		// default deserialization
		in.defaultReadObject();
		path = (String) in.readObject();
		name = (String) in.readObject();
		samples = (double[]) in.readObject();
		peaks = (List<Peak>[]) in.readObject();
		file = new File(path);
	}
	
	public double getMaxAmplitude() {
		double max = Double.MIN_VALUE;
		for (double d : samples) {
			if (Math.abs(d) > max)
				max = d;
		}
		return max;
	}

}
