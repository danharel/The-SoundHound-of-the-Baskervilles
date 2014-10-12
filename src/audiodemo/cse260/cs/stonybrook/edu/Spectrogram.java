package audiodemo.cse260.cs.stonybrook.edu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Spectrogram extends JPanel {
	
	/** The AudioClip currently being processed */
	private AudioClip clip;
	
	/** powerArrays[i] gives the i'th powerArray of length N */
	private double[][] powerArrays;
	
	/** Number of samples per spectra */
	private final int N = AudioDemo.N;

	public Spectrogram(AudioClip clip) {
		super();
		
		this.clip = clip;
		setPreferredSize(new Dimension(AudioDemo.N,100));
		
		powerArrays = new double[clip.length()/N][N];
		for (int i = 0; i < powerArrays.length; i++) {
			powerArrays[i] = null;
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.out.println(clip.length());
		//ith segment of length N in the song.
		for (int i = 0; i < clip.length()/N; i++) {
			// By making an array of all powerArrays, lots of space is taken up, but it doesn't need to recalculate the values every time the image is shifted
			if (powerArrays[i] == null) {
				System.out.println("Calculating...");
				//Get the DFT array of the song, starting from a specific sample.
				double[] dft = Calculations.getDFT(clip,(i)*N);
				//Get the power array of the song using the DFT array.
				powerArrays[i] = Calculations.getPowerArray(dft);
			}
			
			double[] powerArray = powerArrays[i];
			
			//Find the max value in the power array.
			double max = Calculations.getMax(powerArray);
			
			//Calculate the number of samples per pixel.
			Dimension d = getSize();
			long length = clip.length();
			double samplesPerPixel = length / d.getWidth();
			
			//The height that the next rectangle will be drawn at.
			int currHeight = 0;
			double currHeightMax = Double.MIN_NORMAL;
			
			//jth sample in the power array
			for (int j = 0; j < powerArray.length; j++) {
				//Calculate the grey-scale value of the color to draw
				float colorVal = (float)(powerArray[j]/max);
				Color color = new Color(1-colorVal, 1-colorVal, 1-colorVal, 1 );
				/*try {
					if (powerArray[j] > powerArray[j-1] && powerArray[j] > powerArray[j-2]
							&& powerArray[j] > powerArray[j+1] && powerArray[j] > powerArray[j+2])
						color = Color.YELLOW;
						
				}
				catch (ArrayIndexOutOfBoundsException e) {
				}*/
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
			
			g.setColor(Color.YELLOW);
			ArrayList<Integer> peaks = Calculations.getPeakIndexes(powerArray);
			for (int peakIndex : peaks) {
				g.fillRect((int) (N/samplesPerPixel)*i, peakIndex/15, (int) (N/samplesPerPixel), 1);
			}
			
		}
		
		
		
	}

}
