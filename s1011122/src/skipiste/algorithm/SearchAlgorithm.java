package skipiste.algorithm;

import skipiste.graph.elements.Node;

/**
 * Interface that all search algorithms must implement
 * @author s1011122
 *
 */
public interface SearchAlgorithm {
	
	public Path findPath(Node start, Node end);
}
