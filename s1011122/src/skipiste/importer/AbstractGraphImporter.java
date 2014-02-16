package skipiste.importer;

import skipiste.graph.Graph;

/**
 * Abstract parent class for all GrapImporter classes.
 * @author s101122
 *
 */
public class AbstractGraphImporter 
{
	protected Graph graph;

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}	
}
