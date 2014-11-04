package cse260.finalproject.fall2014.dan.harel;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Abtract implementation of functionality for a JPanel to display information.
 * Extended by WaveformPanel and SpectrogramPanel
 * @author danharel
 *
 */
public abstract class DisplayPanel extends JPanel {
	
	/** AudioClip that this DisplayPanel represents */
	protected AudioClip clip;
	
	/**
	 * Creates a new DisplayPanel.
	 * @param clip
	 * 		AudioClip that the DisplayPanel represents
	 */
	public DisplayPanel(AudioClip clip) {
		this.clip = clip;
		setPreferredSize(new Dimension(800, 100));
	}
	
	/**
	 * Zooms in or out depending on the factor.
	 * Zoom greater than 1 will zoom in.
	 * Zoom less than 1 will zoom out.
	 */
	public void zoom(double factor) {
		
	}
	
	/**
	 * Zooms out with a default zoom factor of .5
	 */
	public void zoomOut() throws InvalidNumberException  {
		
	}
	
	/**
	 * Zooms in with a default zoom factor of 2
	 */
	public void zoomIn() throws InvalidNumberException {
		
	}
	
	/**
	 * 
	 * @return
	 * 		Number of samples that have to fit within a single pixel
	 */
	public int getSamplesPerPixel() {
		return -1;
	}
	
}
