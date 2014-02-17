package skipiste.importer.kml;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import skipiste.graph.Node;


/**
 * Test Class for the KMLHandler class. This class uses a known kml file which describes part of a ski area to test the KMLHandler.
 * @author s1011122
 */
public class TestKMLHandler {

	
	/**
	 * The location of our test data.
	 */
	private static final String KML = "PlagneMontalbertPisteRuns.kml";
	/**
	 * The class under test
	 */
	private KMLHandler classUnderTest;
	/**
	 * The data the handler should return.
	 */
	private ArrayList<LinkedList<Node>> data;
	/**
	 * The url of the file on the classpath, use to pass to the handler.
	 */
	private String file;
	
	SAXParserFactory spf;
	SAXParser saxParser;
	XMLReader xmlReader;
	
	@Before
	public void setUp()
	{
		// Configure the SAX parsing.
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		try {
			saxParser = spf.newSAXParser();
			classUnderTest  = new KMLHandler();
			xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(classUnderTest);

		} catch (ParserConfigurationException | SAXException e) {
			// Do Nothing other than print stack trace
			e.printStackTrace();
		}
				
		 file = this.getClass().getResource(KML).getFile();
		
	}
	
	
	
	@Test
	/**
	 * Tests that we extract the correct number of pistes from the kml file.
	 */
	public void testCorrectNumberOfPiste()
	{

		try {
			xmlReader.parse(file);
		} catch (IOException | SAXException e) {
			fail();
		}
		
		data = classUnderTest.getGraphData();

	}
	
}
