package gallhp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ResultsView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int HGAP = 1, VGAP = 1;
	
	public ResultsView() {
		
		TitledBorder resultTitle;
		resultTitle = BorderFactory.createTitledBorder("Simulation Results");
		this.setLayout(new GridLayout(0, 0, HGAP, VGAP));
		this.setBorder(resultTitle);
		this.setPreferredSize(new Dimension(500,500));
	}
	
	public void initGrid(int width, int height) {
		
		JLabel label = null;
		for (int i = 0; i < (width * height); i ++) {
			label = new JLabel();
			label.setOpaque(true);
			label.setBackground(Color.WHITE);
			this.add(label);
		}
	}

	public void updateResults(double temp, int x, int y) {
		System.out.println("Results Updated with " + temp + ", x: " + x + ", y: " + y);
		JLabel label = (JLabel) this.getComponent(y * 7 + x);
		label.setBackground(this.getColor(temp));
		// Do we need to set?
	}
	
	private Color getColor(double temp) {
		
		if (temp < 10) return Color.BLUE;
		if (temp < 25) return Color.CYAN;
		if (temp < 50) return Color.YELLOW;
		if (temp < 75) return Color.ORANGE;
		else return Color.RED;
	}
}
