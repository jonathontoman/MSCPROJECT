package skipiste.graph;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author s1011122
 *
 */
public class Graph {
	public Graph() {
		nodes = new HashMap<String, Node>();
	}

	private Map<String, Node> nodes;
	public Map<String, Node> getNodes() {
		return nodes;
	}

	public void setNodes(Map<String, Node> nodes) {
		this.nodes = nodes;
	}
}
