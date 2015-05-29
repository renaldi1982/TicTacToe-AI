package model;

import java.util.Random;
import helpers.*;


public class AI_Minimax implements AI {

	private Board b;
	private GameState s;
	private Minimax m;
	private char[] prevGrid;
	int prevEmptyPosition;
	
	public AI_Minimax() {
		b = new Board();
		s = GameState.newInstance(b);
        m = new Minimax(b, s);
        prevGrid = GameModel.getInstance().getGameGrid().clone();
	}

	

	@Override
	public int getMove(char[] gameGrid, int[] availableMoves, int playerNumber) {
		
		int pos = -1;
		try{					
		
			//move for other player
			char lastval = 'X';
			if(playerNumber == 2){
				lastval = 'O';
			}
			int lastMove = findLastMove(gameGrid, lastval);
			Point lastMovePoint = positionToPoint(lastMove);
			int lastPlayer = 1;
			if(playerNumber == 1){
				lastPlayer = 2;
			}
			if(lastMove != -1){
				if(GameModel.getInstance().isAchi()){
					b.placeAchiMove(lastMovePoint, lastPlayer);
				}
				else{
					b.placeAMove(lastMovePoint, lastPlayer);
				}
			}
		
		
		
			System.out.println("Player moved to :"+lastMove);
			b.displayBoard();
			
			if(GameModel.getInstance().isAchi()){
				Minimax.setAchi();
			}
			
		
			//find our best move
			m.execute(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, playerNumber);
		
			Point move = m.returnBestMove();
			int position = pointToPosition(move);
			
			if(GameModel.getInstance().isAchi()){
				b.placeAchiMove(move, playerNumber);
			}
			else{
				b.placeAMove(move, playerNumber);
			}
			
			char val = 'X';
			if(playerNumber == 2){
				val = 'O';
			}
			prevGrid[position] = val;
			
			
			System.out.println("Ai moved to :"+position);
			b.displayBoard();
			
			pos = position;
		}
		catch(Exception e){
			
		}
		
		
		
		boolean found = false;
		for(int i=0; i<availableMoves.length; i++){
			if(availableMoves[i] == pos){
				found = true;
			}
		}
		if(pos == -1 || found == false){
			Random rand = new Random();
			pos = availableMoves[rand.nextInt(availableMoves.length)];
		}
		
		return pos;
		
		
	}
	private int findEmptyPosition(char[] grid) {
		for(int i=0; i<grid.length; i++){
			if(grid[i] == 0){
				return i;
			}
			System.out.println(grid[i]);
		}
		return -1;
	}


	private int findLastMove(char[] gameGrid, char val) {
		int lastMove = -1;
		if(GameModel.getInstance().isAchi()){
			lastMove = findEmptyPosition(gameGrid);
			System.out.println("Last move achi: "+lastMove);
		}
		else{
			for(int i=0; i<gameGrid.length; i++){
				if(gameGrid[i] != prevGrid[i]){
					if(gameGrid[i] == val){
						lastMove = i;
						System.out.println("Last move: "+i);
					}
				}
			}
		}
		prevGrid = gameGrid.clone();
		return lastMove;
	}
	private Point positionToPoint(int position){
		int x =0;
		int y = 0;
		switch (position) {
		case 0:
    		x=0;
    		y=0;
    		break;
		case 1:
    		x=0;
    		y=1;
    		break;
		case 2:
    		x=0;
    		y=2;
    		break;
		case 3:
    		x=1;
    		y=0;
    		break;
		case 4:
    		x=1;
    		y=1;
    		break;
		case 5:
    		x=1;
    		y=2;
    		break;
		case 6:
    		x=2;
    		y=0;
    		break;
		case 7:
    		x=2;
    		y=1;
    		break;
		case 8:
    		x=2;
    		y=2;
    		break;
		}
		return new Point(x, y); 
		
	}
	private int pointToPosition(Point point){
		
		if(point.getX() == 0 && point.getY() == 0){
			return 0;
		}
		else if(point.getX() == 0 && point.getY() == 1){
			return 1;
		}
		else if(point.getX() == 0 && point.getY() == 2){
			return 2;
		}
		else if(point.getX() == 1 && point.getY() == 0){
			return 3;
		}
		else if(point.getX() == 1 && point.getY() == 1){
			return 4;
		}
		else if(point.getX() == 1 && point.getY() == 2){
			return 5;
		}
		else if(point.getX() == 2 && point.getY() == 0){
			return 6;
		}
		else if(point.getX() == 2 && point.getY() == 1){
			return 7;
		}
		else {
			return 8;
		}
	}
	
	//for the ai to maintain the state of the game this function should be called for each move
	public void placeMove(int position){
		int player = GameModel.getInstance().getPlayerNumber();
		Point move = positionToPoint(position);
		b.placeAMove(move, player);
	}
}
