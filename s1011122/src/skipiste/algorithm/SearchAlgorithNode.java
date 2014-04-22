package skipiste.algorithm;

import java.util.LinkedList;

import skipiste.graph.elements.Node;

public interface SearchAlgorithNode 
{
	public LinkedList<Node> getShortestPath();
	public double calculateCostToNode();
	public double getCostToNode();
	public void setCostToNode(double cost);
	
}
