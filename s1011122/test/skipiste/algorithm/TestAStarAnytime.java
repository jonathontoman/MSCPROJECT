//package skipiste.algorithm;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import skipiste.graph.Graph;
//import skipiste.graph.GraphBuilder;
//import skipiste.graph.elements.Node;
//import skipiste.graph.elements.Piste;
//import skipiste.utils.OutputKML;
//
//public class TestAStarAnytime {
//
//	private GraphBuilder graphBuidler;
//	private Graph g;
//
//	@Before
//	public void setup() {
//	}
//
//	/**
//	 * This isnt really a proper test case, its a convinient way to kick of
//	 * building a graph whilst carrying out development.
//	 * 
//	 * @throws IOException
//	 */
//	@Test
//	public void testCase1() throws IOException {
//		graphBuidler = new GraphBuilder(this.getClass()
//				.getResource("PlanMontalbertPistesPlanDePisteNl.kml").getFile());
//		g = graphBuidler.getGraph();
//		AnytimeRepairingAStar algorithm = new AnytimeRepairingAStar(g);
//
//		// Get the start and end of each node
//		HashMap<Integer, Node> startOptions = new HashMap<Integer, Node>();
//		HashMap<Integer, Node> endOptions = new HashMap<Integer, Node>();
//
//
//		int i = 1;
//		for (Piste p : g.getPistes()) {
//			System.out.println("Start of " + p.getName() + ":" + i);
//			startOptions.put(i, p.getNodes().getFirst());
//			i++;
//		}
//		System.out.println("Enter source node");
//
//		// open up standard input read in start node choice
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String input = br.readLine();
//		Node source = startOptions.get(new Integer(input));
//		// read in end node choice
//
//		i = 1;
//		for (Piste p : g.getPistes()) {
//			System.out.println("End of " + p.getName() + ":" + i);
//			endOptions.put(i, p.getNodes().getLast());
//			i++;
//		}
//
//		System.out.println("Enter destination node");
//		input = br.readLine();
//		Integer desinationI = new Integer(input);
//
//		Node destination = endOptions.get(desinationI);
//		algorithm.execute(source,destination);
//		
//		while(destination.getPreviousNodeInPath() != null)
//		{
//			System.out.println(OutputKML.outputPlaceMark( destination.getPreviousNodeInPath().getLongitude(), destination.getPreviousNodeInPath().getLattitude()));
//			destination = destination.getPreviousNodeInPath();
//		}
//	}
//}
