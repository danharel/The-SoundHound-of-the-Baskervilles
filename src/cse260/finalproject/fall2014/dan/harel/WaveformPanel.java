package cse260.finalproject.fall2014.dan.harel;

import java.awt.Graphics;

/**
 * Visual representation of the Waveform of an AudioClip.
 * @author danharel
 *
 */
public class WaveformPanel extends DisplayPanel {

	/** AudioClip represented by the Waveform */
	private AudioClip clip;

	/** Model for this Panel */
	Waveform waveform;
	
	public WaveformPanel(AudioClip clip) {
		super(clip);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Draws the waveform to the JPanel
	 */
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub

	}

}
