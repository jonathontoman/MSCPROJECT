package skipiste.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import skipiste.algorithm.Path;
import skipiste.algorithm.arastar.AnytimeRepairingAStar;
import skipiste.algorithm.astar.AStar;
import skipiste.algorithm.dijkstra.Dijkstra;
import skipiste.algorithm.idastar.IterativeDeepeningAStar;
import skipiste.graph.Graph;
import skipiste.graph.GraphBuilder;
import skipiste.graph.GraphValidator;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.utils.OutputKML;

/**
 * Runs the route planning applicaiton against a graph
 * 
 * @author s1011122
 * 
 */
public class GraphRunner {

	/**
	 * The URI string of the piste file.
	 */
	private String pisteKML;
	/**
	 * The graph built by the application.
	 */
	private Graph g;
	/**
	 * The start options
	 */
	private HashMap<Integer, Node> startOptions;
	/**
	 * The end options
	 */
	private HashMap<Integer, Node> endOptions;

	private Dijkstra dijkstra;
	private AStar aStar;
	private AnytimeRepairingAStar araStar;
	private IterativeDeepeningAStar idaStar;

	/**
	 * Start node of the search
	 */
	Node start;
	/**
	 * End node of the search
	 */
	Node end;

	/**
	 * Run the application.
	 * 
	 * @throws IOException
	 */
	public void run() {
		pisteKML = this.getClass().getResource("WhistlerBlackcomb.kml").getFile();
		startOptions = new HashMap<Integer, Node>();
		endOptions = new HashMap<Integer, Node>();

		// Step 1 build the graph.

		System.out.println("Building Graph from file : " + pisteKML);

		GraphBuilder builder = new GraphBuilder();
		
		long startTime = System.currentTimeMillis();
		g = builder.buildGraph(pisteKML);
		long endTime = System.currentTimeMillis();
		System.out.println("Graph Build Time : " + (endTime - startTime) + " milliseconds.");
		System.out.println("Nodes built: " + g.getNodes().size());
		System.out.println("Edges built: " + g.getEdges().size());
		System.out.println("Pistes built: " + g.getPistes().size());
		// validate the graph
		GraphValidator.validateGraph(g);
		
		System.out.println("Graph Built");
		
		while(true)
		{
			
		
		
		System.out
				.println("Select a Start Location (enter number of piste in console):");
		int i = 1;
		for (Piste p : g.getPistes()) {
			System.out.println(p.getName() + ":" + i);
			startOptions.put(i, p.getNodes().getFirst());
			i++;
		}
		System.out.println("Enter start point");
		start = startOptions.get(new Integer(readInFromConsole()));

		System.out
		.println("Select a End Location (enter number of piste in console):");
		i = 1;
		for (Piste p : g.getPistes()) {
			System.out.println(p.getName() + ":" + i);
			endOptions.put(i, p.getNodes().getLast());
			i++;
		}
		System.out.println("Enter end point");
		end = endOptions.get(new Integer(readInFromConsole()));

		runAlgorithms();
		}

	}

	/**
	 * Run the route searching algorithms
	 */
	private void runAlgorithms() {
		try {
			dijkstra = new Dijkstra();
			System.out.println("Dijkstra Results: ");
			Path p = dijkstra.findPath(start,end);
			System.out.println("Total Time taken = " + dijkstra.getDuration()  + " milliseconds");
			System.out.println("Nodes expanded  =" + dijkstra.getNodeCount());
			System.out.println("Path Length (meters) = " + p.getDistance() );
			System.out.println("Path Route KML: ");
			System.out.println(p.printPath());		

			OutputKML.outputRoute(dijkstra.findPath(start, end)
					.getNodesInPath());

		} catch (Exception e) {
			System.out.println("Dijkstra Algorithm Failed");
			e.printStackTrace();
		}

		try {
			aStar = new AStar();
			System.out.println("A* Results: ");
			Path p = aStar.findPath(start,end);
			System.out.println("Total Time taken = " + aStar.getDuration() + " milliseconds");
			System.out.println("Nodes expanded  =" + aStar.getNodeCount() );
			System.out.println("Path Length (meters) = " + p.getDistance() );
			System.out.println("Path Route KML: ");
			System.out.println(p.printPath());		


		} catch (Exception e) {
			System.out.println("A* Algorithm Failed");
			e.printStackTrace();
		}

		try {
			
			idaStar = new IterativeDeepeningAStar();
			System.out.println("IDA* Results: ");
			Path p = idaStar.findPath(start,end);
			System.out.println("Total Time taken = " + idaStar.getDuration() + " milliseconds");
			System.out.println("Nodes expanded  =" + idaStar.getNodeCount());
			System.out.println("Path Length (meters) = " + p.getDistance() );
			System.out.println("Path Route KML: ");
			System.out.println(p.printPath());	
		} catch (Exception e) {
			System.out.println("IDA* Algorithm Failed");
			e.printStackTrace();
		}

		try {
			araStar = new AnytimeRepairingAStar();
			System.out.println("ARA* Results: ");
			Path p = araStar.findPath(start,end);			
			System.out.println("Total Time taken = " + araStar.getDuration()+ " milliseconds");
			System.out.println("Nodes expanded  =" + araStar.getNodeCount() );
			System.out.println("Path Length (meters) = " + p.getDistance() );
			System.out.println("Path Route KML: ");
			System.out.println(p.printPath());	

			OutputKML.outputRoute(dijkstra.findPath(start, end)
					.getNodesInPath());
		} catch (Exception e) {
			System.out.println("ARA* Algorithm Failed");
			e.printStackTrace();
		}

	}

	/**
	 * Read in user input from the console.
	 * 
	 * @return
	 */
	private String readInFromConsole() {
		String input = null;
		// open up standard input read in start node choice
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			input = br.readLine();

		} catch (IOException e) {
			System.out
					.println("Failed to read in input from console. Application exiting.");
			e.printStackTrace();
			System.exit(0);
		}
		return input;
	}		
}
