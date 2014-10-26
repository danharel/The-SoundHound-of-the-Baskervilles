package cse260.finalproject.fall2014.dan.harel;

import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class DisplayPanel extends JPanel {
	
	/** AudioClip that this DisplayPanel represents */
	private AudioClip clip;
	
	/**
	 * Creates a new DisplayPanel.
	 * @param clip
	 * 		AudioClip that the DisplayPanel represents
	 */
	public DisplayPanel(AudioClip clip) {
		
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
	
	public int getSamplesPerPixel() {
		return -1;
	}
	
	@Override
	public abstract void paintComponent(Graphics g);
	
}