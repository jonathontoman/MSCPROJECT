package skipiste.algorithm;

import skipiste.graph.elements.GraphNode;
import skipiste.graph.elements.Node;

/**
 * An abstract decorator class that the algorithm specific classes will
 * implement in order to add their extra functionality.
 * 
 * @author s1011122
 * 
 */
public abstract class NodeDecorator implements GraphNode 
{
	protected Node n;
}
