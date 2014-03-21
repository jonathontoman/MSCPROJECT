package skipiste.importer.kml;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import skipiste.graph.Graph;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.utils.OutputKML;

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

//	/**
//	 * The location of our test data. The file name here is treated as XML ,
//	 * this is just convenience for the eclipse editor and editing XML.
//	 */
//	private static final String KML1 = "PlagneMontalbertPisteRuns.xml";
//	private static final String KML2 = "PlanMontalbertPistesPlanDePisteNl.kml";
//
//	private KMLToGraph classUnderTest;
//	private Graph g;
//
//
//
//	/**
//	 * This isnt really a proper test case, its a convinient way to kick of
//	 * building a graph whilst carrying out development.
//	 */
//	@Test
//	public void testCase1() {
//		
//		// Test using skimap handler which we assume works! We could use
//		// something like easymock to mock out the handler class and build our
//		// own return values so we do not rely on the other classes for this.
//		classUnderTest = new KMLToGraph(new SkiMapHandler());
//		g = classUnderTest.importGraph(this.getClass().getResource(KML1)
//				.getFile());
//
//		for (Piste p : g.getPistes())
//		{
//			System.out.println(p.toString());
//		}
//		
//		for (Node n : g.getNodes())
//		{
//			if (n.isPredicted())
//			{
//				System.out.println(OutputKML.outputPlaceMark(n.getLongitude(),
//						n.getLattitude()));
//			}
//		}
//		
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testCase2() {
//		
//		// Test using plandepiste handler which we assume works! We could use
//		// something like easymock to mock out the handler class and build our
//		// own return values so we do not rely on the other classes for this.
//		classUnderTest = new KMLToGraph(new PlanDePisteHandler());
//		g = classUnderTest.importGraph(this.getClass().getResource(KML2)
//				.getFile());
//
//		for (Piste p : g.getPistes())
//		{
//			System.out.println(p.toString());
//		}
//		
//		for (Node n : g.getNodes())
//		{
//			if (n.isPredicted())
//			{
//				System.out.println(OutputKML.outputPlaceMark(n.getLongitude(),
//						n.getLattitude()));
//			}
//		}
//		
//		assertTrue(true);
//	}
}
