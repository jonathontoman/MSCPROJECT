package skipiste.utils;

/**
 * Utility class to produce KML Strings
 * 
 * @author s1011122
 * 
 */
public class OutputKML {

	/**
	 * Return a String marked up with KML as a KML placemark
	 * @param d - the latitude of the placemak
	 * @param e - the longitude of the placemark
	 * @return
	 */
	public static String outputPlaceMark(double d, double e) {

		return "<Placemark><name>" + d + "," + e
				+ "</name><Point><coordinates>" + d + "," + e
				+ ",</coordinates></Point></Placemark>";
	}
}
