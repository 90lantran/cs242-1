import java.awt.Color;

import javax.swing.JFrame;


public class Chess {
	public static final String [] initList = {"Rook","Knight","Bishop","Queen",
											  "King","Bishop","Knight","Rook"};
	
	
	public void initialize(Board board) {
		for (int i = 0; i < 8; i++) {
			board.setPiece("Pawn",i,1,Color.BLACK);
			board.setPiece("Pawn",i,6,Color.WHITE);
			board.setPiece(initList[i],i,0,Color.BLACK);
			board.setPiece(initList[i],i,7,Color.WHITE);
		}
	}
	
	public static void display() {
		Chess chess = new Chess();
		Board board;
		board = new Board();
		chess.initialize(board);
		
		JFrame frame = new JFrame("War Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(board);
		frame.setSize(592,638);
		//f.setJMenuBar(getMenu());
		//f.addMouseListener(getMouse());
		frame.setVisible(true);
	}
	public static void main(String[] args) {
       display();
    }
}
