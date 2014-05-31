package skipiste.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
 * Runs the route planning application against a graph
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
	
	public GraphRunner(String kml)
	{
		this.pisteKML = kml;
	}

	/**
	 * Run the application.
	 * 
	 * @throws IOException
	 */
	public void run() {
									
		
		startOptions = new HashMap<Integer, Node>();
		endOptions = new HashMap<Integer, Node>();

		// Step 1 build the graph.

		System.out.println("Building Graph from file : " + pisteKML);

		GraphBuilder builder = new GraphBuilder();
				
		
		long startTime = System.currentTimeMillis();
		g = builder.buildGraph(pisteKML);
		
		System.out.println("Outputting all Start End and Intersection Nodes:");
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (Node n : g.getNodes())
		{
			if (n.isStart()| n.isEnd() | n.isIntersection())
			{
				nodes.add(n);
			}
		}
		
		System.out.println(OutputKML.outputPlaceMarks(nodes));
		
		System.out.println("Outputing all pistes");
		
		ArrayList<LinkedList<Node>> pistes = new ArrayList<LinkedList<Node>>();
		for (Piste p : g.getPistes())
		{
			
			pistes.add(p.getNodes());
			
		}
		System.out.println(OutputKML.outputGroupOfRoutes(pistes));
		
		
		
		long endTime = System.currentTimeMillis();
		System.out.println("Graph Build Time : " + (endTime - startTime) + " milliseconds.");
		System.out.println("Nodes built: " + g.getNodes().size());
		System.out.println("Edges built: " + g.getEdges().size());
		System.out.println("Pistes built: " + g.getPistes().size());
		// validate the graph
		GraphValidator.validateGraph(g);
		
		System.out.println("Graph Built");
		
		do
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
			endOptions.put(i, p.getNodes().getLast());
			i++;
		}
		System.out.println("Enter end point");
		end = endOptions.get(new Integer(readInFromConsole()));

		runAlgorithms();
		
		System.out.println("Press any key to continue");
		}
		while(readInFromConsole() != null);

	}
	
	/**
	 * Run the route searching algorithms
	 */
	private void runAlgorithms() {
		
		Path dPath = null;
		Path aPath = null;
		Path idaPath = null;
		Path ara1Path = null;
		
		try {
			dijkstra = new Dijkstra();
			System.out.println("Dijkstra Results: ");
			dPath = dijkstra.findPath(start,end);
			System.out.println("Total Time taken = " + dijkstra.getDuration()  + " milliseconds");
			System.out.println("Nodes expanded  =" + dijkstra.getNodeCount());
			System.out.println("Path Length (meters) = " + dPath.getDistance() );

			OutputKML.outputRoute(dijkstra.findPath(start, end)
					.getNodesInPath());

		} catch (Exception e) {
			System.out.println("Dijkstra Algorithm Failed");
			e.printStackTrace();
		}

		try {
			aStar = new AStar();
			System.out.println("A* Results: ");
			aPath = aStar.findPath(start,end);
			System.out.println("Total Time taken = " + aStar.getDuration() + " milliseconds");
			System.out.println("Nodes expanded  =" + aStar.getNodeCount() );
			System.out.println("Path Length (meters) = " + aPath.getDistance() );


		} catch (Exception e) {
			System.out.println("A* Algorithm Failed");
			e.printStackTrace();
		}

		try {
			
			idaStar = new IterativeDeepeningAStar();
			System.out.println("IDA* Results: ");
			idaPath = idaStar.findPath(start,end);
			System.out.println("Total Time taken = " + idaStar.getDuration() + " milliseconds");
			System.out.println("Nodes expanded  =" + idaStar.getNodeCount());
			System.out.println("Path Length (meters) = " + idaPath.getDistance() );	
		} catch (Exception e) {
			System.out.println("IDA* Algorithm Failed");
			e.printStackTrace();
		}

		try {
			
			System.out.println("Printing the results of each ARA* iteration.");
			araStar = new AnytimeRepairingAStar();
			System.out.println("Final ARA* Results: ");
			ara1Path = araStar.findPath(start,end);			
			System.out.println("Total Time taken = " + araStar.getDuration()+ " milliseconds");
			System.out.println("Nodes expanded  =" + araStar.getNodeCount() );
			System.out.println("Path Length (meters) = " + ara1Path.getDistance() );
	

			OutputKML.outputRoute(dijkstra.findPath(start, end)
					.getNodesInPath());
		} catch (Exception e) {
			System.out.println("ARA* Algorithm Failed");
			e.printStackTrace();
		}

		ArrayList<Path> paths = new ArrayList<Path>();
		paths.add(dPath);
		paths.add(aPath);
		paths.add(idaPath);
		paths.add(ara1Path);
		
		System.out.println("Printing shortest Paths found by algorithms.");
		System.out.println(OutputKML.outputGroupOfRoutes(paths));
		
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
