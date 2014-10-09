package audiodemo.cse260.cs.stonybrook.edu;


import java.awt.*;
import javax.swing.JPanel;

/**
 * Panel to display a real-valued signal as a waveform.
 * 
 * @author Gene Stark
 * @version 201011
 */
public class WaveformPanel extends JPanel {

	/** The underlying audio clip. */
	private AudioClip clip;

	/** The horizontal zoom factor. */
	private double hzoom;

	/**
	 * Initialize a WaveformPanel.
	 * 
	 * @param clip The clip to be displayed.
	 */
	public WaveformPanel(AudioClip clip) {
		super();
		this.clip = clip;
		setZoom(8.0);
	}

	/**
	 * Get the horizontal zoom factor.
	 * 
	 * @return  the horizontal zoom factor, in samples per pixel.
	 */
	public double getZoom() {
		return hzoom;
	}

	/**
	 * Set the horizontal zoom factor.
	 * This causes the size of the panel to change.
	 *
	 * @param horiz  The horizontal zoom factor, in samples per pixel.
	 */
	public void setZoom(double horiz) {
		hzoom = horiz;
		int width = (int)(clip.length() / hzoom);
		int height = 100;
		setPreferredSize(new Dimension(width, height));
		revalidate();
	}

	/**
	 * Redraw the content of this panel.
	 * 
	 * @param g  Graphics context for drawing.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color c = g.getColor();
		g.setColor(Color.BLUE);
		Dimension d = getSize();
		long length = clip.length();
		double samplesPerPixel = length / d.getWidth();
		hzoom = samplesPerPixel;
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
				double v = clip.getSample((int)(i+j)) / amp;
				int y = d.height - (int)((d.height * v + d.height)/2.0);
				if(x > 0)
					g.drawLine(px, py, x, y);
				px = x;
				py = y;
			}
		}
		g.setColor(c);
	}
}
