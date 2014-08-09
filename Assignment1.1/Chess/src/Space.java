
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
	public int rank() {
		return rank;
	}
	
	/**
	 * @return
	 */
	public int file() {
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
