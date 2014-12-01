package cse260.finalproject.fall2014.dan.harel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;

public class MatchListFrame extends JFrame {
	
	private JTable table;
	
	/** Header for the JTable */
	private final String[] header = {"Song Name", "Delta", "Matches", "Offset"};
	
	/** Map of Matches to the number of times it occurs */
	private Map<Match, Integer> matches;
	
	private ArrayList<Match> sortedMatches;
	
	private AudioClip clip;
	
	private static URI audioFileLocation;
	
	private static boolean locationSet = false;
	
	public MatchListFrame(Map<Match, Integer> matches) {
		super("Matches");
		
		setLayout(new GridLayout(3,1));
		
		this.matches = matches;
		
		sortedMatches = new ArrayList<Match>();
		for (Match match : matches.keySet()) {
			addElementToList(match);
		}
		
		Object[][] values = new Object[matches.size()][header.length];
		
		int i = 0;
		for (Match match : sortedMatches) {
			values[i][0] = match.getName();
			values[i][1] = match.getDelta();
			values[i][2] = matches.get(match);
			values[i][3] = getTimeAsString(match.getDelta());
			
			i++;
		}
		
		table = new JTable(values, header);
		add(new JScrollPane(table));
		
		JButton play = new JButton("Play clip");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSelected();
			}
		});
		add(play);
		
		JButton pause = new JButton("Stop clip");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseClip();
			}
		});
		add(pause);
		
		setSize(800, 600);
		
		setAudioFileLocation();
		
		setVisible(true);
	}

	public Map<Match, Integer> getMatches() {
		return matches;
	}
	
	public int getNumMatches() {
		return matches.size();
	}
	
	private void addElementToList(Match match) {
		int val = matches.get(match);
		int low = 0;
		int high = sortedMatches.size();
		while (low < high) {
			int mid = (low+high)/2;
			if (val < matches.get(sortedMatches.get(mid)))
				low = mid+1;
			else
				high = mid;
		}
		//System.out.println("Match: " + match);
		sortedMatches.add(low, match);
			
	}
	
	 private String getTimeAsString(int delta) {
   	 int seconds = delta/AudioClip.samplesPerSecond;
   	 //int seconds = delta;
   	 String sec = ""+seconds%60;if (sec.length()==1)
   		 sec = "0"+sec;
   	 else if (sec.length()==0)
   		 sec = "00";
   	 return seconds/60+":"+sec;
    }
	 
	 private void playSelected() {
		 int[] rowsSelected = table.getSelectedRows();
		 if (rowsSelected.length != 1) {
			 JOptionPane.showMessageDialog(this, "Exactly one song must be selected!");
			 return;
		 }
		 
		 URI base = audioFileLocation;
		 System.out.println("Base: " + base);
		 try {
			 URI name = new URI((String)(table.getValueAt(rowsSelected[0],0)));
			 System.out.println("Name: " + name);
			 int delta = (Integer)(table.getValueAt(rowsSelected[0],1));
			 System.out.println("Delta: " + delta);
			 URI file = base.resolve(name);
			 System.out.println("Full path: " + file);
			 String path = file.getRawPath();
			 System.out.println("Raw path: " + path);
			 clip = new AudioClip(path);
			 clip.play(delta);
		 } catch (UnsupportedAudioFileException e) {
			 e.printStackTrace();
			 System.out.println("Unable to open file for listening.");
		 } catch (URISyntaxException e) {
			e.printStackTrace();
			System.out.println("Invalid URI.");
		}
	 }
	 
	 private void pauseClip() {
		 if (clip != null) { 
			 clip.pause();
			 clip = null;
		 }
		 else {
			 JOptionPane.showMessageDialog(this, "No clip is currently playing!");
		 }
		
	 }
	 
	 private void setAudioFileLocation() {
		 	if (locationSet)
		 		return;
		 
		 	locationSet = true;
		 
		 	JFileChooser fc = new JFileChooser();
			//fc.setCurrentDirectory(new java.io.File("."));
			fc.setDialogTitle("Select the directory containing your audio files");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			int selectVal = fc.showOpenDialog(this);
			if (selectVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("getCurrentDirectory(): " + fc.getCurrentDirectory());
				System.out.println("getSelectedFile() : " + fc.getSelectedFile());
				audioFileLocation = fc.getSelectedFile().toURI();
				System.out.println("Absolute path of audio file location: " + audioFileLocation);
			} else {
				JOptionPane.showMessageDialog(this, "In order to continue, your audio files must be located.");
				System.exit(-1);
			}
	 }

}
