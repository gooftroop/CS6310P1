package Gallhp;

import java.awt.Color;
import java.awt.Dimension;
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
		
		int cell_width = MAX_DIMENSION / width - 1;
		int cell_height = MAX_DIMENSION / height - 1;
		
		
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

	public void updateResults(final double temp, final int x, final int y) {
		
		JPanel cell = results[x][y];
		cell.setBackground(getColor(temp));
	}
	
	public JPanel getColorScale() {
		JPanel colorScale = new JPanel();
		colorScale.setLayout(new GridLayout(1, 8));
		colorScale.add(new JLabel("Degrees: 0"));
		
		JPanel color = new JPanel();
		color.setBackground(Color.BLUE);
		color.setPreferredSize(new Dimension(10, 10));
		colorScale.add(color);
		
		color = new JPanel();
		color.setBackground(Color.CYAN);
		color.setPreferredSize(new Dimension(10, 10));
		colorScale.add(color);
		
		color = new JPanel();
		color.setBackground(Color.YELLOW);
		color.setPreferredSize(new Dimension(10, 10));
		colorScale.add(color);
		
		color = new JPanel();
		color.setBackground(Color.ORANGE);
		color.setPreferredSize(new Dimension(10, 10));
		colorScale.add(color);
		
		color = new JPanel();
		color.setBackground(Color.RED);
		color.setPreferredSize(new Dimension(10, 10));
		colorScale.add(color);
		
		colorScale.add(new JLabel(" 100"));
		
		return colorScale;
	}
	
	private Color getColor(double temp) {
		
		if (temp < 10) return Color.BLUE;
		if (temp < 25) return Color.CYAN;
		if (temp < 50) return Color.YELLOW;
		if (temp < 75) return Color.ORANGE;
		else return Color.RED;
	}
}
