package skipiste.importer.kml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import skipiste.graph.Node;

/**
 * Test Class for the KMLHandler class. This class uses a known KML file which
 * describes part of a ski area to test the KMLHandler. This class exercises a
 * very limited number of test cases it is just to give added confidence that
 * the KML Handler is performing as we expected.
 * 
 * @author s1011122
 */
public class TestKMLHandler {

	/**
	 * The location of our test data. The file name here is treated as XML ,
	 * this is just convenience for the eclipse editor and editing XML.
	 */
	private static final String KML = "PlagneMontalbertPisteRuns.xml";
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

	private SAXParserFactory spf;
	private SAXParser saxParser;
	private XMLReader xmlReader;

	@Before
	public void setUp() {
		// Configure the SAX parsing.
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		try {
			saxParser = spf.newSAXParser();
			classUnderTest = new KMLHandler();
			xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(classUnderTest);

		} catch (ParserConfigurationException | SAXException e) {
			// Do Nothing other than print stack trace
			e.printStackTrace();
		}

		file = this.getClass().getResource(KML).getFile();
		try {
			xmlReader.parse(file);
		} catch (IOException | SAXException e) {
			fail();
		}

		data = classUnderTest.getGraphData();

	}

	@Test
	/**
	 * Tests that we extract the correct number of pistes from the kml file.
	 */
	public void testCorrectNumberOfPiste() {

		// we expect 19 distinct pistes
		int expected = 19;
		int actual = data.size();
		assertEquals(expected, actual);
	}

	@Test
	/**
	 * Tests that we extract the correct number of pistes from the kml file.
	 */
	public void testDescriptionCorrect() {

		LinkedList<Node> piste = data.get(0);
		// expected description of first piste
		String expected = "Les Adrets";
		String actual = piste.getFirst().getName();
		assertEquals(expected, actual);
	}

	@Test
	/**
	 * Tests that we extract the correct number of pistes from the kml file.
	 */
	public void testNodeCountCorrect() {

		LinkedList<Node> piste = data.get(0);
		// expected number of nodes in first piste
		int expected = 26 ;
		int actual = piste.size();
		assertEquals(expected, actual);
	}

}
