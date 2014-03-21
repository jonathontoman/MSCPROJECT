package skipiste.importer.kml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import skipiste.graph.elements.Difficulty;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.graph.elements.Section;

/**
 * Parses KML from OpenSkiMap.org. This produces a List<LinkedList<Node>>. Each
 * entry in the List being a LinkedList of the nodes that make up a single ski
 * piste in our data.
 * 
 */
public class SkiMapHandler extends KMLHandler {

	private static final String PLACEMARK = "Placemark";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String COORDINATES = "coordinates";

	/**
	 * The nodes that we build from the KML
	 */
	private ArrayList<Node> nodes;
	/**
	 * The edges that we build from the KML
	 */
	private ArrayList<Edge> edges;
	/**
	 * The pistes that are represented in the KML
	 */
	private ArrayList<Piste> pistes;
	/**
	 * The piste node we are building at any one time
	 */
	private Piste piste;
	
	/**
	 *Used to modify duplicate piste names so they are unique.
	 */
	private int i;

	/**
	 * Flag to indicate if we are parsing the data or not.
	 */
	private boolean isParsing = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see skipiste.importer.kml.KMLHandlerInterface#getPistes()
	 */
	@Override
	public ArrayList<Piste> getPistes() {
		return pistes;
	}

	/**
	 * @param pistes
	 *            the pistes to set
	 */
	public void setPistes(ArrayList<Piste> pistes) {
		this.pistes = pistes;
	}

	/**
	 * String builder used to build up our data.
	 */
	private StringBuilder sb = new StringBuilder();

	/**
	 * The difficulty of the ski piste we are currently building.
	 */
	private Difficulty difficulty;

	public void startDocument() throws SAXException {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		pistes = new ArrayList<Piste>();
		i=0;
	}

	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		// We have started reading data about a new piste.
		if (qName.equalsIgnoreCase(PLACEMARK)) {
			piste = new Piste();
		}

		// We have started reading data about the piste name
		if (qName.equalsIgnoreCase(NAME)) {
			if (piste != null) {

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
				pistes.add(piste);
				piste = null;
			}
		}

		// We have finished reading in the piste name
		if (qName.equalsIgnoreCase(NAME)) {
			if (piste != null) {
				// In this dataset we sometimes see runs split into more than
				// one set of coordinates, so we could for example have already
				// processed a piste named "Piste" and then come accross another
				// piece of xml datafor a piste also named "Piste", as we can
				// not be sure that one is just a continuation of the other we
				// suffix an "X" onto the piste name if another piste has
				// already been produced with the same name.
				
			
				for ( Piste p : pistes)
				{
					if (sb.toString().equalsIgnoreCase(p.getName()))
					{
						// append the int i to the name so we make it unique
						sb.append(i);
						// incremeent so it is unique for the next time.
						i++;
						break;
					}
				}
				piste.setName(sb.toString());
				sb = new StringBuilder();
				isParsing = false;
			}
		}
		// We have finished reading the piste description
		if (qName.equalsIgnoreCase(DESCRIPTION)) {
			if (piste != null) {

				// The description element of the kml indicates the difficulty
				// of the piste
				// This is delimited in the string by a ":", String after ":"
				// being the difficulty;
				String description = sb.toString();
				String[] tmp = description.split(":");
				String diff = tmp[0];

				// Java 7 can do switch statements on Strings! What an exciting
				// modern world we live in.
				switch (diff) {
				case "Easy":
					piste.setDifficulty(Difficulty.BLUE);
					break;
				case "Intermediate":
					piste.setDifficulty(Difficulty.RED);
					break;
				case "Difficulty":
					piste.setDifficulty(Difficulty.BLACK);
					break;
				}

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

				// Keep track of the previous node in the arraylist so we can
				// build edges
				Node origin = null;
				for (int i = 0; i < coords.length; i++) {
					Node n = new Node();
					String[] s = coords[i].split(",");
					n.setLongitude(new Double(s[0]));
					n.setLattitude(new Double(s[1]));
					n.getPistes().add(piste);

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
					// Add this to the list of nodes
					nodes.add(n);
					// Add this node to the piste
					piste.getNodes().add(n);

					if (origin != null) {
						buildEdge(origin, n, edges, piste);
					}
					origin = n;
				}
			}
		}
	}

	/**
	 * Builds an edge between two nodes
	 * 
	 * @param origin
	 *            - the origin node of this edge
	 * @param terminus
	 *            - the terminus node of this edge
	 * @param edges
	 *            - the list of edges that we will add the newly build edge to
	 * @param piste
	 *            - the piste this edge will be part of.
	 */
	private void buildEdge(Node origin, Node terminus, List<Edge> edges,
			Piste piste) {

		if (origin != null) {

			Edge e = new Edge();
			// Calculate the weight of this edge
			// We will make the weight the distance in km between the origin
			// and terminus nodes.
			// we will set the weight of all edges later.
			e.setDiff(difficulty);

			// Add this to the nodes
			origin.getOutboudEdges().add(e);
			terminus.getInboundEdges().add(e);
			// set this to and from values on the edge
			e.setFrom(origin);
			e.setTo(terminus);
			edges.add(e);
			e.setPiste(piste);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skipiste.importer.kml.KMLHandlerInterface#getNodes()
	 */
	@Override
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skipiste.importer.kml.KMLHandlerInterface#getEdges()
	 */
	@Override
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
}
