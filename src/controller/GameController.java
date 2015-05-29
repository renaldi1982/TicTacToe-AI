package controller;

import java.util.Timer;
import java.util.TimerTask;

import model.AI;
import model.AI_Minimax;
import model.AI_Random;
import model.GameModel;
import model.ServerModel;


public class GameController{
	private static final GameController instance = new GameController();
	public static GameController getInstance() {
		return instance;
	}

	private AI ai;
	private TimerTask updateTask;
	private Timer updater;
	
	private GameController(){
		//ai = new AI_Random();
		ai = new AI_Minimax();
	}

	private void startUpdater(){
		
		updater = new Timer("updater");
		updateTask = new TimerTask() {
	        @Override
	        public void run() {
	            if(GameModel.getInstance().getGameType()==1){
	            	if(GameModel.getInstance().getStatus() == 2){
	            		int position = ai.getMove(GameModel.getInstance().getGrid(), GameModel.getInstance().getAvailableMoves(), GameModel.getInstance().getPlayerNumber() );
	            		System.out.println("The AI moved to :"+position);
	            		move(position);
	            	}
	            }
	            else if(GameModel.getInstance().getGameType()>=2 ){
	            	
	            	//update status
	            	int status = ServerModel.getInstance().getGameStatus();
	            	GameModel.getInstance().setStatus(status);
	            	
	            	System.out.println(status);
	            	
	            	//update grid
	            	char[] grid = ServerModel.getInstance().getGameGrid();
	            	GameModel.getInstance().setGameGrid(grid);
	            	
	            	//check mode
	            	boolean achi = ServerModel.getInstance().getAchi();
	            	GameModel.getInstance().setAchi(achi);
	            	
	            	//if(GameModel.getInstance().getStatus() == 2){
	            	//	placeMove(ai.getMove(GameModel.getInstance().getGrid(), GameModel.getInstance().getAvailableMoves()));
	            	//}
	            	
	            	if(GameModel.getInstance().getGameType() >= 4){
		            	if(GameModel.getInstance().getStatus() == GameModel.getInstance().getPlayerNumber()){
		            		move(ai.getMove(GameModel.getInstance().getGrid(), GameModel.getInstance().getAvailableMoves(), GameModel.getInstance().getPlayerNumber()));
		            	}
		            }
	            	
	            }
	        }
	    };
		updater.scheduleAtFixedRate(updateTask, 0, 500);
	}
	public void move(int position) {
		System.out.println("test");
		if(GameModel.getInstance().getGameType() >= 2){
			ServerModel.getInstance().placeMove(position);
		}
		else{
			GameModel.getInstance().placeMove(position);
		}
	}
	
	
	private void stopUpdater(){
		updater.cancel();
	}
	
	public void newHumanVsHuman(){
		GameModel.getInstance().setGameId("local");
		GameModel.getInstance().setGameInProgress(true);
		GameModel.getInstance().setStatus(1);
		GameModel.getInstance().setGameType(0);
		GameModel.getInstance().setPlayerNumber(1);
		startUpdater();
	}
	public void newHumanVsAI(){
		GameModel.getInstance().setGameId("local");
		GameModel.getInstance().setGameInProgress(true);
		GameModel.getInstance().setStatus(1);
		GameModel.getInstance().setGameType(1);
		GameModel.getInstance().setPlayerNumber(1);
		startUpdater();
	}
	public void hostHumanVsServer(){
		
		//create new game with the server
		String gameId = ServerModel.getInstance().host();
		GameModel.getInstance().setGameId(gameId);
		
		//join myself to the new game
		//ServerModel.getInstance().setGameId(gameId);

		GameModel.getInstance().setPlayerNumber(1);
		GameModel.getInstance().setGameType(2);
		GameModel.getInstance().setGameInProgress(true);
		startUpdater();
	}
	public void joinHumanVsServer(String gameId){
		//join myself to the new game
		String playerId = ServerModel.getInstance().join(gameId);
		GameModel.getInstance().setPlayerNumber(2);
		
		GameModel.getInstance().setGameType(3);
		GameModel.getInstance().setGameInProgress(true);
		startUpdater();
	}
	public void hostAiVsServer(){
		//create new game with the server
		String gameId = ServerModel.getInstance().host();
		GameModel.getInstance().setGameId(gameId);
		
		//join myself to the new game
		//ServerModel.getInstance().setGameId(gameId);

		GameModel.getInstance().setPlayerNumber(1);
		GameModel.getInstance().setGameType(4);
		GameModel.getInstance().setGameInProgress(true);
		startUpdater();
	}
	public void joinAiVsServer(String gameId){
		//join myself to the new game
		String playerId = ServerModel.getInstance().join(gameId);
		GameModel.getInstance().setPlayerNumber(2);
		
		GameModel.getInstance().setGameType(5);
		GameModel.getInstance().setGameInProgress(true);
		startUpdater();
	}
	
	

	public void endGame() {
		stopUpdater();
		GameModel.getInstance().setGameInProgress(false);
		GameModel.getInstance().setStatus(0);
		GameModel.getInstance().setAchi(false);
		GameModel.getInstance().clearGrid();
	}


}
