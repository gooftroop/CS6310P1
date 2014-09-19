package Twfahp;

import java.util.Arrays;

import lib.IResultsHandler;
import lib.Simulation;

public final class Twfahp extends Simulation {
	
	private Float plate[][];
	
	private Float leftTemp, rightTemp, topTemp, bottomTemp;
	
	public Twfahp() {
		/* Empty */
	}
	
	public Twfahp(IResultsHandler rh, int height, int width, double topTemp, double bottomTemp, double leftTemp, double rightTemp) {
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
		
		this.leftTemp 	= new Float(leftTemp);
		this.rightTemp 	= new Float(rightTemp);
		this.topTemp 	= new Float(topTemp);
		this.bottomTemp = new Float(bottomTemp);
		
		this.initializePlate();
	}
	
	@Override
	protected void initializePlate() {
		
		plate = new Float[this.width][this.height];
		
		initializeMatrix(plate);
	}
	
	@Override
	public void run() {
		
		double deviation = 0;
		
		Float newTemp = new Float(0);
		Float hold[] = new Float[this.width - 2];
		
		do {
			
			maxDeviation = 0.0d;
			
			Arrays.fill(hold, this.topTemp);
			
			// iterate through newMatrix, filling in values from oldMatrix, recoding maxDeviation;
			for ( int y = 1 ; y < this.height - 1 ; y ++ ) {
				for ( int x = 1; x < this.width - 1 ; x++ ) {
				
					newTemp = new Float(((plate[x - 1][y] + plate[x + 1][y] + plate[x][y - 1] + plate[x][y + 1]) / 4.0f));
					
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
	
	private void initializeMatrix(Float matrix[][]) {
		
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
