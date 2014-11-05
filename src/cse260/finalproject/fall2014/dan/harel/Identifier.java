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

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
	
	/** KIll me */
	private SongListPanel songList;

	/**
	 * Creates new Identifier object
	 */
	public Identifier() {
		super("Identifier");
		
		setSize(800,600);
		
		database = SongDatabase.getSongDatabase();
		
		getContentPane().setLayout(new GridLayout(2,1));
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenu m = new JMenu("File");
		mb.add(m);
		
		JMenuItem openFile = new JMenuItem("Load File");
		m.add(openFile);
		openFile.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadSong();
			}
		});
		
		JMenuItem play = new JMenuItem("Play Song");
		m.add(play);
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSong();
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
	    		
	    	    waveform = new WaveformPanel(clip);
	    	    add(new JScrollPane(waveform));
	    	    waveform.setVisible(true);
	    	    
	    	    spectrogram = new SpectrogramPanel(clip);
	    	    add(new JScrollPane(spectrogram));
	    	    spectrogram.setVisible(true);
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
		
	}

}
