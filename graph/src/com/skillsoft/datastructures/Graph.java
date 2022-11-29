package com.skillsoft.datastructures;

import java.util.List;

public interface Graph {
	
	enum GraphType{
		DIRECTED,
		UNDIRECTED
	}
	
	void addEdge(int v1, int v2);
	
	//NOTE: For an undirected graph, we assume that the weight
	// in both directions is the same
	void addEdge(int v1, int v2, int weight);
	
	List<Integer> getAdjacentVertices(int v);//Needs to be List<Vertex> for AdjacentListGraph to work
	
	int getNumVertices();
	
	int getIndegree(int v);
	
	void displayGraph();
}
