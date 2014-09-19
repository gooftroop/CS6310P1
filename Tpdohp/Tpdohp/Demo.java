package Tpdohp;

import lib.AbstractPlateDemo;
import lib.DemoHandler;
import lib.ISimulation;

public class Demo extends AbstractPlateDemo {
	
	public static void main(String...args) {
		configureOpts();
		parseArgs(args);
		
		int width 		= Integer.parseInt(options.get("-d"));
		int height 		= Integer.parseInt(options.get("-d"));
		
		double left 	= Double.parseDouble(options.get("-l"));
		double right 	= Double.parseDouble(options.get("-r"));
		double top 		= Double.parseDouble(options.get("-t"));
		double bottom 	= Double.parseDouble(options.get("-b"));
		
		ISimulation simulation = new Tpdohp(new DemoHandler(width, height), width, height, top, bottom, left, right);
		
		System.out.format("Starting %d x %d plate simulation...\n", width, height);
		simulation.run();
	}
}