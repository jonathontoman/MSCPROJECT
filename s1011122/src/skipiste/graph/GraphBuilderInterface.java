package skipiste.graph;

import skipiste.graph.Graph;

public interface GraphBuilderInterface {
	
		
	/**
	 * Build and return a graph for the given KML file
	 * @return
	 */
	public Graph buildGraph(String KMLFile);
}
