package skipiste.algorithm;

import skipiste.graph.elements.Node;

/**
 * Interface that all search algorithms must implement
 * 
 * @author s1011122
 * 
 */
public interface SearchAlgorithm {

	/**
	 * Finds a path from the start node to the end node, if no route exists the
	 * path will consist only of the end node.
	 * 
	 * @param start
	 *            - the start node for the search
	 * @param end
	 *            - the end node for the search
	 * @return a Path.
	 */
	public Path findPath(Node start, Node end);
}
