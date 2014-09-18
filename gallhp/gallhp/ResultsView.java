package gallhp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ResultsView extends JPanel implements ResultsListener {

	private static final long serialVersionUID = 1L;
	
	public ResultsView() {
		TitledBorder resultTitle;
		resultTitle = BorderFactory.createTitledBorder("Simulation Results");
		this.setBorder(resultTitle);
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(500,500));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(WIDTH, HEIGHT, 100, 100);
	}

	public void updateResults(String results) {
		System.out.println("Results Updated\n" + results);
		
	}

}
