package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import helpers.*;

public class Board {
	List<Point> ourTokens;
    List<Point> availablePoints;
    Scanner scan = new Scanner(System.in);
    int[][] board = new int[3][3];     
        
    public Board(){}            
    
    public Scanner getScan(){
        return scan;
    }
    
    public int[][] getBoard(){
        return board;
    }
    
    public int evaluateBoard() {
        int score = 0;

        //Check all rows
        for (int i = 0; i < 3; ++i) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    blank++;
                } else if (board[i][j] == 1) {
                    X++;
                } else {
                    O++;
                }

            } 
            score+=changeInScore(X, O); 
        }

        //Check all columns
        for (int j = 0; j < 3; ++j) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int i = 0; i < 3; ++i) {
                if (board[i][j] == 0) {
                    blank++;
                } else if (board[i][j] == 1) {
                    X++;
                } else {
                    O++;
                } 
            }
            score+=changeInScore(X, O);
        }

        int blank = 0;
        int X = 0;
        int O = 0;

        //Check diagonal (first)
        for (int i = 0, j = 0; i < 3; ++i, ++j) {
            if (board[i][j] == 1) {
                X++;
            } else if (board[i][j] == 2) {
                O++;
            } else {
                blank++;
            }
        }

        score+=changeInScore(X, O);

        blank = 0;
        X = 0;
        O = 0;

        //Check Diagonal (Second)
        for (int i = 2, j = 0; i > -1; --i, ++j) {
            if (board[i][j] == 1) {
                X++;
            } else if (board[i][j] == 2) {
                O++;
            } else {
                blank++;
            }
        }

        score+=changeInScore(X, O);

        return score;
    }
    
    private int changeInScore(int X, int O){
        int change;
        if (X == 3) {
            change = 100;
        } else if (X == 2 && O == 0) {
            change = 10;
        } else if (X == 1 && O == 0) {
            change = 1;
        } else if (O == 3) {
            change = -100;
        } else if (O == 2 && X == 0) {
            change = -10;
        } else if (O == 1 && X == 0) {
            change = -1;
        } else {
            change = 0;
        } 
        return change;
    }
    
    public boolean isGameOver() {
        //Game is over is someone has won, or board is full (draw)        
        return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
    }    
    
    public boolean hasXWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) 
                || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            //System.out.println("X Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                // System.out.println("X Row or Column win");
                return true;
            }
        }
        return false;
    }

    public boolean hasOWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) 
                || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
            // System.out.println("O Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                //  System.out.println("O Row or Column win");
                return true;
            }
        }

        return false;
    }

    public List<Point> getAvailableStates() {
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
    
    public boolean placeAMove(Point point, int player) {        
        if(board[point.getX()][point.getY()] != 0){            
            return false;
        }
        //player = 1 for X, 2 for O
        board[point.getX()][point.getY()] = player;   
       
        return true;
    }

    public boolean placeAchiMove(Point from, Point to, int player){
        
        //player = 1 for X, 2 for O
        board[to.getX()][to.getY()] = player;   
        board[from.getX()][from.getY()] = 0;
        
        return true;
    }        
 public boolean placeAchiMove(Point from, int player){
        return placeAchiMove(from, findEmptySquare(), player);
 }
 
 public Point findEmptySquare(){
	 for(int x=0; x<3; x++){
		 for(int y=0; y<3; y++){
			 if(board[x][y] == 0){
				 return new Point(x, y);
			 }
		 } 
	 }
	 return null;
 }
 
    void takeHumanInput() {
        System.out.println("Your move: ");
        
        int x = scan.nextInt();
        int y = scan.nextInt();
        Point point = new Point(x, y);
        
        if(!getAvailableStates().contains(point)){
            placeAMove(point, 2);
        }else{
            System.out.println("You can't move there!!");            
        }                            
    }

    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();

        }
    } 
    
    public void resetBoard() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                board[i][j] = 0;
            }
        }
    } 

}
