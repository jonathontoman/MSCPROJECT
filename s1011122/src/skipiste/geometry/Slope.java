package skipiste.geometry;

/**
 * The slope of a given line segment.
 * @author s101122
 *
 */
public class Slope 
{
	private final double  rise;
	private final double travel;
	
	public Slope (double rise, double travel)
	{
		this.rise = rise;
		this.travel = travel;
	}
	
	/**
	 * @return the rise
	 */
	public double getRise() {
		return rise;
	}
	/**
	 * @return the travel
	 */
	public double getTravel() {
		return travel;
	}
	
	public boolean isVertical()
	{
		return travel == 0;
	}
	
	public double asDouble()
	{
		
			if (isVertical()) 
			{
				throw new IllegalStateException();
			}
			return rise / travel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(rise);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(travel);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
			}
			if (object == null || object.getClass() != getClass()) {
			return false;
			}
			Slope other = (Slope) object;
			if (isVertical() && other.isVertical()) {
			return true;
			}
			if (isVertical() || other.isVertical()) {
				return false;
			}
			return (asDouble()) == (other.asDouble());
			}
	}
