package skipiste.graph;

import skipiste.graph.Graph;

/**
 * Interface to guarantee essential methods are implemented by GraphBuilding classes.
 * @author s1011122
 */
public interface GraphBuilderInterface {
	
		
	/**
	 * Build and return a graph for the given KML file
	 * @return
	 */
	public Graph buildGraph(String KMLFile);
}
