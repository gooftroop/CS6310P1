package lib;

import java.util.ArrayList;
import java.util.List;
import gallhp.ResultsListener;

public class GallhpHandler extends ResultsHandler {
	
	private String resultMatrix;
	private final int width;
	private List<ResultsListener> listeners = new ArrayList<ResultsListener>();
	
	public GallhpHandler (int width, int height) {
		super();
		
		if (width < 0 || width > Integer.MAX_VALUE) throw new IllegalArgumentException("Invalid width");
		if (height < 0 || height > Integer.MAX_VALUE) throw new IllegalArgumentException("Invalid height");
		
		String populateRow = new String(new char[width]).replace("\0", "000.00 ").concat("\n");
		resultMatrix = new String(new char[height]).replace("\0", populateRow);
		
		this.width = width;
	}

	@Override
	public void display(float temp, int x, int y) {
		
		int index = (y * (width * 7 + NEWLINE_LENGTH)) + (x * 7);
		resultMatrix = resultMatrix.substring(0, index) + String.format("%06.2f ", temp) + resultMatrix.substring((index + 7 ));
		for (ResultsListener l : listeners) {
			l.updateResults(resultMatrix);
		}
	}

	@Override
	public void report() {
		// TODO Auto-generated method stub
		
	}

	public void addListener(ResultsListener resultsView) {
		this.listeners.add(resultsView);
		
	}
}
