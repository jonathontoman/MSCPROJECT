package skipiste.importer.kml;

import skipiste.graph.Graph;
import skipiste.importer.AbstractGraphImporter;
import skipiste.importer.GraphImporter;

/**
 * 
 * Builds a graph from a KML source file.
 * @author s1011122
 *
 */
public class KMLToGraph extends AbstractGraphImporter implements GraphImporter
{
	/**
	 * File location of the KML file to import and generate a graph.
	 */
	private  String kmlFile;
	
	public KMLToGraph(String kmlFile)
	{
		this.kmlFile = kmlFile;
	}

	@Override
	public Graph importGraph() {

		// Get the Nodes from the KML file
		// Build the Edges for the nodes
		
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
