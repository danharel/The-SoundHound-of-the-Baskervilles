package audiodemo.cse260.cs.stonybrook.edu;

import java.util.ArrayList;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

public class Calculations {

	/** Number of samples per spectra */
	private static int N;
	
	/** Difference required for a sample to be considered a "peak" */
	private static final double peakDifference = 10000.0;
	
	public Calculations() {
		
	}
	
	/**
	 * 
	 * @param clip
	 * 				AudioClip to get the DFT of
	 * @return
	 * 				double[] representing the DFT of the AudioClip for
	 * 				a set of N samples
	 */
	public static double[] getDFT(AudioClip clip) {
		return getDFT(clip, 0);
	}
	
	public static double[] getDFT(AudioClip clip, int startSample) {
		N = AudioDemo.N;
		
		double[] samples = new double[N*2];
		for (int i = 0; i < N; i++) {
			samples[i*2] = clip.getSample(i+startSample);
			//System.out.println(clip.getSample(i));
		}
		
		DoubleFFT_1D fft = new DoubleFFT_1D(N);
		fft.complexForward(samples);
		
		return samples;
	}
	
	public static void printArray(double[] arr) {
		int count = 0;
		System.out.print("[");
		for (double d : arr) {
			if (count >= 40) {
				System.out.println("\n");
				count = 0;
			}
			System.out.print(d + ", ");
			count++;
			if (d != 0)
				System.out.println("NON-ZERO FOUND");
		}
		System.out.println("]");
	}
	
	public static void testPrintArray(double[] arr) {
		for (int i = 0; i < arr.length; i++) {
			double d = arr[i];
			if (d > 10E-6)
				System.out.println(d + ", Index: " + i );
		}
	}
	
	/**
	 * 
	 * @param arr
	 * 				Array of DFTs of N samples
	 * @return
	 * 				Power array of the N samples
	 */
	public static double[] getPowerArray(double[] arr) {
		double[] powerArray = new double[arr.length/2];
		for (int i = 0; i < arr.length/2; i++) {
			powerArray[i] = arr[i*2]*arr[i*2] + arr[i*2+1]*arr[i*2+1];
		}
		return powerArray;
	}
	
	public static int getN() {
		return N;
	}
	
	public static double getMax(double[] arr) {
		double max = Double.MIN_VALUE;
		for (double d: arr)
			if (d > max)
				max = d;
		return max;
	}
	
	public static ArrayList<Integer> getPeakIndexes(double[] powerArray) {
			ArrayList<Integer> peaks = new ArrayList<Integer>();
			for (int i = 1; i < powerArray.length - 1; i++) {
				if (powerArray[i] - powerArray[i-1] > peakDifference &&
					powerArray[i] - powerArray[i+1] > peakDifference)
					peaks.add(i);
			}
			return peaks;
	}

}
