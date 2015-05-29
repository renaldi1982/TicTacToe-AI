package model;

import java.util.ArrayList;
import java.util.List;

import helpers.*;

public class GameState {
	private static List<Point> ourTokens = new ArrayList<>();    
    private static Board gameBoard;
    private boolean ahci = false;
        
    private List<Point> availablePoints = new ArrayList<>();
    private List<PointsAndScores> rootsChildrenScores = new ArrayList<>();
    
    public static GameState newInstance(Board b){
        GameState.gameBoard = b;
        
        return new GameState();
    } 
    
    public List<Point> getAvailableStates() {  
        int[][] board = gameBoard.getBoard();
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    availablePoints.add(new Point(i, j));                    
                }
            }
        }        
        
        return availablePoints;
    }            

    public List<Point> getAvailableStatesAhci(){        
        List<Point> nonAchi = getAvailableStates();
        List<Point> achi = new ArrayList<>();
        for(Point p: nonAchi){
            for(Point o: ourTokens){
                if(!MoveAhci.invalidMove(p, o)){
                    achi.add(p);
                }
            }
        }
        return achi;                
    }
    
    public boolean isAhci() {
        return ahci;
    }

    public void setAhci(boolean ahci) {
        this.ahci = ahci;
    }
    
    public static void addOurTokens(Point p){
        ourTokens.add(p);
    }
    
    public static void removeOurTokens(Point p){
        ourTokens.remove(p);
    }
    
    public List<PointsAndScores> getRootsChildrenScore(){
        return rootsChildrenScores;
    }

}
