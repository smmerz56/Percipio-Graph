package com.skillsoft.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skillsoft.datastructures.Graph.GraphType;

public class AdjacencyListGraph /*implements Graph*/{//Should change name to AdjacencySetGraph
	
//	private List<Vertex> vertexList = new ArrayList<>();
//	private int numVertices = 0;
//	private GraphType graphType = GraphType.DIRECTED;
//	
//	public AdjacencyListGraph(int numVertices, GraphType graphType) {
//		this.numVertices = numVertices;
//		
//		for(int i = 0; i < numVertices; i++) {
//			vertexList.add(new Vertex(i, 0));
//		}
//		
//		this.graphType = graphType;
//	}
//	//------------------------------------------------------------------------------
//	@Override
//	public void addEdge(int v1, int v2) {
//		addEdge(v1, v2, 1);
//	}
//	//------------------------------------------------------------------------------
//	@Override
//	public void addEdge(int v1, int v2, int weight) {
//		
//		if (v1 >= numVertices || v2 >= numVertices || v1 < 0 || v2 < 0) {
//			throw new IllegalArgumentException("Vertex number is not valid");
//		}
//		
//		vertexList.get(v1).addEdge(v2, weight);
//		
//		if(graphType == GraphType.UNDIRECTED) {
//			vertexList.get(v2).addEdge(v1, weight);
//		}
//		
//	}
//	
//	//------------------------------------------------------------------------------
//	@Override
//	public List<Vertex> getAdjacentVertices(int v){ 
//		if(v < 0 || v >= numVertices) {
//			throw new IllegalArgumentException("Vertex number is not valid");
//		}
//		
//		return vertexList.get(v).getAdjacentVertices();
//	}
//	
//	//------------------------------------------------------------------------------
//	//Gets the number of other Vertices point to it
//	@Override
//	public int getIndegree(int v) {
//		if(v < 0 || v >= numVertices) {
//			throw new IllegalArgumentException("Vertex number is not valid");
//		}
//		
//		int indegree = 0;
//		
//		for(Vertex vertex : vertexList) {
//			List<Vertex> adjacentVerteicesList = vertex.getAdjacentVertices();
//			for(Vertex adjacentVertex : adjacentVerteicesList) {
//				if(adjacentVertex.getVertexNumber() == v) {
//					indegree++;
//				}
//			}
//
//		}
//		return indegree;
//	}
//	
//	//------------------------------------------------------------------------------
//	@Override
//	public int getNumVertices() {
//		return numVertices;
//	}
//	//------------------------------------------------------------------------------
//	//Mostly used for debugging
//	@Override
//	public void displayGraph() {
//		System.out.println("Adjacency List\n");
//		
//		for(Vertex vertex : vertexList) {
//			
//			List<Vertex> adjacentVertices = vertex.getAdjacentVertices();
//			
//			System.out.println("Edges from "+ vertex.getVertexNumber()+
//					" to : "+ adjacentVertices);
//		}
//		
//	}
}
