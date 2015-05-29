package model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import controller.Main;
import view.ViewObserver;


public class GameModel {
	
	private static final GameModel instance = new GameModel();
	public static GameModel getInstance() {
		return instance;
	}
	
	
	public static final int GRIDSIZE = 9;
	public static final int[][] VALIDAUCHIMOVES = new int[][] {{1,3,4},{0,2,4},{1,5,4},{0,4,6},{0,1,2,3,4,5,6,7,8},{2,4,8},{3,7,4},{4,6,8},{5,7,4}};
	private boolean gameInProgress; //is a game active or not
	private int gameType; //human vs human, human vs ai, etc.
	private int status; //0=waiting for other player, 1=p1 turn, 2=p2 turn, 3= p1 winner, 4= p2 winner
	private boolean achi; //game mode
	private char[] gameGrid; //9 element array X, O, or Null
	private ArrayList<ViewObserver> views; //views to update when state changes
	private String gameTitle; //for the window title bar
	private String gameId;
	private int playerNumber;
	
	private GameModel() {
		playerNumber = 1;
		gameId = "";
		gameGrid = new char[GRIDSIZE];
		views = new ArrayList<ViewObserver>();
		gameTitle = "TermProject";
		gameInProgress = false;
		status=0;
		achi = false;
	}
	public void addView(ViewObserver view) {
		this.views.add(view);
	}
	public String getGameTitle() {
		return gameTitle;
	}
	public char[] getGrid() {
		return gameGrid;
	}
	public void placeMove(int position) {
		if(achi){
			gameGrid[findEmptyPosition()] = gameGrid[position];
			gameGrid[position] = 0;
		}
		else{
			if(status == 1){
				gameGrid[position] = 'X';
			}
			else if(status == 2){
				gameGrid[position] = 'O';
			}
		}
		
		toggleTurn();
		checkWinner();
		updateMode();
		updateViews();
	}
	private void toggleTurn(){
		if(status == 1){
			status = 2;
		}
		else{
			status = 1;
		}
	}
	private void updateMode(){
		int emptySpaces = 0;
		for(int i=0; i<GRIDSIZE; i++){
			if(gameGrid[i] == 0){
				emptySpaces++;
			}
		}
		if(emptySpaces == 1){
			achi = true;
		}
		else {
			achi = false;
		}
	}
	private void checkWinner(){
		char winner = 0;
		//012
		if(gameGrid[0] != 0 && gameGrid[0] == gameGrid[1] && gameGrid[0] == gameGrid[2]){
			winner = gameGrid[0];
		}
		//345
		if(gameGrid[3] != 0 && gameGrid[3] == gameGrid[4] && gameGrid[3] == gameGrid[5]){
			winner = gameGrid[3];
		}
		//678
		if(gameGrid[6] != 0 && gameGrid[6] == gameGrid[7] && gameGrid[6] == gameGrid[8]){
			winner = gameGrid[6];
		}
		//036
		if(gameGrid[0] != 0 && gameGrid[0] == gameGrid[3] && gameGrid[0] == gameGrid[6]){
			winner = gameGrid[0];
		}
		//147
		if(gameGrid[1] != 0 && gameGrid[1] == gameGrid[4] && gameGrid[1] == gameGrid[7]){
			winner = gameGrid[1];
		}
		//258
		if(gameGrid[2] != 0 && gameGrid[2] == gameGrid[5] && gameGrid[2] == gameGrid[8]){
			winner = gameGrid[2];
		}
		//048
		if(gameGrid[0] != 0 && gameGrid[0] == gameGrid[4] && gameGrid[0] == gameGrid[8]){
			winner = gameGrid[0];
		}
		//246
		if(gameGrid[2] != 0 && gameGrid[2] == gameGrid[4] && gameGrid[2] == gameGrid[6]){
			winner = gameGrid[2];
		}
		
		
		if(winner == 'X'){
			status = 3;
		}
		else if(winner == 'O'){
			status = 4;
		}
	}
	private void updateViews(){
		for(int i=0; i<views.size(); i++){
			views.get(i).update();
		}
	}
	public boolean isMoveValid(int position, int player) {
		if(!isGameInProgress() || status == 3 || status == 4){
			return false;
		}
		else if(gameType != 0 && status != player){
			//Playing against an ai or server player and its the other persons turn
			return false;
		}
		else if(achi){
			//all spaces adjacent to the player whos turn it is are valid
			if(status == 1){
				for(int i=0; i<VALIDAUCHIMOVES[findEmptyPosition()].length; i++){
					if(VALIDAUCHIMOVES[findEmptyPosition()][i] == position
						&& gameGrid[position] == 'X'){
						return true;
					}
				}
			}
			else if(status == 2){
				for(int i=0; i<VALIDAUCHIMOVES[findEmptyPosition()].length; i++){
					if(VALIDAUCHIMOVES[findEmptyPosition()][i] == position
						&& gameGrid[position] == 'O'){
						return true;
					}
				}	
			}
		}
		else{
			//all empty spaces are valid
			if(gameGrid[position] == 0){
				return true;
			}
		}
		return false;
	}
	public String getGameTypeDescription() {
		if(!isGameInProgress()){
			return "There is no active game.";
		}
		else if(gameType == 0){
			return "Human -vs- Human"; 
		}
		else if(gameType == 1){
			return "Human -vs- AI"; 
		}
		else if(gameType == 2){
			return "Hosting Human -vs- server Opponent"; 
		}
		else if(gameType == 3){
			return "Joined Human -vs- server Opponent"; 
		}
		else if(gameType == 4){
			return "Hosting AI -vs- server Opponent"; 
		}
		else if(gameType == 5){
			return "Joined AI -vs- server Opponent"; 
		}
		else{
			return gameType+""; //this case should not be possible
		}
	}
	public String getStatusDescription() {
		
		String player = "X";
		if(playerNumber == 2){
			player = "O";
		}
		
		if(!isGameInProgress()){
			return "";
		}
		else if(gameType == 0){
			if(status == 0){
				return "The game hasn't started yet";
			}
			else if(status == 1){
				return "It is X's turn";
			}
			else if(status == 2){
				return "It is O's turn";
			}
			else if(status == 3){
				return "X is the winner";
			}
			else if(status == 4){
				return "O is the winner";
			}
		}
		else {//if(gameType == 0){
			if(status == 0){
				return "The game hasn't started yet";
			}
			else if(status == 1){
				if(playerNumber == 1){
					return "It is your turn";
				}
				else{
					return "It is your opponent's turn"; //Opponent
				}
			}
			else if(status == 2){
				if(playerNumber == 2){
					return "It is your turn";
				}
				else{
					return "It is your opponent's turn"; //Opponent
				}
			}
			else if(status == 3){
				if(playerNumber == 1){
					return "You win";
				}
				else{
					return "Your opponent has won"; //Opponent
				}
			}
			else if(status == 4){
				if(playerNumber == 2){
					return "You win";
				}
				else{
					return "Your opponent has won"; //Opponent
				}
			}
		}
		
		
		return "";
	}
	private int findEmptyPosition(){
		for(int i=0; i<GRIDSIZE; i++){
			if(gameGrid[i] == 0){
				return i;
			}
		}
		return -1;
	}



	public int getGameType() {
		return gameType;
	}



	public void setGameType(int gameType) {
		this.gameType = gameType;
		updateViews();
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
		updateViews();
	}



	public boolean isAchi() {
		return achi;
	}



	public void setAchi(boolean achi) {
		this.achi = achi;
		updateViews();
	}


	public boolean isGameInProgress() {
		return gameInProgress;
	}

	public void setGameInProgress(boolean gameInProgress) {
		this.gameInProgress = gameInProgress;
		updateViews();
	}



	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}



	public void clearGrid() {
		for(int i=0; i<GRIDSIZE; i++){
			gameGrid[i] = 0;
		}
	}
	public int[] getAvailableMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for(int i=0; i<GRIDSIZE; i++){
			if(isMoveValid(i, status)){
				moves.add(i);
			}
		}
		
		int[] ret = new int[moves.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = moves.get(i).intValue();
	    }
	    return ret;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public char[] getGameGrid() {
		return gameGrid;
	}
	public void setGameGrid(char[] gameGrid) {
		this.gameGrid = gameGrid;
	}
	public int getPlayerNumber() {
		return playerNumber;
	}
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}





}
