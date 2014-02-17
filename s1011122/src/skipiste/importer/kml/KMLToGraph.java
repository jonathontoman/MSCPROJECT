package skipiste.importer.kml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import skipiste.graph.Graph;
import skipiste.importer.AbstractGraphImporter;
import skipiste.importer.GraphImporter;

/**
 * 
 * Builds a graph from a KML source file.
 * 
 * @author s1011122
 * 
 */
public class KMLToGraph extends AbstractGraphImporter implements GraphImporter {
	/**
	 * The SAX handler for the KML file
	 */
	private KMLHandler handler;
	private SAXParserFactory spf;
	private SAXParser saxParser;
	private XMLReader xmlReader;

	public KMLToGraph() {
		// Configure the SAX parsing.
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		try {
			saxParser = spf.newSAXParser();
			handler = new KMLHandler();
			xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(handler);

		} catch (ParserConfigurationException | SAXException e) {
			// Do Nothing other than print stack trace
			e.printStackTrace();
		}

	}

	@Override
	public Graph importGraph(String kmlFile) {

		try {
			xmlReader.parse(kmlFile);
		} catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graph;

		// mergeNodes();
		// graph;
	}

	/**
	 * Merge nodes that are within 10m of each other to reduce the types of
	 * node.
	 * 
	 * @param g
	 */
	private void mergeNodes(Graph g) {

	}

}
