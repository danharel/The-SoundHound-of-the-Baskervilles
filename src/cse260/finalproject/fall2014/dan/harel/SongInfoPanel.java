package cse260.finalproject.fall2014.dan.harel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SongInfoPanel extends JPanel {

	/** Visual list of songs in the index */
	private SongListPanel list;
	
	/** Text representation of the selection */
	private JLabel info;
	
	public SongInfoPanel(SongListPanel list) {
		super(new BorderLayout());
	
		info = new JLabel();
		this.list = list;
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("Click!");
				repaint();
			}
		});
		
		add(info, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		List<ClipIdentification> ids = list.getSelectedValuesList();
		if (ids.size() != 1) {
			info.setText("Please select one song to receive information on.");
		}
		else {
			SongDatabase database = SongDatabase.getSongDatabase();
			ClipIdentification id = ids.get(0);
			info.setText(String.format("<html>Name: %s<br>Length: %s<br>Probes Indexed: %d</html>", id.getName(), id.getTimeAsString(), database.getNumProbesIndexed(id)));
			//info.setText(ids.get(0).toString() + "Probes Indexed: " + database.getNumProbesIndexed(ids.get(0)));
		}
	}
}
