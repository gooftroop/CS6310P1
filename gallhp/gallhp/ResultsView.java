package gallhp;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ResultsView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public ResultsView() {
		TitledBorder resultTitle;
		resultTitle = BorderFactory.createTitledBorder("Simulation Results");
		this.setBorder(resultTitle);
		this.setLayout(new FlowLayout());
	}

}
