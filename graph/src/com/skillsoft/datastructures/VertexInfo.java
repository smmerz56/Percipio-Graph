package com.skillsoft.datastructures;

public class VertexInfo {
	private int vertexId;
	private int distance;//shortest distance so far from the source node
	
	public VertexInfo(int vertexId, int distance) {
		this.vertexId = vertexId;
		this.distance = distance;
	}

	public int getVertexId() {
		return vertexId;
	}

	public void setVertexId(int vertexId) {
		this.vertexId = vertexId;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Vertex: " + vertexId + " Edge weight: " + distance;
	}
	
	
	
}
