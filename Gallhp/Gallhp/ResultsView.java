package Gallhp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class ResultsView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int HGAP = 1, VGAP = 1;
	private static final int MAX_DIMENSION = 500;
	
	private JPanel[][] results;
	
	public ResultsView() {
		
		TitledBorder resultTitle;
		resultTitle = BorderFactory.createTitledBorder("Simulation Results");
		this.setBorder(resultTitle);
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(MAX_DIMENSION, MAX_DIMENSION));
	}
	
	public void initGrid(int width, int height) {
		
		results = new JPanel[width][height];
		GridBagConstraints gbc = new GridBagConstraints();
		
		int cell_width = MAX_DIMENSION / width;
		int cell_height = MAX_DIMENSION / height;
		
		
		JPanel cell = null;
		for (int y = 0; y <  height; y++) {
			for (int x = 0; x < width; x++) {
				
				gbc.gridx = x;
                gbc.gridy = y;
				
                cell = new JPanel();
                cell.setOpaque(true);
                cell.setBackground(Color.WHITE);
				
				Border border = null;
                if (y < height - 1)
                	 border = (x < width - 1) ? new MatteBorder(1, 1, 0, 0, Color.GRAY) : new MatteBorder(1, 1, 0, 1, Color.GRAY);
                else
                	border = (x < width - 1) ? new MatteBorder(1, 1, 1, 0, Color.GRAY) : new MatteBorder(1, 1, 1, 1, Color.GRAY);
                
                cell.setBorder(border);
                cell.setPreferredSize(new Dimension(cell_width, cell_height));
				this.add(cell, gbc);
				results[x][y] = cell;
			}
		}
		
		this.validate();
		this.repaint();
	}
	
	public void reset() {
		results = null;
		this.removeAll();
		
		this.validate();
		this.repaint();
	}

	public void updateResults(double temp, int x, int y) {

		JPanel cell = results[x][y];
		cell.setBackground(this.getColor(temp));
	}
	
	private Color getColor(double temp) {
		
		if (temp < 10) return Color.BLUE;
		if (temp < 25) return Color.CYAN;
		if (temp < 50) return Color.YELLOW;
		if (temp < 75) return Color.ORANGE;
		else return Color.RED;
	}
}
