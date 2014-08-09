package model;

/**
 * @author Will
 * 
 */
public class Space {
	private int rank;
	private int file;
	
	/**
	 * @param r the rank (x position) of a board space
	 * @param f the file (y position) of a board space
	 */
	public Space(int r, int f) {
		rank = r;
		file = f;
	}
	
	/**
	 * @return the rank (x position) of a given space
	 */
	public int rank() {
		return rank;
	}
	
	/**
	 * @return the file (y position) of a given space
	 */
	public int file() {
		return file;
	}
	
	/**
	 * @param other the other space (rank,file) to compare to
	 * @return true if one space the same (x,y) position of another
	 */
	public boolean equals(Space other) {
		return (rank == other.rank) && (file == other.file);
	}

}
