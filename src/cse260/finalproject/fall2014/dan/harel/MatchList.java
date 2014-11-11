package cse260.finalproject.fall2014.dan.harel;

import java.awt.Component;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class MatchList extends JFrame {
	
	/** Map of Matches to the number of times it occurs */
	Map<Match, Integer> matches;
	
	public MatchList(Map<Match, Integer> matches) {
		this.matches = matches;
		
		add(new JScrollPane(new MatchListPanel()));
		setSize(800, 600);
		
		setVisible(true);
	}
	
	class MatchListPanel extends JList<Match> {
		
		DefaultListModel list;

		public MatchListPanel() {
			super();
			
			list = new DefaultListModel<Match>();
			for (Match match : matches.keySet())
				addElementToList(match);
			setModel(list);
			
			setCellRenderer(new Renderer());
		}
		
		private void addElementToList(Match match) {
			int val = matches.get(match);
			int low = 0;
			int high = list.size();
			while (low < high) {
				int mid = (low+high)/2;
				if (val < matches.get(list.get(mid)))
					low = mid+1;
				else
					high = mid;
			}
			list.add(low, match);
				
		}
		
		class Renderer extends JLabel implements ListCellRenderer<Object> {

		     // This is the only method defined by ListCellRenderer.
		     // We just reconfigure the JLabel each time we're called.

		     public Component getListCellRendererComponent(
		       JList<?> list,           // the list
		       Object value,            // value to display
		       int index,               // cell index
		       boolean isSelected,      // is the cell selected
		       boolean cellHasFocus)    // does the cell have focus
		     {
		         String s = value.toString();
		         s+= "\tDelta: " + matches.get((Match)value);
		         
		         setText(s);
		         return this;
		     }
		 }

		
	}

}
