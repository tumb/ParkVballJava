package view.playoffssetup;

/**
 * Helper class to return the 3 times with one call
 * @author thom
 *
 */
public class DivisionCounts {
	private int blueCount ; 
	private int greenCount ;
	private int redCount ; 
	
	public DivisionCounts(int blueCount, int greenCount, int redCount) {
		this.blueCount = blueCount ; 
		this.greenCount = greenCount ; 
		this.redCount = redCount ; 
	}

	public int getBlueCount() {
		return blueCount;
	}

	public int getGreenCount() {
		return greenCount;
	}

	public int getRedCount() {
		return redCount;
	}
	
	
}
