package view;

import java.awt.BorderLayout;

import javax.swing.*;

import model.GameModel;
import controller.Main;

public class GameView extends JFrame implements ViewObserver {

	
	
	public GameView() {
		GameModel.getInstance().addView(this);
		
		//set up window
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(GameModel.getInstance().getGameTitle());
		
		//add gamePanel
		//this.getContentPane().add((GamePanel)Main.gameCanvas, BorderLayout.CENTER);
		
		StatusBar statusBar = new StatusBar();
		this.getContentPane().add(statusBar, BorderLayout.NORTH);
		
		TicTacToeGrid grid = new TicTacToeGrid();
		this.getContentPane().add(grid, BorderLayout.CENTER);
		
		Menu menu = new Menu();
		this.getContentPane().add(menu, BorderLayout.EAST);
		
		//set the window visable
		this.setVisible(true);
	}

	@Override
	public void update() {
		
		
	}
}
