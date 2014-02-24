package skipiste.algorithm;

import org.junit.Before;
import org.junit.Test;

import skipiste.graph.Graph;
import skipiste.importer.kml.KMLToGraph;

public class TestAStar 
{
	/**
	 * The location of our test data. The file name here is treated as XML ,
	 * this is just convenience for the eclipse editor and editing XML.
	 */
	private static final String KML = "PlagneMontalbertPisteRuns.xml";

	private KMLToGraph graphBuidler;
	private Graph g;

	@Before
	public void setup() {
		graphBuidler = new KMLToGraph();
	}

	/**
	 * This isnt really a proper test case, its a convinient way to kick of
	 * building a graph whilst carrying out development.
	 */
	@Test
	public void testCase1() {
		g = graphBuidler.importGraph(graphBuidler.getClass().getResource(KML).getFile());
		AStar algorithm  = new AStar(g);
		String start = "Les Adrets0";
		String end = "Pravendue136";
		algorithm.execute(start, end);;
		algorithm.printShortestPath();
	}
}
