package skipiste.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import skipiste.algorithm.AStar;
import skipiste.algorithm.Dijkstra;
import skipiste.graph.Graph;
import skipiste.graph.GraphBuilder;
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

	public void run() throws IOException {

		// Get the file we are going to process as a graph
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		System.out
				.println("Enter the file path of the kml file we are going to process");
		String kmlFile = reader.readLine();

		System.out.println("PROCESSING GRAPH ....");
		GraphBuilder builder = new GraphBuilder(kmlFile);

		Graph g = builder.getGraph();
		
		// Get the start and end of each node
		HashMap<Integer, Node> startOptions = new HashMap<Integer, Node>();
		HashMap<Integer, Node> endOptions = new HashMap<Integer, Node>();
		
		int i = 1;
		for (Piste p : g.getPistes()) {	
			System.out.println("Start of " + p.getName() + ":" + i);
			startOptions.put(i, p.getNodes().getFirst());
			i++;
		}
		
		// open up standard input read in start node choice
		String input = reader.readLine();
		Node source = startOptions.get(new Integer(input));
		// read in end node choice

		i = 1;
		for (Piste p : g.getPistes()) {
			System.out.println("End of " + p.getName() + ":" + i);
			endOptions.put(i, p.getNodes().getLast());
			i++;
		}

		System.out.println("Enter destination node");
		input = reader.readLine();
		Integer desinationI = new Integer(input);		

		Node destination = endOptions.get(desinationI);
		System.out.println("Enter source node");
		String selection;
		System.out.println("Select Algorithm to use:");
		System.out.println("1= Dijkstra, 2=AStar");
		
		selection = reader.readLine();
		switch(selection)
		{
		case "1" : Dijkstra d = new Dijkstra(); d.execute(source, destination);break;
		case "2" : AStar a = new AStar(g); a.execute(source, destination);break;
		}
		
		// output route
		while(destination.getPreviousNodeInPath() != null)
		{
			System.out.println(OutputKML.outputPlaceMark( destination.getPreviousNodeInPath().getLongitude(), destination.getPreviousNodeInPath().getLattitude()));
			destination = destination.getPreviousNodeInPath();
		}
		
	}
}
