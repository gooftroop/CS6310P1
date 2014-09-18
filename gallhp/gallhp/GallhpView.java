package gallhp;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import lib.GallhpHandler;
import lib.Simulation;
import tpdahp.Tpdahp;
import tpdohp.Tpdohp;
import tpfahp.Tpfahp;
import twfahp.Twfahp;

public class GallhpView extends JFrame {

	private static final long serialVersionUID = 1L;
	private MenuView menuView;
	private ResultsView resultsView;
	private GallhpHandler resultHandler;
	private Simulation simulation;
	
	public GallhpView() {
		// add our JPanel components
		menuView = new MenuView(this);
		resultsView = new ResultsView();
		
		this.setTitle("Heat Diffusion Simulation");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(menuView, BorderLayout.CENTER);
		this.add(resultsView, BorderLayout.PAGE_END);
		this.pack();
		this.setVisible(true);
	}
	
	public void chooseModel() {
		System.out.println("Running model simulation " + menuView.lattices);
		resultHandler = new GallhpHandler(menuView.lattices, menuView.lattices);
		if (menuView.model == "tpdahp") {
			simulation = new Tpdahp(resultHandler);		
		}
		else if (menuView.model == "tpfahp"){
			simulation = new Tpfahp(resultHandler);		
		}
		else if (menuView.model == "twfahp") {
			simulation = new Twfahp(resultHandler);		
		}
		// assume tpdohp
		else {
			simulation = new Tpdohp(resultHandler);		
		}
		resultHandler.addListener(resultsView);
		simulation.setup(menuView.lattices, menuView.lattices, menuView.left, menuView.right, menuView.top, menuView.bottom);
		simulation.run();
		
		
	}

}
