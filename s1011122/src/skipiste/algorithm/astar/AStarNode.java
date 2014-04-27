package skipiste.algorithm.astar;

import java.util.Set;

import skipiste.algorithm.NodeDecorator;
import skipiste.graph.elements.Edge;

public class AStarNode extends NodeDecorator{

	@Override
	public double getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCost(double cost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Edge> getOutboundEdges() 
	{
		return this.n.getOutboundEdges();
	}

}
