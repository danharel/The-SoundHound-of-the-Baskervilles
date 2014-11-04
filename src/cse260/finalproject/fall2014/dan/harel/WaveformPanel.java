package cse260.finalproject.fall2014.dan.harel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * Visual representation of the Waveform of an AudioClip.
 * @author danharel
 *
 */
public class WaveformPanel extends DisplayPanel {
	
	private AudioClip clip;
	
	/** int value of the y-axis. All lines are drawn from here */
	// y grows downward. Top left = (0,0)
	private int yAxis;
	
	public WaveformPanel(AudioClip clip) {
		super(clip);
		// TODO Auto-generated constructor stub
		
		this.clip = clip;
		
		yAxis = getHeight()/2;
		
		System.out.println(yAxis);
		
		invalidate();
		revalidate();
		
		repaint();
		
	}
	
	/**
	 * Draws the waveform to the JPanel
	 */
	@Override
	public void paintComponent(Graphics g) {
		/*
		super.paintComponent(g);
		yAxis = getHeight()/2;
		double max = clip.getMaxAmplitude();
		int x = 0;
		for (double sample : clip.getSamples()) {
			//System.out.println((int)(yAxis*(1-sample/max)));
			g.drawLine(0, (int)(yAxis*(1-sample/max)), x, (int)(yAxis*(1-sample/max)));
			revalidate();
			x++;
		}*/
		
		//STARK'S CODE. REMOVE LATER
		super.paintComponent(g);
		Color c = g.getColor();
		g.setColor(Color.BLUE);
		Dimension d = getSize();
		long length = clip.getSamples().length;
		double samplesPerPixel = length / d.getWidth();
		double hzoom = samplesPerPixel;
		double amp = clip.getMaxAmplitude();
		if(amp < 1E-6)
			amp = 1E-6;
		int px = 0;
		int py = 0;
		Rectangle bounds = g.getClipBounds();
		int startX = (int)bounds.getX();
		int endX = startX + (int)bounds.getWidth();
		// We probably need to overshoot the clip region in order to properly draw the waveform.
		if(startX > 0)
			startX--;
		double i = startX * samplesPerPixel;
		for(int x = startX; x < endX; x++, i += samplesPerPixel) {
			for(double j = 0.0; j < samplesPerPixel; j += 1.0) {
				double v = clip.getSamples()[(int)(i+j)] / amp;
				int y = d.height - (int)((d.height * v + d.height)/2.0);
				if(x > 0)
					g.drawLine(px, py, x, y);
				px = x;
				py = y;
			}
		}
			
	}
	
	/*@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000,600);
	}*/

}
