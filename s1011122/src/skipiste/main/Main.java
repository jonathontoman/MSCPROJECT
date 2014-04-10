package skipiste.main;

import java.io.IOException;



/**
 * Main class for running the route planning.
 * @author s1011122
 *
 */
public class Main 
{
	
	/**
	 * Main method
	 * @param args
	 * @throws IOException 
	 */
	public static void main (String args[]) throws IOException
	{
		GraphRunner gr = new GraphRunner();
		gr.run();
	}
}
