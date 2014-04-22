package skipiste.utils.distance;

import skipiste.graph.elements.Node;

/**
 * Calculates distance between two points on a plain using standard trigonometry
 * @author s1011122
 *
 */
public class BasicDistance implements DistanceCalculator{

	@Override
	public double calculateDistanceBetweenNodes(Node node1, Node node2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double calculateDistanceBetweenCoordinates(Double x1,
			Double y1, Double x2, Double y2) 
	{
	
		double xDiff  = x2 -x1;
		double yDiff =  y2 -y1;
		
		// Negate the differences		
		double sumXY = Math.abs(yDiff) + Math.abs(xDiff);
		double square = Math.pow(sumXY,2);
		return Math.sqrt(square);
		
	}

}
