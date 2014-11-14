package cse260.finalproject.fall2014.dan.harel;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;

public class MatchInfo extends JFrame {
	
	/** Header for the JTable */
	private final String[] header = {"Song Name", "Delta", "Matches", "Offset"};
	
	/** Map of Matches to the number of times it occurs */
	private Map<Match, Integer> matches;
	
	private ArrayList<Match> sortedMatches;
	
	public MatchInfo(Map<Match, Integer> matches) {
		super("Matches");
		
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
		
		//add(new JScrollPane(new MatchTablePanel(this)));
		add(new JScrollPane(new JTable(values, header)));
		setSize(800, 600);
		
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

}
