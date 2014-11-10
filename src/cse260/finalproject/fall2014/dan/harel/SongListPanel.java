package cse260.finalproject.fall2014.dan.harel;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * Displays a list of all songs indexed, along with the ability to display
 * information about these songs.
 * @author danharel
 *
 */
public class SongListPanel extends JList<ClipIdentification> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SongDatabase database;
	
	DefaultListModel<ClipIdentification> songList;

	public SongListPanel(SongDatabase database) {
		super();
		
		this.database = database;
		
		songList = new DefaultListModel<ClipIdentification>();
		for (ClipIdentification clip : database.getSongsIndexed())
			songList.addElement(clip);
		setModel(songList);
	}
	
	public void addSong(AudioClip clip) {
		songList.addElement(clip.getIdentifier());
		revalidate();
		System.out.println("Song added to list!");
	}
	
	public void removeSong(ClipIdentification clip) {
		songList.removeElement(clip);
	}
}
