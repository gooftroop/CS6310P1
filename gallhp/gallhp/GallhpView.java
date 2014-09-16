package gallhp;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class GallhpView extends JFrame {

	private static final long serialVersionUID = 1L;
	private MenuView menuView;
	private ResultsView resultsView;
	
	public GallhpView() {
		// add our JPanel components
		menuView = new MenuView();
		resultsView = new ResultsView();
		
		this.setTitle("Heat Diffusion Simulation");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(menuView, BorderLayout.CENTER);
		this.add(resultsView, BorderLayout.PAGE_END);
		this.pack();
		this.setVisible(true);
	}

}
