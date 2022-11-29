package com.skillsoft.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.skillsoft.datastructures.Graph.GraphType;

public class Main {
	//------------------------------------------------------------------------------
	private static Map<Integer, DistanceEntry> buildDistanceTable(Graph graph, int source){
		
		Map<Integer, DistanceEntry> distanceTable = new HashMap<>();
		
		PriorityQueue<VertexInfo> queue = new PriorityQueue<>(new Comparator<VertexInfo>() {
			@Override
			public int compare(VertexInfo v1, VertexInfo v2) {
				return ((Integer) v1.getDistance()).compareTo(v2.getDistance());
			}
		});
		
		for(int j = 0; j < graph.getNumVertices(); j++) {
			distanceTable.put(j, new DistanceEntry());
		}
		
		distanceTable.get(source).setDistance(0);
		distanceTable.get(source).setLastVertex(source);
		
		VertexInfo sourceVertexInfo = new VertexInfo(source, 0);
		queue.add(sourceVertexInfo);
		
		Map<Integer, VertexInfo> vertexInfoMap = new HashMap<>();
		vertexInfoMap.put(source, sourceVertexInfo);
		
		while(!queue.isEmpty()) {
			VertexInfo vertexInfo = queue.poll();
			
			int currentVertex = vertexInfo.getVertexId();
			
			for(Integer neighbor : graph.getAdjacentVertices(currentVertex)) {
				
				//Note: Get the new distance, account for the weighted edge.
				int distance = distanceTable.get(currentVertex).getDistance()
						+ graph.getWeightedEdge(currentVertex, neighbor);
				
				//NOTE: If we find a new shortest path to the neighbor update
				// the distance and the last vertex
				if(distanceTable.get(neighbor).getDistance() > distance) {
					
					distanceTable.get(neighbor).setDistance(distance);
					distanceTable.get(neighbor).setLastVertex(currentVertex);
					
					//NOTE: We've found a new short path to the neighbor so remove
					//the old node from the priority queue.
					VertexInfo neighborVertexInfo = vertexInfoMap.get(neighbor);
					
					if(neighborVertexInfo != null) {
						queue.remove(neighborVertexInfo);
					}
					//NOTE: Add the neighbor back with a new updated distance
					neighborVertexInfo = new VertexInfo(neighbor, distance);
					
					queue.add(neighborVertexInfo);
					vertexInfoMap.put(neighbor, neighborVertexInfo);
				}
			}
		}
		return distanceTable;
	}
	//------------------------------------------------------------------------------
	public static void shortestPath(Graph graph, int source, int destination) {
		
		Map<Integer, DistanceEntry> distanceTable = buildDistanceTable1(graph, source);
		
		Stack<Integer> stack = new Stack<>();
		stack.push(destination);
		
		int previousVertex = distanceTable.get(destination).getLastVertex();
		
		while(previousVertex != -1 && previousVertex != source) {
			 stack.push(previousVertex);
			 previousVertex = distanceTable.get(previousVertex).getLastVertex();
		}
		
		if(previousVertex == -1) {
			System.out.println("There is no path from node: "+source
					+ " to node: "+destination);
		}
		else {
			System.out.print("\n\nShortest path is "+source);
			
			while (!stack.isEmpty()) {
				System.out.print(" -> "+stack.pop());
			}
			System.out.println("\n\nShortest Path Unweighted DONE");
		}
	}
	//------------------------------------------------------------------------------
	private static Map<Integer, DistanceEntry> buildDistanceTable1(Graph graph, int source){
		
		Map<Integer, DistanceEntry> distanceTable = new HashMap<>();
		
		for(int j = 0; j < graph.getNumVertices(); j++) {
			distanceTable.put(j, new DistanceEntry());
		}
		
		distanceTable.get(source).setDistance(0);
		distanceTable.get(source).setLastVertex(source);
		
		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(source);
		
		while(!queue.isEmpty()) {
			int currentVertex = queue.pollFirst();
			
			for(int i : graph.getAdjacentVertices(currentVertex)) {
				
				int currentDistance = distanceTable.get(i).getDistance();
				
				if(currentDistance == -1) {
					currentDistance = 1 + distanceTable.get(currentVertex).getDistance();
					
					distanceTable.get(i).setDistance(currentDistance);
					distanceTable.get(i).setLastVertex(currentVertex);
					
					//NOTE: Enqueue the neighbor only if it has other adjacent vertices.
					if(!graph.getAdjacentVertices(i).isEmpty()) {
						queue.add(i);
					}
				}
			}
		}
		return distanceTable;
	}
	//------------------------------------------------------------------------------
	//Used for course/task scheduling
	public static List<String> order(List<String> courseList, Map<String, List<String>> prereqs){
		
		Graph courseGraph = new AdjacencyMatrixGraph(courseList.size(), Graph.GraphType.DIRECTED);
		
		Map<String, Integer> courseIdMap = new HashMap<>();
		Map<Integer, String> idCourseMap = new HashMap<>();
		
		for(int i = 0; i < courseList.size(); i++) {
			
			courseIdMap.put(courseList.get(i), i);			
			idCourseMap.put(i, courseList.get(i));
		}
			
		for(Map.Entry<String, List<String>> prereq : prereqs.entrySet()) {
			
			for(String course : prereq.getValue()) {
				courseGraph.addEdge(courseIdMap.get(prereq.getKey()), courseIdMap.get(course));
			}
		}
		
		List<Integer> courseIdList = sort(courseGraph);
		
		List<String> courseScheduleList = new ArrayList<>();
		
		for(int courseId : courseIdList) {
			courseScheduleList.add(idCourseMap.get(courseId));
		}
		
		return courseScheduleList;
	}
	//------------------------------------------------------------------------------
	public static List<Integer> sort(Graph graph){
		
		LinkedList<Integer> queue = new LinkedList<>();
		
		Map<Integer, Integer> indegreeMap = new HashMap<>();
		
		for(int vertex = 0; vertex < graph.getNumVertices(); vertex++) {
			int indegree = graph.getIndegree(vertex);
			
			indegreeMap.put(vertex, indegree);
			
			if(indegree == 0) {
				queue.add(vertex);
			}
		}
			
		List<Integer> sortedList = new ArrayList<>();
		
		while(!queue.isEmpty()) {
			//Note: If more than one element exists then it means that the graph
			// has more than one topological sort solution.
			
			int vertex = queue.remove();
			
			//Note: This is the equivalent of processing the node/list
			sortedList.add(vertex);
			
			List<Integer> adjacenetVertices = graph.getAdjacentVertices(vertex);
			
			for(int adjacentVertex : adjacenetVertices) {
				int updatedIndegree = indegreeMap.get(adjacentVertex) - 1;
				
				indegreeMap.put(adjacentVertex, updatedIndegree);
				
				if(updatedIndegree == 0) {
					queue.add(adjacentVertex);
				}
			}
		}
		
		if(sortedList.size() != graph.getNumVertices()) {
			throw new RuntimeException("The graph had a cycle!");
		}
		
		return sortedList;
	}
	//------------------------------------------------------------------------------
	public static void depthFirstTraversal(Graph graph, boolean[] visited, int currentVertex) {
		if(visited[currentVertex]) {
			return;
		}
		
		visited[currentVertex] = true;
		
		List<Integer> list = graph.getAdjacentVertices(currentVertex);
		
		for(int vertex : list) {
			depthFirstTraversal(graph, visited, vertex);
		}
		
		System.out.print(currentVertex + "->");
	}
	//------------------------------------------------------------------------------
	public static void breadthFirstTraversal(Graph graph, boolean[] visited, int currentVertex) {
		
		Queue<Integer> queue = new LinkedList<>();
		queue.add(currentVertex);
		
		while(!queue.isEmpty()) {
			int vertex = queue.remove();
			
			if(visited[vertex]) {
				continue;
			}
			
			System.out.print(vertex + "->");
			visited[vertex]=true;
			
			List<Integer> list = graph.getAdjacentVertices(vertex);
			
			for(int v : list) {
				if(!visited[v]) { 
					queue.add(v);
				}
			}
		}
		
	}
	public static void main(String[] args) {
		
		//------------------------------------------------------------------------------
//		Graph graph = new AdjacencyMatrixGraph(8, GraphType.DIRECTED);
		Graph graph = new AdjacencyMatrixGraph(8, GraphType.UNDIRECTED);
		graph.addEdge(2,7);
		graph.addEdge(3,0);
		graph.addEdge(0,4);
		graph.addEdge(0,1);
		graph.addEdge(2,1);
		graph.addEdge(1,3);
		graph.addEdge(3,5);
		graph.addEdge(6,3);
		graph.addEdge(4,7);
		
		graph.addEdge(0,7);//added to see if if finds a shorter path

		graph.displayGraph();
		shortestPath(graph, 1, 7);
		//------------------------------------------------------------------------------
		
		//------------------------------------------------------------------------------
//		List<String> courses = new ArrayList<>();
//		
//		courses.add("CSS100");
//		courses.add("CSS101");
//		courses.add("CSS102");
//		courses.add("CSS103");
//		courses.add("CSS104");
//		courses.add("CSS105");
//		courses.add("CSS240");
//		
//		Map<String, List<String>> prereqs = new HashMap<>();
//		List<String> list = new ArrayList<>();
//		
//		list.add("CSS101");
//		list.add("CSS102");
//		list.add("CSS103");
//		prereqs.put("CSS100", list);
//		
//		list = new ArrayList<>();
//		list.add("CSS104");
//		prereqs.put("CSS101", list);
//		
//		list = new ArrayList<>();
//		list.add("CSS105");
//		prereqs.put("CSS103", list);
//		
//		list = new ArrayList<>();
//		list.add("CSS240");
//		prereqs.put("CSS102", list);
//		
//		List<String> courseSchedule = order(courses, prereqs);
//		System.out.println("Valid schedule for CS Students: "+courseSchedule);
		//------------------------------------------------------------------------------
		
		
//		Graph graph = new AdjacencyMatrixGraph(8, GraphType.DIRECTED);
//		Graph graph = new AdjacencyMatrixGraph(8, GraphType.UNDIRECTED);
		
//		graph.addEdge(1,0, 2);
//		graph.addEdge(1,2, 1);
//		graph.addEdge(1,5, 3);
//		
//		graph.addEdge(3,4);
//		
//		graph.addEdge(2,7);
//		graph.addEdge(2,4);
//		graph.addEdge(2,3, 5);
//		
//		graph.addEdge(5,6, 4);
//		
//		graph.addEdge(6,3, 2);
		
//		graph.addEdge(1,0);
//		graph.addEdge(1,2);
//		graph.addEdge(1,5);
//		
//		graph.addEdge(3,4);
//		
//		graph.addEdge(2,7);
//		graph.addEdge(2,4);
//		graph.addEdge(2,3);
//		
//		graph.addEdge(5,6);
//		
//		graph.addEdge(6,3);
		
//		graph.addEdge(1,0);
//		graph.addEdge(1,2);
//		graph.addEdge(1,5);
//		
//		graph.addEdge(3,4);
//		
//		graph.addEdge(2,7);
//		graph.addEdge(2,4);
//		graph.addEdge(2,3);
//		
//		graph.addEdge(5,6);
//		
//		graph.addEdge(6,3);
//		
//		graph.addEdge(4,6);
//		graph.addEdge(6,7);
//		graph.addEdge(7,3);
		
//		graph.addEdge(2,7);
//		graph.addEdge(0,3);
//		graph.addEdge(0,4);
//		graph.addEdge(0,1);
//		graph.addEdge(2,1);
//		graph.addEdge(1,3);
//		graph.addEdge(3,5);
//		graph.addEdge(3,6);
//		graph.addEdge(4,7);
//
//		graph.displayGraph();
//		
//		System.out.println("\n-----------------------------------------\n");
//		System.out.println(sort(graph));
//		System.out.println("\n-----------------------------------------\n");
		
//		System.out.println("\nDepthFirstUndirectedAndCycles-----------------------------------------\n");
//		boolean[] visited = new boolean[graph.getNumVertices()];
//		for(int i = 0; i <graph.getNumVertices(); i++) {
//			depthFirstTraversal(graph, visited, i);
//		}
//		System.out.println("\n\nDepthFirst-----------------------------------------\n");
		
//		System.out.println("\nDepthFirstDirected-----------------------------------------\n");
//		boolean[] visited = new boolean[graph.getNumVertices()];
////		depthFirstTraversal(graph, visited, 1);
//		depthFirstTraversal(graph, visited, 6);
//		System.out.println("\n\n-----------------------------------------");
		
//		System.out.println("\nDepthFirstTraversal-----------------------------------------");
//		boolean[] visited = new boolean[graph.getNumVertices()];
//		for(int i = 0; i <graph.getNumVertices(); i++) {
//			breadthFirstTraversal(graph, visited, i);
//		}
//		System.out.println("\n\n-----------------------------------------");
		
//		System.out.println("\nBreadthFirstTraversal-----------------------------------------");
//		boolean[] visited = new boolean[graph.getNumVertices()];
//		breadthFirstTraversal(graph, visited, 2);
//		System.out.println("\n-----------------------------------------");
		
//		System.out.println("\nIndegree of 1: " + graph.getIndegree(1));
//		
//		System.out.println("\nIndegree of 3: " + graph.getIndegree(3));
//		
//		System.out.println("\nIndegree of 5: " + graph.getIndegree(5));
		
//		
//		System.out.println("\nVertices adjacent to 1: " + graph.getAdjacentVertices(1));
//		
//		System.out.println("\nVertices adjacent to 2: " + graph.getAdjacentVertices(2));
//		
//		System.out.println("\nVertices adjacent to 5: " + graph.getAdjacentVertices(6));
//
//		System.out.println("\nVertices adjacent to 7: " + graph.getAdjacentVertices(7));
		
		
//		Graph graph = new AdjacencyListGraph(8, GraphType.DIRECTED);
//		Graph graph = new AdjacencyListGraph(8, GraphType.UNDIRECTED);
//		
//		graph.addEdge(1,0, 2);
//		graph.addEdge(1,2, 1);
//		graph.addEdge(1,5, 3);
//		
//		graph.addEdge(3,4);
//		
//		graph.addEdge(2,7);
//		graph.addEdge(2,4);
//		graph.addEdge(2,3, 5);
//		
//		graph.addEdge(5,6, 4);
//		
//		graph.addEdge(6,3, 2);
		
//		graph.displayGraph();
		
//		System.out.println("\nIndegree of 1: " + graph.getIndegree(1));
//		
//		System.out.println("\nIndegree of 3: " + graph.getIndegree(3));
//		
//		System.out.println("\nIndegree of 5: " + graph.getIndegree(5));
		
//		System.out.println("\nVertices adjacent to 1: " + graph.getAdjacentVertices(1));
//		
//		System.out.println("\nVertices adjacent to 2: " + graph.getAdjacentVertices(2));
//		
//		System.out.println("\nVertices adjacent to 5: " + graph.getAdjacentVertices(6));
//
//		System.out.println("\nVertices adjacent to 7: " + graph.getAdjacentVertices(7));	
		
		
	}
	
}
