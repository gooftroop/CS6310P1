package Tpfahp;

import java.util.Arrays;

import lib.IResultsHandler;
import lib.Simulation;

public final class Tpfahp extends Simulation {

	private float plate[][];
	
	private float leftTemp, rightTemp, topTemp, bottomTemp;
	
	public Tpfahp() {
		/* Empty */
	}
	
	public Tpfahp(IResultsHandler rh, int height, int width, double topTemp, double bottomTemp, double leftTemp, double rightTemp) {
		super(rh);
		
		this.setup(height, width, topTemp, bottomTemp, leftTemp, rightTemp);
		this.initializePlate();
	}
	
	@Override
	public void setup(int width, int height, double topTemp, double bottomTemp, double leftTemp, double rightTemp) {
	
		if (height < 0 || height >= Integer.MAX_VALUE)
			throw new IllegalArgumentException("Invalid height dimension");
		
		if (width < 0 || width >= Integer.MAX_VALUE)
			throw new IllegalArgumentException("Invalid width dimension");
		
		if (leftTemp < MIN_TEMP || leftTemp > MAX_TEMP)
			throw new IllegalArgumentException("Invalid left temperature value");
		
		if (rightTemp < MIN_TEMP || rightTemp > MAX_TEMP)
			throw new IllegalArgumentException("Invalid left temperature value");
		
		if (topTemp < MIN_TEMP || topTemp > MAX_TEMP)
			throw new IllegalArgumentException("Invalid left temperature value");
		
		if (bottomTemp < MIN_TEMP || bottomTemp > MAX_TEMP)
			throw new IllegalArgumentException("Invalid left temperature value");
		
		this.height = height + 2;
		this.width = width + 2;
		
		this.leftTemp 	= (float) leftTemp;
		this.rightTemp 	= (float) rightTemp;
		this.topTemp 	= (float) topTemp;
		this.bottomTemp = (float) bottomTemp;
		
		this.initializePlate();
	}
	
	@Override
	protected void initializePlate() {
		
		plate = new float[this.width][this.height];

		
		this.initializeMatrix(plate);
	}
	
	@Override
	public void run() {
		
		double deviation = 0;
		
		float newTemp = 0;
		float hold[] = new float[this.width - 2];
		
		do {
			
			maxDeviation = 0.0d;
			
			Arrays.fill(hold, this.topTemp);
			
			// iterate through newMatrix, filling in values from oldMatrix, recoding maxDeviation;
			for ( int y = 1 ; y < this.height - 1 ; y ++ ) {
				for ( int x = 1; x < this.width - 1 ; x++ ) {
				
					newTemp = ((plate[x - 1][y] + plate[x + 1][y] + plate[x][y - 1] + plate[x][y + 1]) / 4.0f);
					
					if ((deviation = (newTemp - plate[x][y])) > maxDeviation )
						maxDeviation = deviation;
					
					this.update(newTemp, (x - 1), (y - 1));
					
					if (y == this.height - 2) {
						plate[x][y] = newTemp;
						plate[x][y - 1] = hold[x - 1];
						hold[x -1] = this.topTemp;
					} else {
						plate[x][y - 1] = hold[x - 1];
						hold[x - 1] = newTemp;
					}
				}
			}
			
		} while (maxDeviation >= MAX_DEVIATION && currIterations++ <= MAX_ITERATIONS);
		
		rh.stop();
		rh.setNumIterations(currIterations);
		rh.report();
	}
	
	private void initializeMatrix(float matrix[][]) {
		
		for (int y = 0; y < this.height; y++) {
			for ( int x = 0; x < this.width; x++) {
				
				// the corners of the matrix are are not used in the simulation, and so do not need special cases
				if (y == 0)
					matrix[x][y] = this.topTemp; 	// top row
				else if (y == (this.height - 1))
					matrix[x][y] = this.bottomTemp; // bottom row
				else if ( x == 0 )
					matrix[x][y] = this.leftTemp; 	// left column 
				else if (x == (this.width - 1))
					matrix[x][y] = this.rightTemp; 	// right column 
				else
					matrix[x][y] = new Float(0.0); 			// not an edge, the plate itself
			}
		}
	}
}