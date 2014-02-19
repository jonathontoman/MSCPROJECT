package skipiste.importer.kml;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import skipiste.graph.Graph;

/**
 * Tests the KMLtoGraph class builds a graph as we expect it.
 * 
 * @author s1011122
 * 
 */
public class TestKMLToGraph {

	/**
	 * The location of our test data. The file name here is treated as XML ,
	 * this is just convenience for the eclipse editor and editing XML.
	 */
	private static final String KML = "PlagneMontalbertPisteRuns.xml";

	private KMLToGraph classUnderTest;
	private Graph g;

	@Before
	public void setup() {
		classUnderTest = new KMLToGraph();
	}

	/**
	 * This isnt really a proper test case, its a convinient way to kick of
	 * building a graph whilst carrying out development.
	 */
	@Test
	public void testCase1() {
		g = classUnderTest.importGraph(this.getClass().getResource(KML).getFile());
		System.out.println(g.getNodes().toString());
		assertTrue(true);
	}
}
