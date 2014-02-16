package skipiste.graph;

/**
 * Describes the section of the piste that a node belongs to, either the Start, a mid point in the piste or the end of the piste.
 * @author s1011122
 *
 */
public enum Section {
	
	START("Start"),MID("Mid"),END("End");
	
	private String section;
	
	private Section(String section)
	{
		this.section = section;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

}
