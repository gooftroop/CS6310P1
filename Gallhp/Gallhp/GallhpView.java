package Gallhp;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lib.ISimulation;
import lib.IView;
import lib.UpdatePacket;
import util.GallhpHandler;
import util.SimulationFactory;
import util.SimulationSelection;

public class GallhpView extends JFrame implements Observer, IView {

	private static final long serialVersionUID = 1L;
	
	private JButton runBtn, resetBtn, stopBtn;
	private MenuView menuView;
	private ResultsView resultsView;
	private GallhpHandler resultHandler;
	private ISimulation simulation;
	private JPanel colorScale;
	
	private Thread runner = null;
	
	public GallhpView() {
		
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
		} catch (InstantiationException e1) {
		} catch (IllegalAccessException e1) {
		} catch (UnsupportedLookAndFeelException e1) {
		}
		
		// add our JPanel components
		menuView = new MenuView(this);
		resultsView = new ResultsView();
		
		this.setTitle("Heat Diffusion Simulation");
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(2, 2, 2, 2);
		
		this.add(menuView, gbc);
		gbc.gridx++;
		this.add(resultsView, gbc);
		gbc.gridx++;
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		runBtn = new JButton("Run");
		runBtn.setPreferredSize(new Dimension(40, 25));
		runBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resultsView.reset();
				runSim();
			}
		});
		
		this.add(runBtn, gbc);
		gbc.gridy++;
		
		stopBtn = new JButton("Stop");
		stopBtn.setPreferredSize(new Dimension(40, 25));
		stopBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stopSim();
			}
		});
		
		this.add(stopBtn, gbc);
		gbc.gridy++;
		
		resetBtn = new JButton("Reset");
		resetBtn.setPreferredSize(new Dimension(40, 25));
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuView.reset();
				resultsView.reset();
			}
		});
		
		this.add(resetBtn, gbc);
		
		gbc.gridx++;
		
		colorScale = resultsView.getColorScale();
		this.add(colorScale, gbc);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void runSim() {
		
		double top = Double.parseDouble(menuView.getTopEdgeText());
		double bottom = Double.parseDouble(menuView.getBottomEdgeText());
		double left = Double.parseDouble(menuView.getLeftEdgeText());
		double right = Double.parseDouble(menuView.getRightEdgeText());
		
		int width = Integer.parseInt(menuView.getPlateWidth());
		int height = Integer.parseInt(menuView.getPlateHeight());
		
		// Display error message
		if (width <= 0 || width > Integer.MAX_VALUE) return;
		if (height <= 0 || height > Integer.MAX_VALUE) return;
		
		resultsView.initGrid(width, height);
		
		resultHandler = new GallhpHandler();
		resultHandler.addObserver(this);
		
		SimulationSelection selection = menuView.getSelectedSimulation();
		
		if (selection != null) {

			simulation = SimulationFactory.getInstance().produceSimulation(selection);

			simulation.injectHandler(resultHandler);
			simulation.setup(width, height, top, bottom, left, right);
			
			runner = new Thread(new Runnable() {
			    public void run() {
			    	try { 
			    		simulation.run();
			    	} catch(Exception e) {
			    		e.printStackTrace();
			    	}
			    }
			});
			
			runner.start();
		}
	}
	
	public void stopSim() {
		
		if (runner != null && runner.isAlive()) {
			runner.interrupt();
			try {
				runner.join();
			} catch (InterruptedException e) {
				// Do nothing
			}
			
			runner = null;
		}
		
		resultsView.reset();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (!(arg1 instanceof UpdatePacket)) 
			return;

		UpdatePacket packet = (UpdatePacket) arg1;
		resultsView.updateResults(packet.temp, packet.x, packet.y);
	}
}
