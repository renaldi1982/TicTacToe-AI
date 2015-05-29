package model;

//playerNumber 1=X 2=O
public interface AI {
	
	public int getMove(char[] gameGrid, int[] availableMoves, int playerNumber);

}
