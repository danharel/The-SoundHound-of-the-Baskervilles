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
	private static final int samplesPerSpectra = Spectra.samplesPerSpectra;

	private static final int spectraInterval = Spectra.spectraInterval;

	public SpectrogramPanel(AudioClip clip) {
		super(clip);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,getWidth(), getHeight());

		//Calculate the number of samples per pixel.
		long length = clip.getSamples().length;
		double samplesPerPixel = length / getWidth();

		//ith segment of length N in the song.

		for (int i = 1; i < clip.getSamples().length/spectraInterval; i++) {

			Spectra spectra = new Spectra(i, Arrays.copyOfRange(
					clip.getSamples(), 
					spectraInterval*i - samplesPerSpectra/2, 
					spectraInterval*i + samplesPerSpectra/2)
					);

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

			//The height that the next rectangle will be drawn at.
			int currHeight = 0;
			double currMax = Double.MIN_NORMAL;

			//jth sample in the power array
			for (int j = 0; j < powerArray.length; j++) {
				//Calculate the grey-scale value of the color to draw
				float colorVal = (float)((powerArray[j]-min)/(max-min));
				Color color = new Color(colorVal, colorVal, colorVal, 1 );

				//Set the color
				g.setColor(color);

				//Draw the color
				//Divide j by 15 to fit it in the screen. Figure this out later
				if (/* 2*j*(samplesPerSpectra/samplesPerPixel*2)/getHeight() */ j/5 > currHeight) {
					currHeight = j/5;
					//currHeight = (int)(2*j*(samplesPerSpectra/samplesPerPixel)/getHeight());
					currMax = Double.MIN_VALUE;
				}

				if (colorVal > currMax) {
					currMax = colorVal;
					g.fillRect(
							(int) ((spectraInterval*i-samplesPerSpectra/4)/samplesPerPixel), 
							currHeight, 
							(int) (samplesPerSpectra/samplesPerPixel),
							1
							);
				}
				
			}
			
		}
		
		g.setColor(Color.YELLOW);
		for (List<Peak> peaks : clip.getPeaks()) {
			for (Peak peak : peaks) {
				int x = (int)((peak.getTime()-samplesPerSpectra/4)/samplesPerPixel);
				//int y = (int)(getHeight()*peak.getFrequency()/samplesPerSpectra);
				int y = (int)(peak.getFrequency()/1);
				int width = (int)(samplesPerSpectra/samplesPerPixel);
				int height = 1;
				g.fillRect(x,y,width,height);
				revalidate();
				//System.out.printf("x: %d\ny: %d\n\n", peak.getTime(), (int) (5*getHeight()*peak.getFrequency()/samplesPerSpectra));
			}
		}
		//g.fillRect(100,100,100,100);

	}
}