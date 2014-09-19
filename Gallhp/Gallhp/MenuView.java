package Gallhp;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import util.SimulationSelection;

public class MenuView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JRadioButton tpdahpBtn, tpfahpBtn, twfahpBtn, tpdohpBtn;
	private ButtonGroup group;
	private JTextField topEdge, rightEdge, bottomEdge, leftEdge, latticeCount;
	
	private SimulationSelection selection = null;
	
	protected String model;
	protected int lattices;
	protected float top, left, right, bottom;
	
	
	public MenuView(GallhpView frame) {
		
		TitledBorder modelTitle;
		modelTitle = BorderFactory.createTitledBorder("Simulation Options");
		this.setBorder(modelTitle);
		this.setLayout(new FlowLayout());
		
		JPanel options = new JPanel();
		options.setLayout(new GridBagLayout());
		GridBagConstraints ogbc = new GridBagConstraints();
		
		ogbc.gridx = 0;
		ogbc.gridy = GridBagConstraints.RELATIVE;
		ogbc.anchor = GridBagConstraints.WEST;
		
		tpdahpBtn = new JRadioButton("Tpdahp");
		tpdahpBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selection = SimulationSelection.valueOf(((JRadioButton) arg0.getSource()).getText().toUpperCase());
			}
		});
		
		tpfahpBtn = new JRadioButton("Tpfahp");
		tpfahpBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selection = SimulationSelection.valueOf(((JRadioButton) arg0.getSource()).getText().toUpperCase());
			}
		});

		twfahpBtn = new JRadioButton("Twfahp");
		twfahpBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selection = SimulationSelection.valueOf(((JRadioButton) arg0.getSource()).getText().toUpperCase());
			}
		});

		tpdohpBtn = new JRadioButton("Tpdohp");
		tpdohpBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selection = SimulationSelection.valueOf(((JRadioButton) arg0.getSource()).getText().toUpperCase());
			}
		});

		tpfahpBtn.setSelected(true);
		selection = SimulationSelection.valueOf(tpfahpBtn.getText().toUpperCase());
		
		group = new ButtonGroup();
		group.add(tpfahpBtn);
		group.add(tpdahpBtn);
		group.add(tpdahpBtn);
		group.add(tpdohpBtn);
		
		options.add(tpfahpBtn, ogbc);
		options.add(tpdahpBtn, ogbc);
		options.add(twfahpBtn, ogbc);
		options.add(tpdohpBtn, ogbc);
		
		this.add(options);
		
		JPanel input = new JPanel();
		input.setLayout(new GridBagLayout());
		GridBagConstraints igbc = new GridBagConstraints();
		
		igbc.gridx = 0;
		igbc.gridy = GridBagConstraints.RELATIVE;
		igbc.anchor = GridBagConstraints.WEST;
		
		// Inputs for parameters
		topEdge = new JTextField();
		topEdge.setColumns(4);
		JLabel topEdgeLabel = new JLabel("Top");
		input.add(topEdgeLabel, igbc);
		input.add(topEdge, igbc);
		
		rightEdge = new JTextField();
		rightEdge.setColumns(4);
		JLabel rightEdgeLabel = new JLabel("Right");
		input.add(rightEdgeLabel, igbc);
		input.add(rightEdge, igbc);
		
		bottomEdge = new JTextField();
		bottomEdge.setColumns(4);
		JLabel bottomEdgeLabel = new JLabel("Bottom");
		input.add(bottomEdgeLabel, igbc);
		input.add(bottomEdge, igbc);
		
		leftEdge = new JTextField();
		leftEdge.setColumns(4);
		JLabel leftEdgeLabel = new JLabel("Left");
		input.add(leftEdgeLabel, igbc);
		input.add(leftEdge, igbc);
		
		latticeCount = new JTextField();
		latticeCount.setColumns(4);
		JLabel latticeCountLabel = new JLabel("Dimensions");
		input.add(latticeCountLabel, igbc);
		input.add(latticeCount, igbc);
		
		this.add(input);
		
	}

	public void reset() {
		
		latticeCount.setText(null);
		topEdge.setText(null);
		rightEdge.setText(null);
		bottomEdge.setText(null);
		leftEdge.setText(null);
		tpfahpBtn.setSelected(true);
	}

	public String getTopEdgeText() {
		return this.topEdge.getText().equals("") ? "0" : this.topEdge.getText();
	}

	public String getBottomEdgeText() {
		return this.bottomEdge.getText().equals("") ? "0" : this.bottomEdge.getText();
	}
	
	public String getLeftEdgeText() {
		return this.leftEdge.getText().equals("") ? "0" : this.leftEdge.getText();
	}
	
	public String getRightEdgeText() {
		return this.rightEdge.getText().equals("") ? "0" : this.rightEdge.getText();
	}
	
	public String getPlateWidth() {
		return this.latticeCount.getText().equals("") ? "0" : this.latticeCount.getText();
	}
	
	public String getPlateHeight() {
		return this.latticeCount.getText().equals("") ? "0" : this.latticeCount.getText();
	}
	
	public SimulationSelection getSelectedSimulation() {
		return selection;
	}
}
