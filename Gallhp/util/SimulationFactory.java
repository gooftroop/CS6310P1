package util;

import Tpdahp.Tpdahp;
import Tpdohp.Tpdohp;
import Tpfahp.Tpfahp;
import Twfahp.Twfahp;
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
