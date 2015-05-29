package model;

import java.util.Random;

import controller.Main;

public class AI_Random implements AI{

	public AI_Random(){
		
	}
	@Override
	public int getMove(char[] gameGrid, int[] availableMoves, int playerNumber) {
		Random rand = new Random();
		return availableMoves[rand.nextInt(availableMoves.length)];
	}
}
