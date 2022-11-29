package com.skillsoft.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	
	private int vertexNumber;	
	private int weight;
	private List<Vertex> adjacencyList = new ArrayList<>();//Should use a Set instead
	
	//------------------------------------------------------------------------------
	public Vertex(int vertexNumber, int weight) {
		this.vertexNumber = vertexNumber;
		this.weight = weight;
	}
	//------------------------------------------------------------------------------
	public int getVertexNumber() {
		return vertexNumber;
	}
	//------------------------------------------------------------------------------
	public int getWeight() {
		return weight;
	}
	//------------------------------------------------------------------------------
	public void addEdge(int vertexNumber, int weight) {
		adjacencyList.add(new Vertex(vertexNumber, weight));
	}
	//------------------------------------------------------------------------------
	public List<Vertex> getAdjacentVertices(){
		return adjacencyList;
	}
	//------------------------------------------------------------------------------
	public String toString(){
		return "Vertex: "+ vertexNumber + " Weight: "+ weight;
	}
}
