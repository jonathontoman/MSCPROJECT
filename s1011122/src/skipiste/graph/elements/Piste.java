package skipiste.graph.elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a Ski Piste
 */
public class Piste {


	/**
	 * The difficulty of this piste
	 */
	private Difficulty difficulty;

	/**
	 * The Piste Name
	 */
	private String name;
	
	/**
	 * List of Nodes that make up this piste.
	 */
	private LinkedList<Node> nodes;

	/**
	 * No arg Constructor
	 */
	public Piste() {
		super();
		this.nodes = new LinkedList<Node>();
	}

	/**
	 * Constructor
	 * 
	 * @param difficulty
	 *            the difficulty of this piste
	 * @param name
	 *            the name of this piste
	 */
	public Piste(Difficulty difficulty, String name) {
		super();
		this.difficulty = difficulty;
		this.name = name;
		this.nodes = new LinkedList<Node>();
	}



	/**
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty
	 *            the difficulty to set
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nodes
	 */
	public LinkedList<Node> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes
	 *            the nodes to set
	 */
	public void setNodes(LinkedList<Node> nodes) {
		this.nodes = nodes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piste other = (Piste) obj;
		if (difficulty != other.difficulty)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Piste [difficulty=" + difficulty + ", name=" + name
				+ ", nodes=" + nodes + "]";
	}


}
