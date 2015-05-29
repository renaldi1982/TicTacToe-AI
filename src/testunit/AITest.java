package testunit;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.AI_Minimax;
import model.GameModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.GameView;
import controller.GameController;

public class AITest {
	
	private GameView view;
	private GameController controller;
	private AI_Minimax ai;
	private static List<Integer> moves;
	static{
		moves = new ArrayList<>();
		for(int i = 0; i < 9; i++){
			moves.add(i);
		}
	}
	
	@Before
	public void setupTest(){
		 view = new GameView();
		 controller.newHumanVsAI();
	}
	
	@Test
	public void test() {
		assertTrue(testAIinstanceCreated());
		int move = testAIReturnValidMoves();
		assertTrue((move > -1 && move < 9));		
	}

	private boolean testAIinstanceCreated(){
		ai = new AI_Minimax();
		return ai == null;
	}
	
	private int testAIReturnValidMoves(){
		int move = ai.getMove(GameModel.getInstance().getGameGrid(), GameModel.getInstance().getAvailableMoves(), 1);
		moves.remove(move);
		return move;
	}
	
	@After
	public void afterTest(){
		GameController.getInstance().endGame();
	}
	
}
