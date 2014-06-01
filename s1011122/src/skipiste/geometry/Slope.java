package skipiste.geometry;

/**
 * The slope of a given line segment.
 * 
 * @author s101122
 * 
 */
public class Slope {
	/**
	 * Difference on x axis
	 */
	private final double xDifference;
	/**
	 * Difference on y axis
	 */
	private final double yDifference;

	public Slope(double yDiff, double xDiff) {
		this.xDifference = yDiff;
		this.yDifference = xDiff;
	}

	/**
	 * @return the rise
	 */
	public double getRise() {
		return xDifference;
	}

	/**
	 * @return the travel
	 */
	public double getTravel() {
		return yDifference;
	}

	public boolean isVertical() {
		return yDifference == 0;
	}

	public double asDouble() {

		if (isVertical()) {
			throw new IllegalStateException();
		}
		return xDifference / yDifference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(xDifference);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yDifference);
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
		// if one is parallel and the other isnt they are not parallel
		if ((isVertical() && !other.isVertical())
				|| (other.isVertical() && !isVertical())) {
			return false;
		}
		// if both are vertical then they are parallel
		if (isVertical() && other.isVertical()) {
			return true;
		}
		// otherwise compare the values of the slopes
		return (asDouble()) == (other.asDouble());
	}
}
