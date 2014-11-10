package cse260.finalproject.fall2014.dan.harel;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import audiodemo.cse260.cs.stonybrook.edu.AudioDemo;

/**
 * Abtract implementation of functionality for a JPanel to display information.
 * Extended by WaveformPanel and SpectrogramPanel
 * @author danharel
 *
 */
public abstract class DisplayPanel extends JPanel {
	
	/** AudioClip that this DisplayPanel represents */
	protected AudioClip clip;
	
	/** Horizontal zoom */
	protected double hzoom;
	
	/**
	 * Creates a new DisplayPanel.
	 * @param clip
	 * 		AudioClip that the DisplayPanel represents
	 */
	public DisplayPanel(AudioClip clip) {
		this.clip = clip;
		//setPreferredSize(new Dimension(800, 100));
		setZoom(32);
	}
	
	/**
	 * Zooms in or out depending on the factor.
	 * Zoom greater than 1 will zoom in.
	 * Zoom less than 1 will zoom out.
	 */
	public void setZoom(double horiz) {
		System.out.println("Setting zoom to " + horiz);
		hzoom = horiz;
		int width = (int)(clip.getSamples().length / hzoom/2.0);
		//int width = 700;
		int height = 100;
		setPreferredSize(new Dimension(width, height));
		revalidate();
	}
	
	/**
	 * Zooms out with a default zoom factor of .5
	 */
	public void zoomOut() {
		setZoom(hzoom*2);
	}
	
	/**
	 * Zooms in with a default zoom factor of 2
	 */
	public void zoomIn() {
		setZoom(hzoom/2);
	}
	
	/**
	 * 
	 * @return
	 * 		Number of samples that have to fit within a single pixel
	 */
	public int getSamplesPerPixel() {
		return -1;
	}
	
	public double getZoom() {
		return hzoom;
	}
	
}
