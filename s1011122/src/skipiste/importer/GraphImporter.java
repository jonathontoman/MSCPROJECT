package skipiste.importer;

import skipiste.graph.Graph;

public interface GraphImporter {
	
	/**
	 * Import a graph from a KML representation of a ski resorts piste runs.
	 * @param KMLFile - the KMLFile describing the ski resorts piste runs
	 * @return the completed graph.
	 */
	public Graph importGraph(String KMLFile);
}
