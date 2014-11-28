package cse260.finalproject.fall2014.dan.harel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * Model for an individual Spectra.
 * @author danharel
 *
 */
public class Spectra {
	
	/** Number of spectra per second */
	private static final int spectraPerSecond = 15;
	
	public static final int spectraInterval = AudioClip.samplesPerSecond/spectraPerSecond;
	
	/** Size of each spectra */
	public static final int samplesPerSpectra = 1024;
	
	/** Number of this Spectra. Ex: the first spectra has number = 0; */
	private int number;
	
	/** Multiplier. Surrounding values must be less than this factor for a
	 * value to be considered a peak; Lower differential results in more peaks
	 */
	private final double differential = .15;

	/** Samples represented by the Spectra */
	private double[] samples;
	
	/**
	 * 
	 * @param samples
	 * 		Samples to represent by the Spectra
	 */
	public Spectra(int number, double[] samples) {
		this.number = number;
		this.samples = samples;
	}
	
	/** 
	 * 
	 * @return
	 * 		DFT of the Spectra
	 */
	public double[] getDFT() {
		/*double[] samplesModified = new double[samples.length*2];
		for (int i = 0; i < samples.length; i++)
			samplesModified[i*2] = samples[i];
		DoubleFFT_1D fft = new DoubleFFT_1D(samples.length);
		fft.complexForward(samplesModified);
		return samplesModified;*/
		
		double[] samplesModified = new double[samples.length*2];
		for (int i = 0; i < samples.length; i++) {
			samplesModified[i*2] = samples[i];
			//System.out.println(clip.getSample(i));
		}	
		DoubleFFT_1D fft = new DoubleFFT_1D(samples.length);
		fft.complexForward(samplesModified);
		
		return samplesModified;
	}
	
	public double[] getPowerArray() {
		double dft[] = getDFT();
		double[] powerArray = new double[samples.length];
		for (int i = 0; i < dft.length/2; i++) {
			powerArray[i] = dft[i*2]*dft[i*2] + dft[i*2+1]*dft[i*2+1];
		}
		
		return Arrays.copyOf(powerArray, powerArray.length/2);
		//return powerArray;
	}
	
	/**
	 * 
	 * @return
	 * 		List of Peaks of the Spectra
	 */
	public List<Peak> getPeaks() {
		List<Peak> peaks = new ArrayList<Peak>();
		
		double[] power = getPowerArray();

		double maxVal = Double.MIN_VALUE;
		for(double val : power) {
			if (val > maxVal)
				maxVal = val;
		}
		
		for (int i = 1; i < power.length-1; i++) {
			if (power[i] - power[i-1] > maxVal*differential &&
				power[i] - power[i+1] > maxVal*differential)
				peaks.add(new Peak(number*Spectra.spectraInterval,i));
		}
		/*for (int i = 1; i < power.length-1; i++) {
			if (power[i]*differential > power[i-1] &&
				power[i]*differential > power[i+1])
			//peaks.add(new Peak(number*samples.length+i,i));
			peaks.add(new Peak(number*Spectra.spectraInterval,i));
		}*/
		//System.out.println(peaks.size());
		return peaks;
	}
	
	class SpectraPeakIterator implements Iterator<Peak> {
		
		private int index;
		private final double[] power = getPowerArray();
		private Peak next;
		
		public SpectraPeakIterator() {
			setNext();
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public Peak next() {
			Peak returnVal = next;
			setNext();
			return returnVal;
		}

		@Override
		public void remove() {
			
		}
		
		private void setNext() {
			next = null;
			while (index < power.length - 1) {
				if (power[index]*differential > power[index-1] &&
						power[index]*differential > power[index+1]) {
						next = new Peak(number*Spectra.spectraInterval,index);
						break;
				}
				index++;
			}
		}
		
	}
	
}
