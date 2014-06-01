package skipiste.importer.kml;

import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;

import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;

/**
 * Abstract class to be extended by classes handling KML files.
 * @author s101122
 *
 */
public abstract class KMLHandler  extends DefaultHandler{

	/**
	 * Get the Pistes built from the KML
	 * @return
	 */
	public abstract ArrayList<Piste> getPistes();

	/**
	 * Get the Nodes built from the KML
	 * @return
	 */
	public abstract ArrayList<Node> getNodes();

}
