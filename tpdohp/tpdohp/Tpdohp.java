package tpdohp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lib.IResultsHandler;
import lib.Simulation;
import util.Cell;

public final class Tpdohp extends Simulation {
	
	private LatticePoint root = null;
	
	private double leftTemp = 0, rightTemp = 0, topTemp = 0, bottomTemp = 0;
	
	private int height = 0, width = 0;
	
	public Tpdohp(IResultsHandler rh) {
		super(rh);
	}
	
	@Override
	public void setup(int height, int width, float leftTemp, float rightTemp, float topTemp, float bottomTemp) {
		
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
		
		this.leftTemp 	= (double) leftTemp;
		this.rightTemp 	= (double) rightTemp;
		this.topTemp 	= (double) topTemp;
		this.bottomTemp = (double) bottomTemp;
		
		this.initializePlate();
	}

	@Override
	public void initializePlate() {
		
		LatticePoint left = null, curr = null, right = null, top = null;
		
		double temp = 0;
		boolean edge = true;
		
		for (int h = 0; h < this.height; h++) {
			for (int w = 0; w < this.width; w++) {
				
				edge = (w == 0 || h == 0 || w == this.width - 1 || h == this.height - 1) ? true : false;
				
				if (h == 0) temp = this.topTemp;
				else if (w == 0) temp = this.leftTemp;
				else if (h == this.height - 1) temp = this.bottomTemp;
				else if (w == this.width - 1) temp = this.rightTemp;
				else temp = 0;
				
				curr = new LatticePoint(top, null, left, null, temp, edge, w - 1, h - 1);
				if (top != null) top.setBottom(curr);
				if (left != null) left.setRight(curr);
				
				left = curr;
				if (top != null) top = top.right;
			}
			
			top = curr;
			if (++h >= this.height) break;
			
			for (int w = this.width - 1; w >= 0; w--) {
				
				edge = (w == 0 || h == 0 || w == this.width - 1 || h == this.height - 1) ? true : false;
				
				if (h == 0) temp = this.topTemp;
				else if (w == 0) temp = this.leftTemp;
				else if (h == this.height - 1) temp = this.bottomTemp;
				else if (w == this.width - 1) temp = this.rightTemp;
				else temp = 0;
				
				curr = new LatticePoint(top, null, null, right, temp, edge, w - 1, h - 1);
				if (top != null) top.setBottom(curr);
				if (right != null) right.setLeft(curr);
				
				if (curr.x == 0 && curr.y == 0) this.root = curr;
				
				right = curr;
				if (top != null) top = top.left;
			}
			
			top = curr;
		}
	}
	
	@Override
	public void run() {
		
		Queue<LatticePoint> bfs = new LinkedList<LatticePoint>();
		
		int currIterations = 0;
		double maxDeviation = 0;
		double deviation = 0;
		
		do {
			
			maxDeviation = 0;
			
			bfs.add(this.root);
			this.root.visited(true);
			
			if ((deviation = this.root.calculateTemp()) > maxDeviation) maxDeviation = deviation;
			
			while(!bfs.isEmpty()) {
				
				LatticePoint point = bfs.remove();
				LatticePoint child = null;
				Iterator<LatticePoint> itr = point.getChildren(false);
				while(itr.hasNext()) {
					child = itr.next();
					child.visited(true);
					
					if ((deviation = child.calculateTemp()) > maxDeviation) maxDeviation = deviation;
					bfs.add(child);
				}
				
				this.update((float)point.getTemp(), point.x, point.y);
			}

			clearNodes();
			rh.setNumIterations(currIterations);
		
		} while (maxDeviation >= 0.01 && currIterations++ <= MAX_ITERATIONS);
		
		rh.stop();
		rh.setNumIterations(currIterations);
		rh.report();
	}
	
	private void clearNodes() {
		
		Queue<LatticePoint> bfs = new LinkedList<LatticePoint>();
		
		bfs.add(this.root);
		this.root.visited = false;
		this.root.swapTemp();
		
		while(!bfs.isEmpty()) {
			LatticePoint point = bfs.remove();
			LatticePoint child = null;
			
			Iterator<LatticePoint> itr = point.getChildren(true);
			while(itr.hasNext()) {
				child = itr.next();
				child.visited = false;
				child.swapTemp();
				bfs.add(child);
			}
		}
	}
	
	private final class LatticePoint implements Cell<LatticePoint> {
		
		public static final double AVG = 4.0;
		
		public final int x, y;
		
		private boolean visited;
		private double currTemp, newTemp;
		
		private final boolean isEdge;
		
		private LatticePoint top = null, bottom = null, left = null, right = null;
		
		public LatticePoint(double temp, boolean isEdge, int x, int y) {
			
			if (temp > Double.MAX_VALUE) throw new IllegalArgumentException("Invalid temp provided");
			if (x > Integer.MAX_VALUE || x < Integer.MIN_VALUE) throw new IllegalArgumentException("Invalid 'x' provided");
			if (y > Integer.MAX_VALUE || y < Integer.MIN_VALUE) throw new IllegalArgumentException("Invalid 'y' provided");
			
			this.x = x;
			this.y = y;
			
			this.setTemp(temp);
			this.visited = false;
			this.isEdge = isEdge;
		}
		
		public LatticePoint(LatticePoint top, LatticePoint bottom, LatticePoint left, LatticePoint right, double temp, boolean isEdge, int x, int y) {
			
			this(temp, isEdge, x, y);
			
			this.setTop(top);
			this.setBottom(bottom);
			this.setLeft(left);
			this.setRight(right);
		}
		
		@Override
		public void setTop(LatticePoint top) {
			
			if (top == null) return;
			this.top = top;
		}
		
		@Override
		public LatticePoint getTop() {
			return this.top;
		}
		
		@Override
		public void setBottom(LatticePoint bottom) {
			
			if (bottom == null) return;
			this.bottom = bottom;
		}
		
		@Override
		public LatticePoint getBottom() {
			return this.bottom;
		}
		
		@Override
		public void setRight(LatticePoint right) {
			
			if (right == null) return;
			this.right = right;
		}
		
		@Override
		public LatticePoint getRight() {
			return this.right;
		}
		
		@Override
		public void setLeft(LatticePoint left) {
			
			if (left == null) return;
			this.left = left;
		}
		
		@Override
		public LatticePoint getLeft() {
			return this.left;
		}
		
		@Override
		public double getTemp() {
			return new Double(this.currTemp);
		}
		
		@Override
		public void setTemp(double temp) {
			this.currTemp = temp;
		}
		
		@Override
		public double calculateTemp() {
			this.newTemp = (this.top.getTemp() + this.bottom.getTemp() + this.right.getTemp() + this.left.getTemp()) / AVG;
			return this.newTemp - this.currTemp;
		}
		
		@Override
		public void swapTemp() {
			this.currTemp = this.newTemp;
			this.newTemp = 0;
		}
		
		@Override
		public void visited(boolean visited) {
			this.visited = visited;
		}
		
		@Override
		public Iterator<LatticePoint> getChildren(boolean unvisited) {
			List<LatticePoint> ret = new ArrayList<LatticePoint>();
			
			if (!this.top.isEdge 	&& this.top.visited == unvisited) 		ret.add(this.top);
			if (!this.bottom.isEdge && this.bottom.visited == unvisited) 	ret.add(this.bottom);
			if (!this.left.isEdge 	&& this.left.visited == unvisited) 		ret.add(this.left);
			if (!this.right.isEdge 	&& this.right.visited == unvisited) 	ret.add(this.right);
			
			return ret.iterator();
		}
	}
}
