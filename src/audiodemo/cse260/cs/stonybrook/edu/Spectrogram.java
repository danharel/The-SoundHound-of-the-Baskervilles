package audiodemo.cse260.cs.stonybrook.edu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Spectrogram extends JPanel {
	
	private AudioClip clip;

	public Spectrogram(AudioClip clip) {
		super();
		
		this.clip = clip;
		setPreferredSize(new Dimension(Calculations.getN(),100));
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int i = 0; i < /*clip.length()/Calculations.getN()*/1; i++) {
			double[] dft = Calculations.getDFT(clip, i);
			//Calculations.printArray(dft);
			double[] powerArray = Calculations.getPowerArray(dft);
			//Calculations.printArray(powerArray);
			for (int j = 0; j < powerArray.length; j++) {
				//Idk
				g.drawRect(0,0,1024,1);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 1024, 1);
			}
			
		}
		
		
	}

}
