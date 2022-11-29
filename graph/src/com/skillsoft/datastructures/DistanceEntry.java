package com.skillsoft.datastructures;

public class DistanceEntry {
	private int distance;//of current node to current node
	private int lastVertex;//last vertex that was seen while calculating the distance from the source node
	
	public DistanceEntry(){
		//The initial distance to all nodes is assumed infinite
		distance = Integer.MAX_VALUE;//Or -1 for unweighted graphs
		lastVertex = -1;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getLastVertex() {
		return lastVertex;
	}

	public void setLastVertex(int lastVertex) {
		this.lastVertex = lastVertex;
	}
}
