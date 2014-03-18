package skipiste.algorithm;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Describable;

import skipiste.graph.Graph;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.importer.kml.KMLToGraph;
import skipiste.importer.kml.SkiMapHandler;

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
		graphBuidler = new KMLToGraph(new SkiMapHandler());
	}

	/**
	 * This isnt really a proper test case, its a convinient way to kick of
	 * building a graph whilst carrying out development.
	 * @throws IOException 
	 */
	@Test
	public void testCase1() throws IOException {
		g = graphBuidler.importGraph(graphBuidler.getClass().getResource(KML).getFile());
		AStar algorithm  = new AStar(g);
		
		HashMap<Integer, Node> searchOptions = new HashMap<Integer,Node>();
		
		int i = 0;
		for (Piste p : g.getPistes())
		{
			for (Node n : p.getNodes())
			{
				searchOptions.put(new Integer(i), n);
				i++;
			}
		}
		
		System.out.println(searchOptions);
		System.out.println("Enter source node");
		Console c = System.console();
		
		
		   //  open up standard input
	      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		
		Integer sourceI = new Integer(input);
		System.out.println("Enter destination node");
		input = br.readLine();
		Integer desinationI = new Integer(input);
		Node destination = searchOptions.get(desinationI);

		
		algorithm.execute(g,searchOptions.get(sourceI),searchOptions.get(desinationI));
		
		StringBuilder sb = new StringBuilder();
		while (destination.getPreviousNodeInPath() != null)
		{
			sb.append(destination.getPreviousNodeInPath().toString());
			destination = destination.getPreviousNodeInPath();
		}
		System.out.println(sb.toString());
	}
}
