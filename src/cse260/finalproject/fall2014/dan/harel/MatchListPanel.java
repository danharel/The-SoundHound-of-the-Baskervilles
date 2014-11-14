package cse260.finalproject.fall2014.dan.harel;

import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class MatchListPanel extends JList<Match> {
	
	/**
	 * 
	 */
	private final MatchInfo matchInfo;
	private DefaultListModel list;

	public MatchListPanel(MatchInfo matchInfo) {
		super();
		this.matchInfo = matchInfo;
		
		list = new DefaultListModel<Match>();
		for (Match match : this.matchInfo.matches.keySet())
			addElementToList(match);
		setModel(list);
		
		setCellRenderer(this.new Renderer());
	}
	
	private void addElementToList(Match match) {
		int val = this.matchInfo.matches.get(match);
		int low = 0;
		int high = list.size();
		while (low < high) {
			int mid = (low+high)/2;
			if (val < this.matchInfo.matches.get(list.get(mid)))
				low = mid+1;
			else
				high = mid;
		}
		//System.out.println("Match: " + match);
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
	         s+= "\tMatches: " + MatchListPanel.this.matchInfo.matches.get((Match)value)
	        		 + "\tOffset: " + getTimeAsString(((Match)value).getDelta());
	         
	         setText(s);
	         return this;
	     }
	     
	     public String getTimeAsString(int delta) {
	    	 int seconds = delta/AudioClip.samplesPerSecond;
	    	 //int seconds = delta;
	    	 String sec = ""+seconds%60;if (sec.length()==1)
	    		 sec = "0"+sec;
	    	 else if (sec.length()==0)
	    		 sec = "00";
	    	 return seconds/60+":"+sec;
	     }

	}


}