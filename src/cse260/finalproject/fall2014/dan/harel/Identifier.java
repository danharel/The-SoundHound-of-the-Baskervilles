package cse260.finalproject.fall2014.dan.harel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Application used to identify the identity of an audio file.
 * @author danharel
 *
 */
public class Identifier extends JFrame {
	
	/** Current clip loaded */
	private AudioClip clip;
	
	/** JPanel representing the Waveform */
	private WaveformPanel waveform;
	
	/** JPanel representing the Spectrogram */
	private SpectrogramPanel spectrogram;
	
	/** Database of songs and probes for comparisons */
	private SongDatabase database;

	/**
	 * Creates new Identifier object
	 */
	public Identifier() {
		super("Identifier");
		
		setSize(800,600);
		
		database = SongDatabase.getSongDatabase();
		
		//getContentPane().setLayout(new GridLayout(2,1));
		getContentPane().setLayout(new BorderLayout());
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenu file = new JMenu("File");
		mb.add(file);
		
		JMenuItem openFile = new JMenuItem("Load File");
		file.add(openFile);
		openFile.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadSong();
			}
		});
		
		JMenuItem play = new JMenuItem("Play Song");
		file.add(play);
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSong();
			}
		});
		
		JMenu view = new JMenu("View");
		mb.add(view);
		
		JMenuItem zoomIn = new JMenuItem("Zoom In");
		view.add(zoomIn);
		zoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zoomIn();
			}
		});
		
		JMenuItem zoomOut = new JMenuItem("Zoom Out");
		view.add(zoomOut);
		zoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zoomOut();
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}

	/**
	 * Allows the user to load a song into the application to be identified
	 */
	private void loadSong() {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	try {
	    		System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
	    		File file = chooser.getSelectedFile();
	    		clip = new AudioClip(file);

	    		getContentPane().removeAll();
	    		
	    		JPanel content = new JPanel(new GridLayout(2,1));
	    		getContentPane().add(content);
	    		
	    	    waveform = new WaveformPanel(clip);
	    	    JScrollPane scroll1 = new JScrollPane(waveform);
	    	    content.add(scroll1);
	    	    //waveform.setVisible(true);
	    	    
	    	    spectrogram = new SpectrogramPanel(clip);
	    	    JScrollPane scroll2 = new JScrollPane(spectrogram);
	    	    content.add(scroll2);
	    	    
	    	    scroll1.getHorizontalScrollBar().setModel(scroll2.getHorizontalScrollBar().getModel());
	    	    
	    	    findMatch(clip);
	    	    
	    	    setTitle(clip.getName());
	    	    
	    	    pack();
	    	    revalidate();
	    	}
	    	catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	}
	
	/**
	 * Plays the currently loaded song, blocking the application until the song
	 * is finished playing.
	 */
	private void playSong() {
		if (waveform != null)
			clip.play();
		else
			JOptionPane.showMessageDialog(waveform, "Must load a song first!");
	}
	
	/**
	 * Plays the currently loaded song n the background without blocking the 
	 * application.
	 */
	private void playSongInBackground() {
	}
	
	/**
	 * Finds the closest match of an AudioClip to another AudioClip in the
	 * database.
	 * @param clip
	 * 		Clip to match.
	 */
	private void findMatch(AudioClip clip) {
		//database.printProbeLocations();
		//database.printPorbes();
		
		Map<Match, Integer> matches = new HashMap<Match, Integer>();
		List<AbstractMap.SimpleEntry<Probe, ProbeLocation>> probes = Extractor.getProbesAndLocations(clip);
		
		//database.printProbeLocations();
		/* Go through each Probe-ProbeLocation pairing */
		for (AbstractMap.SimpleEntry<Probe, ProbeLocation> probe : probes ) {
			/* Get the Locations of all indexed probes that match this one */ 
			Set<ProbeLocation> locations = database.getMatches(probe.getKey());
			/* There's a chance that this Probe is totally new. Skip it if it is */
			if (locations != null) {
				/* Check all of the locations found */
				for (ProbeLocation location : locations) {
					/* Find the difference in time between those two probes */
					int delta = ProbeLocation.getDelta(probe.getValue(), location);
					/* Create a pairing of this ClipIdentification to the delta value */
					Match match = new Match(location.getClipIdentification(), delta);
					/* Put that pairing into a map to count the number of occurances
					 * of that particular pairing of ID and delta */
					if (!matches.containsKey(match))
						matches.put(match, 1);
					else
						matches.put(match, matches.get(match)+1);
				}
			}
			else {
			}
		}
		new MatchListFrame(matches);
		/*
		for (Match match : matches.keySet())
			System.out.println(match + "\t Occurances: " + matches.get(match));*/
	}
	
	private void zoomIn() {
		if (waveform != null && spectrogram != null) {
			waveform.zoomIn();
			spectrogram.zoomIn();
		}	
		else
			JOptionPane.showMessageDialog(waveform, "Must load a song first!");
	}
	
	private void zoomOut() {
		if (waveform != null && spectrogram != null) {
			waveform.zoomOut();
			spectrogram.zoomOut();
		}	
		else
			JOptionPane.showMessageDialog(waveform, "Must load a song first!");
	}

}
