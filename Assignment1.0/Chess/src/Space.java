
public class Space {
	private int rank;
	private int file;
	
	/**
	 * @param r
	 * @param f
	 */
	public Space(int r, int f) {
		rank = r;
		file = f;
	}
	
	/**
	 * @return
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * @return
	 */
	public int getFile() {
		return file;
	}
	
	/**
	 * @param other
	 * @return
	 */
	public boolean equals(Space other) {
		return (rank == other.rank) && (file == other.file);
	}
}
