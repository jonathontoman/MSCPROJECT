package skipiste.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import skipiste.algorithm.GraphNode;
import skipiste.algorithm.Path;
import skipiste.graph.elements.Node;

/**
 * Utility class to produce KML Strings
 * 
 * @author s1011122
 * 
 */
public class OutputKML {

	// KML output strings
	private static final String PREFIX = "<kml><Folder><Placemark><LineString><coordinates>";
	private static final String SUFFIX = "</coordinates></LineString></Placemark></Folder></kml>";
	private static final String OPEN = "<kml><Folder>";
	private static final String CLOSE = "</Folder></kml>";
	private static final String OPEN_POINT = "<Point>";
	private static final String OPEN_LINESTRING = "<LineString>";
	private static final String CLOSE_LINESTRING = "</LineString>";
	private static final String CLOSE_POINT = "</Point>";
	private static final String OPEN_PLACEMARK = "<Placemark>";
	private static final String CLOSE_PLACEMARK = "</Placemark>";
	private static final String OPEN_COORDS = "<coordinates>";
	private static final String CLOSE_COORDS = "</coordinates>";
	private static final String OPEN_NAME = "<name>";
	private static final String CLOSE_NAME = "</name>";

	/**
	 * Produces KML output of a list of different routes based on paths produced
	 * by the algorithms
	 * 
	 * @param paths - the paths that we want to output in kml
	 * @return - the paths in kml
	 */
	public static String outputGroupOfRoutes(List<Path> paths) {

		StringBuilder content = new StringBuilder();
		content.append(OPEN);
		for (Path p : paths) {
			content.append(OPEN_PLACEMARK);
			if (p.getName() != null) {
				content.append(OPEN_NAME);
				content.append(p.getName());
				content.append(CLOSE_NAME);
			}

			content.append(OPEN_LINESTRING);
			content.append(OPEN_COORDS);

			for (GraphNode n : p.getNodesInPath()) {
				// at latitude
				content.append(n.getLongitude());
				// at comma
				content.append(",");
				// add longitude
				content.append(n.getLatitude());
				// add comma
				content.append(",");
				// add space
				content.append(" ");

			}
			content.append(CLOSE_COORDS);
			content.append(CLOSE_LINESTRING);

			content.append(CLOSE_PLACEMARK);
		}
		content.append(CLOSE);
		return content.toString();
	}

	/**
	 * Return a String marked up with KML as a KML placemark
	 * 
	 * @param d
	 *            - the latitude of the placemak
	 * @param e
	 *            - the longitude of the placemark
	 * @return
	 */
	public static String outputPlaceMark(double d, double e) {

		return "<Placemark><name>" + d + "," + e
				+ "</name><Point><coordinates>" + d + "," + e
				+ ",</coordinates></Point></Placemark>";
	}
	
	/**
	 * Outputs KML for a list of individual longitude/latitude points.
	 * @param nodes  the nodes whoose coordinates we want to output in KML.
	 * @return the KML output showing node coordinates.
	 */
	public static String outputPlaceMarks(List<Node> nodes) {

		StringBuilder content = new StringBuilder();
		for (Node n : nodes) {
			// open placemark
			content.append(OPEN_PLACEMARK);
			// open point
			content.append(OPEN_POINT);
			// open coordinates
			content.append(OPEN_COORDS);
			// add longitude
			content.append(n.getLongitude());
			content.append(",");
			// add latitude
			content.append(n.getLatitude());
			// close coordinates
			content.append(CLOSE_COORDS);
			// close point
			content.append(CLOSE_POINT);
			// close placemark
			content.append(CLOSE_PLACEMARK);
		}
		return OPEN + content.toString() + CLOSE;
	}

	/**
	 * Produce KML string of the route described by the list of nodes
	 * 
	 * @param nodes
	 *            - the list of nodes we want to turn into a KML route
	 * @return KML String describing the route.
	 */
	public static String outputRoute(LinkedList<GraphNode> nodes) {
		StringBuilder content = new StringBuilder();

		// add the coordinates to the content of the kml
		for (GraphNode n : nodes) {
			// at latitude
			content.append(n.getLongitude());
			// at comma
			content.append(",");
			// add longitude
			content.append(n.getLatitude());
			// add comma
			content.append(",");
			// add space
			content.append(" ");
		}

		return PREFIX + content.toString() + SUFFIX;
	}

	/**
	 * Produce KML string of the route described by the list of nodes
	 * 
	 * @param nodes
	 *            - the list of nodes we want to turn into a KML route
	 * @return KML String describing the route.
	 */
	public static String outputRoutes(LinkedList<Node> nodes) {
		StringBuilder content = new StringBuilder();

		// add the coordinates to the content of the kml
		for (Node n : nodes) {
			// at latitude
			content.append(n.getLongitude());
			// at comma
			content.append(",");
			// add longitude
			content.append(n.getLatitude());
			// add comma
			content.append(",");
			// add space
			content.append(" ");
		}
		return PREFIX + content.toString() + SUFFIX;
	}
	
	/**
	 * Outputs a group of different routes in KML
	 * @param pisteList - the list of pistes which we want outputted in KML format.
	 * @return the KML outout for a group of pistes.
	 */
	public static String outputGroupOfRoutes(
			ArrayList<LinkedList<Node>> pisteList) {
		StringBuilder content = new StringBuilder();

		for (List<Node> nodes : pisteList) {
			content.append(OPEN_PLACEMARK);
			content.append(OPEN_LINESTRING);
			content.append(OPEN_COORDS);

			for (Node n : nodes) {
				// at latitude
				content.append(n.getLongitude());
				// at comma
				content.append(",");
				// add longitude
				content.append(n.getLatitude());
				// add comma
				content.append(",");
				// add space
				content.append(" ");

			}
			content.append(CLOSE_COORDS);
			content.append(CLOSE_LINESTRING);
			content.append(CLOSE_PLACEMARK);
		}
		return OPEN + content.toString() + CLOSE;
	}
}
