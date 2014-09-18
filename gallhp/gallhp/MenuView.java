package gallhp;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import util.SimulationSelection;

public class MenuView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JRadioButton twdahpBtn, tpfahpBtn, twfahpBtn, tpdohpBtn;
	private ButtonGroup group;
	private JTextField topEdge, rightEdge, bottomEdge, leftEdge, latticeCount;
	
	protected String model;
	protected int lattices;
	protected float top, left, right, bottom;
	
	
	public MenuView(GallhpView frame) {
		
		TitledBorder modelTitle;
		modelTitle = BorderFactory.createTitledBorder("Simulation Options");
		this.setBorder(modelTitle);
		this.setLayout(new FlowLayout());
		
		twdahpBtn = new JRadioButton("Twdahp");
		tpfahpBtn = new JRadioButton("Tpfahp");
		twfahpBtn = new JRadioButton("Twfahp");
		tpdohpBtn = new JRadioButton("Tpdohp");
		
		tpfahpBtn.setSelected(true);
		
		group = new ButtonGroup();
		group.add(tpfahpBtn);
		group.add(twdahpBtn);
		group.add(twfahpBtn);
		group.add(tpdohpBtn);
		
		this.add(tpfahpBtn);
		this.add(twdahpBtn);
		this.add(twfahpBtn);
		this.add(tpdohpBtn);
		
		// Inputs for parameters
		topEdge = new JTextField();
		topEdge.setColumns(4);
		JLabel topEdgeLabel = new JLabel("Top");
		this.add(topEdgeLabel);
		this.add(topEdge);
		
		rightEdge = new JTextField();
		rightEdge.setColumns(4);
		JLabel rightEdgeLabel = new JLabel("Right");
		this.add(rightEdgeLabel);
		this.add(rightEdge);
		
		bottomEdge = new JTextField();
		bottomEdge.setColumns(4);
		JLabel bottomEdgeLabel = new JLabel("Bottom");
		this.add(bottomEdgeLabel);
		this.add(bottomEdge);
		
		leftEdge = new JTextField();
		leftEdge.setColumns(4);
		JLabel leftEdgeLabel = new JLabel("Left");
		this.add(leftEdgeLabel);
		this.add(leftEdge);
		
		latticeCount = new JTextField();
		latticeCount.setColumns(4);
		JLabel latticeCountLabel = new JLabel("Lattice Count");
		this.add(latticeCountLabel);
		this.add(latticeCount);
		
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
		return this.topEdge.getText();
	}

	public String getBottomEdgeText() {
		return this.bottomEdge.getText();
	}
	
	public String getLeftEdgeText() {
		return this.leftEdge.getText();
	}
	
	public String getRightEdgeText() {
		return this.rightEdge.getText();
	}
	
	public String getPlateWidth() {
		return this.latticeCount.getText();
	}
	
	public String getPlateHeight() {
		return this.latticeCount.getText();
	}
	
	public SimulationSelection getSelectedSimulation() {
		return SimulationSelection.valueOf(this.group.getSelection().toString().toUpperCase());
	}
}
