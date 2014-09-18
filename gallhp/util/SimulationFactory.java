package util;

import tpdahp.Tpdahp;
import tpdohp.Tpdohp;
import tpfahp.Tpfahp;
import twfahp.Twfahp;
import lib.ISimulation;

public final class SimulationFactory implements ISimulationFactory {
	
	private static SimulationFactory instance = null;
	
	public static SimulationFactory getInstance() {
		if (instance == null)
			instance = new SimulationFactory();
		
		return instance;
	}
	
	private SimulationFactory() {
		/* Empty */
	}

	@Override
	public ISimulation produceSimulation(SimulationSelection simulation) {
		
		switch(simulation) {
			case TPDOHP:
				return new Tpdohp();
			case TPDAHP:
				return new Tpdahp();
			case TPFAHP:
				return new Tpfahp();
			case TWFAHP:
				return new Twfahp();
			default:
				throw new IllegalArgumentException("Invalid simulation " + simulation + " provided");
		}
	}
}
