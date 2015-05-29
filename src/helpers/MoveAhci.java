package helpers;

import java.util.ArrayList;
import java.util.List;

public class MoveAhci {               
    
    public MoveAhci(){}
        
    public static boolean invalidMove(Point from, Point to){
        if(from.getX() == 1 && from .getY() == 1){
            return false;
        }
        if((from.getX() == to.getY()) || (from.getY() == to.getX())){
            return true;
        }
        
        return false;
    }        
    
}
