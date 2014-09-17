package tpdahp;

import java.util.Arrays;

import lib.IResultsHandler;
import lib.Simulation;

public final class Tpdahp extends Simulation {
	
	private double plate[][];
	
	private double leftTemp, rightTemp, topTemp, bottomTemp;
	
	public Tpdahp(IResultsHandler rh) {
		super(rh);
	}
	
	@Override
	public void setup(int height, int width, double leftTemp, double rightTemp, double topTemp, double bottomTemp) {
		
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
		
		this.leftTemp 	= leftTemp;
		this.rightTemp 	= rightTemp;
		this.topTemp 	= topTemp;
		this.bottomTemp = bottomTemp;
		
		this.initializePlate();
	}
	
	@Override
	protected void initializePlate() {
		
		plate = new double[this.width][this.height];
		
		initializeMatrix(plate);
	}
	
	@Override
	public void run() {
		
		double newTemp = 0;
		double deviation = 0;
		double hold[] = new double[this.width];
		Arrays.fill(hold, this.topTemp);
		
		do {
			
			Arrays.fill(hold, this.topTemp);
			maxDeviation = 0.0d;
			
			// iterate through newMatrix, filling in values from oldMatrix, recoding maxDeviation;
			for ( int y = 1 ; y < this.height - 1 ; y ++ ) {
				for ( int x = 1; x < this.width - 1 ; x++ ) {
				
					newTemp = ((plate[x - 1][y] + plate[x + 1][y] + plate[x][y - 1] + plate[x][y + 1]) / 4.0d);
					
					if ((deviation = newTemp - plate[x][y]) > maxDeviation )
						maxDeviation = deviation;
					
					plate[x][y - 1] = hold[x - 1];
					hold[x - 1] = newTemp; 
					
					this.update(newTemp, (x - 1), (y - 1));
				}
			}
			
		} while (maxDeviation >= 0.01 && currIterations++ <= MAX_ITERATIONS);
		
		rh.stop();
		rh.setNumIterations(currIterations);
		rh.report();
	}
	
	private void initializeMatrix(double matrix[][]) {
		
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
					matrix[x][y] = 0.0d; 			// not an edge, the plate itself
			}
		}
	}
}
