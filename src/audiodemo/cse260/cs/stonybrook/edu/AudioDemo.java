package audiodemo.cse260.cs.stonybrook.edu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

/**
 * Demonstration of how to load an audio clip from a file,
 * display it as a waveform, and play it.
 *
 * @author E. Stark
 * @version 20140918
 */
public class AudioDemo extends JFrame {

	/** Samples per spectra */ 
	public static int N = 8000;
	
	/** The currently displayed AudioClip, if any, otherwise null. */
	private AudioClip currentClip;

	/** WaveformPanel displaying the current clip, if any, otherwise null. */
	private WaveformPanel clipPanel;
	
	/** Spectrogram displaying the current Spectrogram */
	private Spectrogram spectrogram;

	public AudioDemo() {
		super("Audio Demo");
		setSize(800,600);
		//setSize(800, N);
		getContentPane().setLayout(new BorderLayout());
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		JMenu m = new JMenu("File");
		mb.add(m);
		JMenuItem openItem = new JMenuItem("Open");
		m.add(openItem);
		openItem.addActionListener
		(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadClip();
			}
		});
		JMenuItem quitItem = new JMenuItem("Quit");
		m.add(quitItem);
		quitItem.addActionListener
		(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JMenuItem testSample = new JMenuItem("Test Sample");
		m.add(testSample);
		testSample.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadClip(AudioClip.testClip());
				
				//int N = 8000;
				double[] DFT = Calculations.getDFT(currentClip);
				Calculations.testPrintArray(DFT);
			}
		});
		m = new JMenu("Clip");
		mb.add(m);
		JMenuItem zoomInItem = new JMenuItem("Zoom in");
		m.add(zoomInItem);
		zoomInItem.addActionListener
		(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(clipPanel != null) {
					clipPanel.setZoom(clipPanel.getZoom() / 2.0);
					spectrogram.setZoom(clipPanel.getZoom() / 2.0);
				}
			}
		});
		JMenuItem zoomOutItem = new JMenuItem("Zoom out");
		m.add(zoomOutItem);
		zoomOutItem.addActionListener
		(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(clipPanel != null)
					clipPanel.setZoom(clipPanel.getZoom() * 2.0);
					spectrogram.setZoom(clipPanel.getZoom() * 2.0);
			}
		});
		JMenuItem playItem = new JMenuItem("Play");
		playItem.addActionListener
		(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentClip != null) {
					SwingWorker<Object,Object> task =
							new SwingWorker<Object,Object>() {
						@Override
						public Object doInBackground() {
							try {
								currentClip.play();
							} catch(Exception x) {
								error(x);
							}
							return null;
						}
						@Override
						public void done() { }
					};
					task.execute();
				}
			}
		});
		m.add(playItem);
	}

	/**
	 * Display an error dialog.
	 * 
	 * @param x  The exception that was thrown.
	 */
	private void error(Exception x) {
		JOptionPane.showMessageDialog(this, x);
	}

	/**
	 * Prompt the user to select an audio file, create an AudioClip
	 * from its contents, and display the contents as a waveform.
	 */
	private void loadClip() {
		JFileChooser d = new JFileChooser();
		d.setDialogTitle("Select Audio File");
		d.setDialogType(JFileChooser.OPEN_DIALOG);
		d.setFileFilter
		(new javax.swing.filechooser.FileFilter() {
			private AudioFileFormat.Type[] types =
					AudioSystem.getAudioFileTypes();

			@Override
			public boolean accept(File f) {
				String name = f.getName();
				return(f.isDirectory() || supportedType(name));
			}

			private boolean supportedType(String name) {
				for(int i = 0; i < types.length; i++) {
					if(name.endsWith(types[i].getExtension()))
						return true;
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "audio files in supported formats";
			}
		});
		int ret = d.showOpenDialog(this);
		currentClip = AudioClip.testClip();
		if(ret == JFileChooser.APPROVE_OPTION) {
			try {
				File f = d.getSelectedFile();
				AudioInputStream ain = AudioSystem.getAudioInputStream(f);
				currentClip = AudioClip.fromStream(ain, f.getName());
				clipPanel = new WaveformPanel(currentClip);
				spectrogram = new Spectrogram(currentClip);
				ain.close();
				getContentPane().removeAll();
				JPanel panel = new JPanel(new GridLayout(2,1));
				panel.add(new JScrollPane(clipPanel));
				panel.add(new JScrollPane(spectrogram));
				//getContentPane().add(new JScrollPane(clipPanel));
				//getContentPane().add(new JScrollPane(spectrogram));
				getContentPane().add(panel);
				invalidate();
				validate();
				//revalidate();
				repaint();
			} catch(IOException x) {
				JOptionPane.showMessageDialog
				(null, "IOException: " + x.getMessage());
			} catch(UnsupportedAudioFileException x) {
				JOptionPane.showMessageDialog
				(null, "Unsupported audio file format.");
			} catch(Throwable x) {
				JOptionPane.showMessageDialog
				(null, "Error: " + x);
				x.printStackTrace();
			}
		}
	}
	
	private void loadClip(AudioClip clip) {
		currentClip = clip;
		clipPanel = new WaveformPanel(currentClip);
		spectrogram = new Spectrogram (currentClip);
		getContentPane().removeAll();
		//getContentPane().add(new JScrollPane(clipPanel));
		getContentPane().add(new JScrollPane(spectrogram));
		invalidate();
		validate();
		//revalidate();
		repaint();
		
		
	}

	public static void main(String[] args) {
		AudioDemo demo = new AudioDemo();
		demo.setLocationByPlatform(true);
		demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		demo.setVisible(true);
	}
}
