package model;

import java.util.List;

import helpers.*;

public class Minimax {
	/* Set this if you want to specified depth limit for search */
    private static int uptoDepth = -1;            
    private static boolean achi = false;
    
    private Board gameBoard;
    private GameState gameState;            
    
    public Minimax(Board board,GameState g){
        gameBoard = board;     
        gameState = g;
    }
    
    public int execute(int alpha, int beta, int depth, int turn){    
        Board b = new Board();                   
        
        if(beta<=alpha){ 
//            System.out.println("Pruning at depth = "+depth);
            if(turn == 1) return Integer.MAX_VALUE; 
            else return Integer.MIN_VALUE; 
        }
        
        if(depth == uptoDepth || gameBoard.isGameOver()) return gameBoard.evaluateBoard();
        
        List<Point> pointsAvailable;
        if(!achi){
            pointsAvailable = gameState.getAvailableStates();
        }else{
            pointsAvailable = gameState.getAvailableStatesAhci();
        }
                
        if(pointsAvailable.isEmpty()) return 0;                
        
        if(depth==0) gameState.getRootsChildrenScore().clear(); 
        
        int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;
        
        for(int i=0;i<pointsAvailable.size(); ++i){
            Point point = pointsAvailable.get(i);
            
            int currentScore = 0;
            
            //Ours
            if(turn == 1){
                gameBoard.placeAMove(point, 1); 
                currentScore = execute(alpha, beta, depth+1, 2); //recursive call all the way to the leaf
                maxValue = Math.max(maxValue, currentScore); 
                
                //Set alpha
                alpha = Math.max(currentScore, alpha);
                
                if(depth == 0)
                    gameState.getRootsChildrenScore().add(new PointsAndScores(currentScore, point));
            }
            //Opponents AI
            else if(turn == 2){
                gameBoard.placeAMove(point, 2);
                currentScore = execute(alpha, beta, depth+1, 1); //recursive call all the way to the leaf 
                minValue = Math.min(minValue, currentScore);
                
                //Set beta
                beta = Math.min(currentScore, beta);
            }
            //reset board            
            gameBoard.getBoard()[point.getX()][point.getY()] = 0; 
//            System.out.println("Reset game board in : " + point.getX() + "," + point.getY() + ", for player : " + turn);
            
            //If a pruning has been done, don't evaluate the rest of the sibling states
            if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break;
        }
        return turn == 1 ? maxValue : minValue;
    }        
    
    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < gameState.getRootsChildrenScore().size(); ++i) {
            if (MAX < gameState.getRootsChildrenScore().get(i).getScore()) {
                MAX = gameState.getRootsChildrenScore().get(i).getScore();
                best = i;
            }
        }
               
        return gameState.getRootsChildrenScore().get(best).getPoint();
    }
    
    public static void setAchi(){
        achi = true;
    }
 
}
