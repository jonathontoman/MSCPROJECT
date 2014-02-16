package skipiste.importer.kml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class KMLParser {
	SAXParserFactory spf;
	SAXParser saxParser;
	XMLReader xmlReader;

	/**
	 * The Handler used by this parsing class.
	 */
	private KMLHandler handler;

	public KMLParser() throws ParserConfigurationException, SAXException,
			IOException {
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		saxParser = spf.newSAXParser();
		handler = new KMLHandler();

	}

	public void parseKML(String fileUrl) throws SAXException, IOException {
		xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(handler);
		xmlReader.parse(convertToFileURL(fileUrl));

	}

	private String convertToFileURL(String filename) {
		String path = new File(filename).getAbsolutePath();
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar, '/');
		}

		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return "file:" + path;
	}

	/**
	 * Returns the ski piste object built by the PisteMapHandler
	 * 
	 * @return
	 */
	public ArrayList<LinkedList<Node>> getPistes() {

		return handler.getGraphData();

	}
}
