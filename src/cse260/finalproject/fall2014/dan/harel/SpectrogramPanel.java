package cse260.finalproject.fall2014.dan.harel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GUI representation of the Spectrogram
 * @author danharel
 * @todo Replace the deprecated Spectrogram class
 *
 */
public class SpectrogramPanel extends DisplayPanel {
	
	/** Size of a Spectra */
	private static final int N = Spectra.N;

	public SpectrogramPanel(AudioClip clip) {
		super(clip);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//ith segment of length N in the song.
		for (int i = 0; i < clip.getSamples().length/N; i++) {
			
			Spectra spectra = new Spectra(i, Arrays.copyOfRange(clip.getSamples(), N*i, N*i+N));
			
			double[] powerArray = spectra.getPowerArray();
			
			for (int j = 0; j < powerArray.length; j++)
				powerArray[j] = Math.log(powerArray[j]);
			
			//Find the max value in the power array.
			double max = Double.MIN_VALUE;
			double min = Double.MAX_VALUE;
			for (double d : powerArray) {
				if (d > max)
					max = d;
				if (d < min)
					min = d;
			}				
			
			//Calculate the number of samples per pixel.
			long length = clip.getSamples().length;
			double samplesPerPixel = length / getWidth();
			
			//The height that the next rectangle will be drawn at.
			int currHeight = 0;
			double currHeightMax = Double.MIN_NORMAL;
			
			//jth sample in the power array
			for (int j = 0; j < powerArray.length; j++) {
				//Calculate the grey-scale value of the color to draw
				float colorVal = (float)((powerArray[j]-min)/(max-min));
				Color color = new Color(colorVal, colorVal, colorVal, 1 );
				//Set the color
				g.setColor(color);
				//Draw the color
				//Divide j by 15 to fit it in the screen.
				if ((int) (j/15) > currHeight) {
					currHeight = j/15;
					currHeightMax = Double.MIN_VALUE;
				}
				if (powerArray[j] > currHeightMax) {
					currHeightMax = powerArray[j];
					g.fillRect((int) (N/samplesPerPixel)*i, currHeight, (int) (N/samplesPerPixel), 1);
				}
			}
			
			// Draw the peaks. Currently horribly wrong
			g.setColor(Color.YELLOW);
			List<Peak> peaks = spectra.getPeaks();
			for (Peak peak : peaks) {
				//Draw the peak
				g.fillRect((int) (N/samplesPerPixel)*i, peak.getTime()%N, (int) (N/samplesPerPixel), 1);
				//g.fillRect((int) (N/samplesPerPixel)*i, peakIndex/15, (int) (N/samplesPerPixel), 1);
			}
		}
	}

}
