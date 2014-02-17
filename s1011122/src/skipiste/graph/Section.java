package skipiste.graph;

/**
 * Describes the section of the piste that a node belongs to. There are 4 types
 * of Section: START - this represents the start or origin of a piste, MID -
 * this represents a point on the piste between the START and the END of a
 * piste, JUNCTION - this represents a node with multiple inbound and /or
 * outbound egdes, i.e it is a JUNCTION of two or more pistes on a piste map,
 * END the end or terminus of a piste.
 * 
 * @author s1011122
 * 
 */
public enum Section {

	START("Start"), MID("Mid"), JUNCTION("Junction"), END("End");

	private String section;

	private Section(String section) {
		this.section = section;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

}
