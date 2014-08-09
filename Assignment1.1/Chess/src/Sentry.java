import java.util.Vector;


public class Sentry extends Piece {

	@Override
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		moves = new Vector<Space>();
		
		Piece queen = new Queen();
		queen.setColor(color);
		queen.updateMoves(spaces,rank,file);
		
		int rankdiff, filediff;
		Space curSpace;
		
		for (int it = 0; it < queen.moves.size(); it++) {
			curSpace = queen.moves.elementAt(it);
			filediff = Math.abs(curSpace.file() - file);
			rankdiff = Math.abs(curSpace.rank() - rank);
			
			if (filediff <= 2 || rankdiff <= 2)
				moves.add(curSpace);
		}
		
	}

}
