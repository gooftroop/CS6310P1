package gallhp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class MenuView extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JRadioButton twdahpBtn, tpfahpBtn, twfahpBtn, tpdohpBtn;
	private ButtonGroup group;
	private JTextField topEdge, rightEdge, bottomEdge, leftEdge, latticeCount;
	private JButton runBtn, resetBtn;
	protected String model;
	protected int lattices;
	protected float top, left, right, bottom;
	private GallhpView frame;
	
	
	public MenuView(GallhpView frame) {
		this.frame = frame;
		TitledBorder modelTitle;
		modelTitle = BorderFactory.createTitledBorder("Simulation Options");
		this.setBorder(modelTitle);
		this.setLayout(new FlowLayout());
		
		twdahpBtn = new JRadioButton("twdahp");
		tpfahpBtn = new JRadioButton("tpfahp");
		twfahpBtn = new JRadioButton("twfahp");
		tpdohpBtn = new JRadioButton("tpdohp");
		
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
		
		runBtn = new JButton("Run");
		runBtn.addActionListener(this);
		
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(this);
		
		this.add(runBtn);
		this.add(resetBtn);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == runBtn) {
			System.out.println("Run Button pressed");
			this.runSim();
		}
		else if (e.getSource() == resetBtn) {
			System.out.println("Reset Button pressed");
			this.reset();
		}
		else {
			// let's assume some ghost is interfering here
		}
	}


	public void reset() {
		latticeCount.setText(null);
		topEdge.setText(null);
		rightEdge.setText(null);
		bottomEdge.setText(null);
		leftEdge.setText(null);
		tpfahpBtn.setSelected(true);
	}
	
	public void runSim() {
		System.out.println("Running simulation....");
		
		this.top = Float.parseFloat(topEdge.getText());
		this.right = Float.parseFloat(rightEdge.getText());
		this.bottom = Float.parseFloat(bottomEdge.getText());
		this.left = Float.parseFloat(leftEdge.getText());
		lattices = Integer.parseInt(latticeCount.getText());
		model = group.getSelection().toString();
		frame.chooseModel();
	}

}
