package skipiste.algorithm;

import skipiste.graph.elements.GraphNode;

/**
 * Interface that all search algorithms must implement
 * @author s1011122
 *
 */
public interface SearchAlgorithm {
	
	public Path findPath(GraphNode start, GraphNode end);
}
