package skipiste.graph;

/**
 * Enum to represent the difficulty of a ski piste. The difficulties are in
 * order of easiest to hardert : GREEN, BLUE,RED,BLACK
 * 
 * @author s1011122
 * 
 */
public enum Difficulty {

	GREEN("Green"), BLUE("Blue"), RED("Red"), BLACK("Black");

	private String difficulty;

	private Difficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the difficulty
	 */
	public String getDifficulty() {
		return difficulty;
	}

}
