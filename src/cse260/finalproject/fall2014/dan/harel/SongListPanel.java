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
public class SongListPanel extends JList<AudioClip> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SongDatabase database;
	
	DefaultListModel<AudioClip> songList;

	public SongListPanel(SongDatabase database) {
		super();
		
		this.database = database;
		
		songList = new DefaultListModel<AudioClip>();
		for (AudioClip clip : database.getSongsIndexed())
			songList.addElement(clip);
		setModel(songList);
	}

	class CellRenderer extends JLabel implements ListCellRenderer<Object> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(
				JList<? extends Object> list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			// TODO Auto-generated method stub
			setText("Hello!");
			
			return this;
		}
	     
	}
	
	public void addSong(AudioClip clip) {
		songList.addElement(clip);
	}
	
	public void removeSong(AudioClip clip) {
		songList.removeElement(clip);
	}
}
