package skipiste.importer.kml;

import java.util.ArrayList;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import skipiste.graph.Difficulty;
import skipiste.graph.Edge;
import skipiste.graph.Node;
import skipiste.graph.Section;
import skipiste.utils.HaversineDistance;

/**
 * Parses KML from OpenSkiMap.org. This produces a List<LinkedList<Node>>. Each
 * entry in the List being a LinkedList of the nodes that make up a single ski
 * piste in our data.
 * 
 */
public class KMLHandler extends DefaultHandler {

	private static final String PLACEMARK = "Placemark";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String COORDINATES = "coordinates";

	/**
	 * The data set that we are populating.
	 */
	private ArrayList<LinkedList<Node>> graphData;
	/**
	 * The List of nodes that comprise a complete ski piste.
	 */
	private LinkedList<Node> piste;
	/**
	 * Flag to indicate if we are parsing the data or not.
	 */
	private boolean isParsing = false;
	/**
	 * String builder used to build up our data.
	 */
	private StringBuilder sb = new StringBuilder();
	/**
	 * The name of the current piste run we are creating data for.
	 */
	private String pisteName;
	/**
	 * The description of the current piste we are creating data for.
	 */
	private String description;
	
	/**
	 * The difficulty of the ski piste we are currently building.
	 */
	private Difficulty diff;

	public void startDocument() throws SAXException {
		graphData = new ArrayList<LinkedList<Node>>();
	}

	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		// We have started reading data about a new piste.
		if (qName.equalsIgnoreCase(PLACEMARK)) {
			piste = new LinkedList<Node>();
		}

		// We have started reading data about the piste name
		if (qName.equalsIgnoreCase(NAME)) {
			if (piste != null) {

				pisteName = new String();
				isParsing = true;
			}
		}
		// We have started reading data about the piste description
		if (qName.equalsIgnoreCase(DESCRIPTION)) {
			if (piste != null) {

				isParsing = true;
			}
		}
		// We have started reading data about the piste coordinates
		if (qName.equalsIgnoreCase(COORDINATES)) {
			if (piste != null) {
				isParsing = true;
			}
		}
	}

	@Override
	public void characters(char[] chars, int i, int i1) throws SAXException {
		if (isParsing) {
			sb.append(new String(chars, i, i1));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		// We have moved onto the next set of coordinates for a piste run. Add
		// the linkedlist that represents an entire piste run to the dataset.
		if (qName.equalsIgnoreCase(PLACEMARK)) {
			if (piste != null) {
				graphData.add(piste);
			}
		}

		// We have finished reading in the piste name
		if (qName.equalsIgnoreCase(NAME)) {
			if (piste != null) {
				pisteName = sb.toString();
				sb = new StringBuilder();
				isParsing = false;
			}
		}
		// We have finished reading the piste description
		if (qName.equalsIgnoreCase(DESCRIPTION)) {
			if (piste != null) {

				description = sb.toString();
				isParsing = false;
				sb = new StringBuilder();
			}
		}
		// We have finished reading in the coordinate list for a piste run,
		// create the nodes and add these to the linked list representing the
		// entire piste run.
		if (qName.equalsIgnoreCase(COORDINATES)) {

			if (piste != null) {

				isParsing = false;

				// In the KML dataset we have a string
				// longitude,latitude,altitude[whitespace] to represent the
				// coordinates of the data. We know in our data set that the
				// altitude is always marked as zero regardless of the actual
				// altitude, and that the coordinate sets are separated by a
				// whitespace and that the data within the set is comma
				// delimited.
				String coordinates = sb.toString();
				sb = new StringBuilder();
				String trimmed = coordinates.trim();
				String[] coords = trimmed.split("\\s+");

				for (int i = 0; i < coords.length; i++) {
					Node n = new Node();
					String[] s = coords[i].split(",");
					n.setLattitude(new Double(s[0]));
					n.setLongitude(new Double(s[1]));

					if (i == 0) {
						// if this is the first node in the piste mark it as
						// such
						n.setSection(Section.START);

					} else if (coords.length - i == 1) {
						// if this is the last node in the piste mark it as such
						n.setSection(Section.END);
					} else {
						// otherwise it is a mid point.
						n.setSection(Section.MID);
					}
				}

				// From this list of nodes that make up a piste we can infer the
				// edges
				buildEdges(piste);

			}
		}

	}

	/**
	 * Builds the edges between the nodes of a piste.
	 * 
	 * @param piste
	 *            - the linkedList<Node> that represents a ski piste.
	 */
	private void buildEdges(LinkedList<Node> piste) {
		// Keep track of the nodes
		Node origin = null;
		Node terminus = null;
		
		for (Node n : piste)
		{
			// This node is the terminus for the edge originating from the previous node
			terminus = n;
			// If there was an origin node we need to build an edge between them
			if (origin != null)
			{
				Edge e = new Edge();
				// Calcualte the weight of this edge
				// We will make the weight the distance in km between the origin and terminus nodes.
				e.setWeight(HaversineDistance.calculateDistance(origin, terminus));
				
				// Add this to the nodes
				origin.getOutboudEdges().add(e);
				terminus.getInboundEdges().add(e);
			}			
			// set this node as the origin for the next time round the loop
			origin = n;		
		}				
	}

	public ArrayList<LinkedList<Node>> getGraphData() {
		return graphData;
	}

	public void setGraphData(ArrayList<LinkedList<Node>> graphData) {
		this.graphData = graphData;
	}
}
