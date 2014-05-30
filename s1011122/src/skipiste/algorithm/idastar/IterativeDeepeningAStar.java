package skipiste.algorithm.idastar;

import skipiste.algorithm.AbstractSearchAlgorithm;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.utils.distance.DistanceCalculator;
import skipiste.utils.distance.HaversineDistance;
/**
 * The Iterative Deepening A* (IDA*)  Algorithm.
 * @author s1011122
 *
 */
public class IterativeDeepeningAStar extends
		AbstractSearchAlgorithm<IDAStarNode> {

	/**
	 * Calculator to calculate distance between nodes.
	 */
	DistanceCalculator calc;

	/**
	 * The local threshold value.
	 */
	private double localThreshold;
	/**
	 * The global threshold value;
	 */
	private double globalThreshold;

	/**
	 * The best path to the destination node.
	 */
	boolean pathFound;
	 
	int nodes;


	/**
	 * Run AStar's algorithm against our graph.
	 * 
	 * @param s
	 *            - the start Node
	 * @param t
	 *            - the target Node.
	 */
	public void execute() {

		calc = new HaversineDistance();
		globalThreshold = calc.calculateDistanceBetweenCoordinates(
				start.getLongitude(), start.getLatitude(), end.getLongitude(),
				end.getLatitude());
		pathFound = false;
		// set cost to start node
		start.setCost(0);	
		nodes =0;
		
		while (pathFound == false && globalThreshold < Double.MAX_VALUE)
		{			 
			// set local threshold value.
			localThreshold = globalThreshold;
			// reset global threshold to infinity
			globalThreshold = Double.MAX_VALUE;
			pathFound = ida(start, start.getCost(), localThreshold);
		}
	}

	protected boolean ida(IDAStarNode currentNode, double pathLength,
			double upperBound) 
	{
		if (currentNode.equals(end))
		{			
			end = currentNode;
			return true;
		}
		
		// increment node expansion counter
		nodeCount++;
		for (Edge e : currentNode.getOutboundEdges()) {	
			// now we need to relax the PisteSections, examine each
			// destination Node of these PisteSections.

			IDAStarNode prospectiveNode = new IDAStarNode(e.getTo());
			// Set the heuristic value;
			prospectiveNode.setHeuristic(calc
					.calculateDistanceBetweenCoordinates(
							prospectiveNode.getLongitude(),
							prospectiveNode.getLatitude(),
							end.getLongitude(), end.getLatitude()));

							
			// set the cost of getting to n from current Node u
			prospectiveNode.setCost(e.getWeight() + currentNode.getCost());
			if (prospectiveNode.fValue() > localThreshold)
			{
				if (prospectiveNode.fValue() < globalThreshold)
				{
					// set the new global threshold
					globalThreshold = prospectiveNode.fValue();
				}
			}
			else
			{
				prospectiveNode.setPrevious(currentNode);
				
				boolean found = ida(prospectiveNode, prospectiveNode.getCost(), upperBound); 
				if (found)
				{
					return true;
				}
			}	
		}		
		return false;
	}

	@Override
	protected IDAStarNode buildSpecificNode(Node n) {
		return new IDAStarNode(n);
	}

}
