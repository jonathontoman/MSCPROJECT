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
		if (args.length != 1 )
		{
			System.out.println("Exactly one argument must be passed to the main method, the file input kml");
		}
		
		GraphRunner gr = new GraphRunner(args[0]);
		gr.run();
	}
}
