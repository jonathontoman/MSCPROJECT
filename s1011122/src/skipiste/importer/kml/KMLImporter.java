package skipiste.importer.kml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;

/**
 * 
 * Builds a graph from a KML source file.
 * 
 * @author s1011122
 * 
 */
public class KMLImporter {

	/**
	 * The SAX handler for the KML file
	 */
	private SAXParserFactory spf;
	private SAXParser saxParser;
	/**
	 * Nodes that we build up from the KML file and modify in this class.
	 */
	private List<Node> nodes;
	/**
	 * Edges that we build up from the KML file and modify in this class.
	 */
	private List<Edge> edges;
	/**
	 * Pistes that we build up from the KML file and modify in this class.
	 */
	private List<Piste> pistes;

	/**
	 * Constructor
	 * 
	 * @param handler
	 * @param kmlFile
	 */
	public KMLImporter(KMLHandler handler, String kmlFile) {

		// Configure the SAX parsing.
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		try {
			saxParser = spf.newSAXParser();
			File f = new File(kmlFile);
			InputStream inputStream = new FileInputStream(f);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			saxParser.parse(is, handler);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// Do Nothing other than print stack trace
			e.printStackTrace();
		}

		// the graph built by the KML handler will produce n number of
		// unconnected graphs, one for each named piste in the origin data.
		// we need to go through three processes to connected theses graphs.
		nodes = handler.getNodes();
		edges = handler.getEdges();
		pistes = handler.getPistes();

	}

	/**
	 * @return the nodes
	 */
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}

	/**
	 * @param edges the edges to set
	 */
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	/**
	 * @return the pistes
	 */
	public List<Piste> getPistes() {
		return pistes;
	}

	/**
	 * @param pistes the pistes to set
	 */
	public void setPistes(List<Piste> pistes) {
		this.pistes = pistes;
	}
}
