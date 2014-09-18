package lib;

import gallhp.ResultsView;

public class GallhpHandler extends ResultsHandler {
	
	private String resultMatrix;
	private final int width;
	private ResultsView listener;
	
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
		//System.out.println("Index " + index);
		resultMatrix = resultMatrix.substring(0, index) + String.format("%06.2f ", temp) + resultMatrix.substring((index + 7 ));
		System.out.println("## RESULT ## \n" + resultMatrix);
		this.listener.updateResults();
	}

	@Override
	public void report() {
		// TODO Auto-generated method stub
		
	}

	public void addListener(ResultsView resultsView) {
		this.listener = resultsView;
		
	}
}
