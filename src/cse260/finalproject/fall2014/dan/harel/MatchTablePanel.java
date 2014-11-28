package cse260.finalproject.fall2014.dan.harel;
import java.util.Set;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


public class MatchTablePanel extends JTable {
	
	private final String[] header = {"Song Name", "Delta", "Matches", "Offset"};

	public MatchTablePanel(MatchInfoFrame matchInfo) {
		Set<Match> matches = matchInfo.getMatches().keySet();
		Object[][] values = new Object[matches.size()][header.length];
		
		int i = 0;
		for (Match match : matches) {
			values[i][0] = match.getName();
			values[i][1] = match.getDelta();
			values[i][2] = matchInfo.getMatches().get(match);
			values[i][3] = getTimeAsString(match.getDelta());
		}
		
		//super(values, header);
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
