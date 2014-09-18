package gallhp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;

import lib.ISimulation;
import lib.IView;
import lib.UpdatePacket;
import util.GallhpHandler;
import util.SimulationFactory;
import util.SimulationSelection;

public class GallhpView extends JFrame implements Observer, IView {

	private static final long serialVersionUID = 1L;
	
	private JButton runBtn, resetBtn;
	private MenuView menuView;
	private ResultsView resultsView;
	private GallhpHandler resultHandler;
	private ISimulation simulation;
	
	public GallhpView() {
		
		// add our JPanel components
		menuView = new MenuView(this);
		resultsView = new ResultsView();
		
		this.setTitle("Heat Diffusion Simulation");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(menuView, BorderLayout.WEST);
		this.add(resultsView, BorderLayout.EAST);
		
		runBtn = new JButton("Run");
		runBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Run Button pressed");
				runSim();
			}
		});
		
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Reset Button pressed");
				menuView.reset();
			}
		});
		
		this.add(runBtn, BorderLayout.SOUTH);
		this.add(resetBtn, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}
	
	public void runSim() {
		
		System.out.println("Running simulation....");
		
		double top = Double.parseDouble(menuView.getTopEdgeText());
		double bottom = Double.parseDouble(menuView.getBottomEdgeText());
		double left = Double.parseDouble(menuView.getLeftEdgeText());
		double right = Double.parseDouble(menuView.getRightEdgeText());
		
		int width = Integer.parseInt(menuView.getPlateWidth());
		int height = Integer.parseInt(menuView.getPlateHeight());
		
		resultHandler = new GallhpHandler();
		resultHandler.addObserver(this);
		
		SimulationSelection selection = menuView.getSelectedSimulation();
		simulation = SimulationFactory.getInstance().produceSimulation(selection);
		simulation.injectHandler(resultHandler);
		simulation.setup(width, height, top, bottom, left, right);
		simulation.run();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (!(arg1 instanceof UpdatePacket)) 
			return;
		
		UpdatePacket packet = (UpdatePacket) arg1;
		resultsView.updateResults(packet.temp, packet.x, packet.y);
	}
}
