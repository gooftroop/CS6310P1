package gallhp;
import tpdohp.Tpdohp;
import tpfahp.Tpfahp;
import tpdahp.Tpdahp;
import twfahp.Twfahp;

import java.util.HashMap;

import lib.DemoHandler;
import lib.ISimulation;

public class Gallhp {
	
	private float top, right, bottom, left;
	private int latticeCount;
	private String model;

	public Gallhp(HashMap<String, Float> params, int lattices, String model) {
		this.model = model;
		this.latticeCount = lattices;
		
		this.top = params.get("top");
		this.right = params.get("right");
		this.bottom = params.get("bottom");
		this.left = params.get("left");
		
		this.chooseModel();
	}
// twdahpBtn, tpfahpBtn, twfahpBtn, tpdohpBtn
	private void chooseModel() {
		if (model == "tpdahp") {
			ISimulation simulation = new Tpdahp(new DemoHandler(latticeCount, latticeCount));		
			simulation.setup(latticeCount, latticeCount, this.left, this.right, this.top, this.bottom);
		}
		else if (model == "tpfahp"){
			ISimulation simulation = new Tpfahp(new DemoHandler(latticeCount, latticeCount));		
			simulation.setup(latticeCount, latticeCount, this.left, this.right, this.top, this.bottom);
		}
		else if (model == "twfahp") {
			ISimulation simulation = new Twfahp(new DemoHandler(latticeCount, latticeCount));		
			simulation.setup(latticeCount, latticeCount, this.left, this.right, this.top, this.bottom);
		}
		// assume tpdohp
		else {
			ISimulation simulation = new Tpdohp(new DemoHandler(latticeCount, latticeCount));		
			simulation.setup(latticeCount, latticeCount, this.left, this.right, this.top, this.bottom);
		}
		
	}

}
