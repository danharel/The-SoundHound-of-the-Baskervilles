package audiodemo.cse260.cs.stonybrook.edu;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame{

	private static final int N = 8000;
	
	private GridLayout layout;
	
	private WaveformPanel waveform;
	private Spectrogram spectrogram;
	
	private AudioClip clip;
	
	public MainFrame() {
		loadClip();
		setSize(600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
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
		File f;
		if(ret == JFileChooser.APPROVE_OPTION) {
			try {
				f = d.getSelectedFile();
				AudioInputStream ain = AudioSystem.getAudioInputStream(f);
				clip = AudioClip.fromStream(ain, f.getName());
				waveform = new WaveformPanel(clip);
				spectrogram = new Spectrogram(clip);
				ain.close();
				layout = new GridLayout(2,1);
				getContentPane().removeAll();
				getContentPane().add(new JScrollPane(waveform));
				//getContentPane().add(new JScrollPane(spectrogram));
				invalidate();
				validate();
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

	public static void main(String[] args) {
		MainFrame demo = new MainFrame();
		/*try {
			demo.clip.play();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//demo.loadClip();
		
		
		
	}

}
