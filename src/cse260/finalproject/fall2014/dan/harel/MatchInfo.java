package cse260.finalproject.fall2014.dan.harel;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

public class MatchInfo extends JFrame {
	
	/** Map of Matches to the number of times it occurs */
	Map<Match, Integer> matches;
	
	public MatchInfo(Map<Match, Integer> matches) {
		super("Matches");
		
		this.matches = matches;
		
		add(new JScrollPane(new MatchListPanel(this)));
		setSize(800, 600);
		
		setVisible(true);
	}

}
