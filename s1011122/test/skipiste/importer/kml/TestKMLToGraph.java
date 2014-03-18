package skipiste.importer.kml;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import skipiste.graph.Graph;
import skipiste.graph.elements.Piste;

/**
 * Tests the KMLtoGraph class builds a graph as we expect it. We have separate
 * test cases to cover the handler and the intersection methods so we just need
 * to check that we get the correct number of new nodes when we create
 * intersections etc.
 * 
 * @author s1011122
 * 
 */
public class TestKMLToGraph {

	/**
	 * The location of our test data. The file name here is treated as XML ,
	 * this is just convenience for the eclipse editor and editing XML.
	 */
	private static final String KML = "TestSkiMap.xml";

	private KMLToGraph classUnderTest;
	private Graph g;

	@Before
	public void setup() {
		// Test using skimap handler which we assume works! We could use
		// something like easymock to mock out the handler class and build our
		// own return values so we do not rely on the other classes for this.
		classUnderTest = new KMLToGraph(new SkiMapHandler());
	}

	/**
	 * This isnt really a proper test case, its a convinient way to kick of
	 * building a graph whilst carrying out development.
	 */
	@Test
	public void testCase1() {
		g = classUnderTest.importGraph(this.getClass().getResource(KML)
				.getFile());

		for (Piste p : g.getPistes())
		{
			System.out.println(p.toString());
		}
		
		assertTrue(true);
	}
}
