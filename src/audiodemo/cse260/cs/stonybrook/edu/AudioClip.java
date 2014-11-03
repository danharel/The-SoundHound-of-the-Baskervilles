package audiodemo.cse260.cs.stonybrook.edu;

import java.io.*;
import javax.sound.sampled.*;

/**
 * An AudioClip represents an audio clip.
 *
 * @author E. Stark
 * @version 20140918
 */

public class AudioClip implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Audio format associated with the clip. */
	private AudioFormat format;

	/** Samples making up the clip. */
	private double[] samples;

	/**
	 * Maximum amplitude of the clip.
	 * This is the maximum of the absolute values of the samples.
	 */
	private double maxAmplitude;

	/**
	 * Create an audio clip with a given format from a given array
	 * of samples.
	 *
	 * @param format  The format of the clip.
	 * @param samples  The array of samples.
	 */
	private AudioClip(AudioFormat format, double[] samples) {
		this.format = format;
		this.samples = samples;

		// Compute the maximum amplitude.
		for(int i = 0; i < samples.length; i++) {
			double a = Math.abs(samples[i]);
			if(a > maxAmplitude)
				maxAmplitude = a;
		}
	}
	
	public static AudioClip testClip() {
		final int sampleRate = AudioDemo.N;
		int frequency = 1;
		final int seconds = 1;
		AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
		double[] testSamples = new double[sampleRate*seconds];
		for (int i = 0; i < testSamples.length; i++) {
			testSamples[i] = Math.sin(2*Math.PI*i*frequency/sampleRate);
		}
		
		return new AudioClip(format, testSamples);
	}

	/**
	 * Get the number of samples in this clip.
	 * 
	 * @return the number of samples in this clip.
	 */
	public int length() {
		return samples.length;
	}

	/**
	 * Get the sample at a specified index in this clip.
	 * 
	 * @return the sample at a specified index in this clip.
	 */
	public double getSample(int index) {
		return samples[index];
	}

	/**
	 * Get the maximum amplitude of this signal.  This is the
	 * maximum of the absolute values of the samples in this signal.
	 * 
	 * @return  the maximum amplitude of this signal.
	 */
	public double getMaxAmplitude() {
		return maxAmplitude;
	}

	/**
	 * Get the audio format of this clip.
	 */
	public AudioFormat getAudioFormat() {
		return format;
	}

	/**
	 * Extract an audio clip from an audio input stream.
	 *
	 * @param in The audio input stream from which to extract the clip.
	 * @param name The name of the clip.
	 * @return the extracted audio clip.
	 * @throws IOException if there is an error reading the input stream.
	 */
	public static AudioClip fromStream(AudioInputStream in, String name)
			throws IOException {
		// Verify that the input stream has the format we need.
		// Right now this is 16-bit signed PCM format.
		AudioFormat format = in.getFormat();
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
		double[] samples = new double[(int)length];;
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
		return new AudioClip(format, samples);
	}

	/**
	 * Create an audio input stream from the data in this AudioClip.
	 *
	 * @param format The audio format to use to encode the data.
	 * @return the audio input stream created from the data in this
	 * Audio Clip.
	 */
	public AudioInputStream toStream() {
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

		// Reconstruct PCM samples from the clip and wrap them
		// in an input stream.
		byte[] result = new byte[samples.length * bytesPerFrame];
		int off = 0;
		for(int i = 0; i < samples.length; i++, off += bytesPerFrame) {
			double v = samples[i] * 32768.0;
			short s = v > Short.MAX_VALUE ? Short.MAX_VALUE : (short)v;
			byte b1 = (byte)((s>>8) & 0xff);
			byte b2 = (byte)(s & 0xff);
			if(!bigEndian) {
				byte tmp = b1;
				b1 = b2;
				b2 = tmp;
			}
			for(int j = 0; j < channels; j++) {
				// If there is more than one channel, duplicate samples.
				result[off+2*j] = b1;
				result[off+2*j+1] = b2;
			}
		}
		return
				new AudioInputStream(new ByteArrayInputStream(result),
						format, samples.length);
	}

	/**
	 * Play this audio clip over the audio output device.
	 *
	 * @param format The audio format to use to encode the data.
	 * @throws UnsupportedAudioFileException if the specified format is not
	 * supported on this platform.
	 * @throws LineUnavailableException if no audio line can be obtained
	 * to play the clip.
	 * @throws IOException if there is an error while playing the clip.
	 */
	public void play()
			throws UnsupportedAudioFileException, LineUnavailableException,
			IOException {
		AudioInputStream ain = toStream();
		SourceDataLine outputLine = AudioSystem.getSourceDataLine(format);
		outputLine.open(format);
		outputLine.start();
		byte[] buf = new byte[1024];
		int n;
		while((n = ain.read(buf)) != -1)
			outputLine.write(buf, 0, n);
		outputLine.drain();
		// For some reason I have found that closing the line too soon
		// at this point truncates the playing of the clip.  This doesn't
		// make sense, since drain() should have waited for the data
		// to be played, but maybe it is only a problem under FreeBSD.
		try {
			Thread.sleep(1000);
		} catch(InterruptedException x){
			// Do nothing.
		}
		outputLine.close();
	}  
}

