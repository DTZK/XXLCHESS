package XXLChess;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Rook extends Piece{
    
    public Rook(int x, int y, char letter) {
        super(x, y, letter);
        setup_rook();
        
    }

    @Override
    public void set_Value() {
        // TODO Auto-generated method stub
        value = 5.25;
    }

    @Override
    public double get_Value() {
        // TODO Auto-generated method stub
        return value;
    }

    @Override
    public void tick(String player_colour) {
        if(isCastling()){
            this.x = destination_x;
            update_previous_coordinate_x(this.x);
        }
        if(clicked)
        {   
            if (destination_x*48 == this.x&&destination_y*48 == this.y){
                clicked = false;
                update_previous_coordinate_x(this.x);
                update_previous_coordinate_y(this.y);
                first_move = false;
            }
            if (moves.size()==0){
                clicked = false;
            }

            int divided_current_x = current_x/48;
            int divided_current_y = current_y/48;
            // calc difference of 
            float increment_x = Math.abs(destination_x - divided_current_x)*speed;
            float increment_y = Math.abs(destination_y - divided_current_y)*speed;
            if(isCastling()){
                this.x = destination_x*48;
                update_previous_coordinate_x(this.x);
            }
            
            if (current_x < destination_x*48){
                if (destination_x*48 > this.x){
                    
                    this.x+= increment_x;
                }
            }
            if (current_x > destination_x*48){
                if (destination_x*48 < this.x){
                    
                    this.x-= increment_x;
                }
            }
            
            if (current_y < destination_y*48){
                
                if (destination_y*48 > this.y){
                    
                    this.y+=increment_y;
                }
  
            }
            else if (current_y > destination_y*48){
                
                if (destination_y*48 < this.y){
                    this.y-=increment_y;
                }

            }
        }
    }
    public void setup_rook(){
        int row = (x/48) ;
        if (row-1<7){
            is_left_rook = true;
            is_right_rook = false;

        }
        else{
            is_right_rook = true;
            is_left_rook = false;
        }
    }

    @Override
    public List<int[]> possibleMoves(String player_colour) {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        // System.out.println("this is rook");
        int row = (x/48) ;
        int column = (y/48) ;
        
        //check along the  x-axis left side
        for (int i = row -1;i>=0;i--){
            if(withinBounds(i,column)){
                int[] array= {i,column};
                if (empty_space(array[0], array[1])){
                    moves.add(array); 
                }
                else{
                    //add the move when meeting its foe then stops
                    if(!alliesSpot(letter, array[0], array[1])){
                        moves.add(array); 

                    }
                    //stops when meeting ally
                    break;
                }
            }
        }

        //check along x-axis right side
        for(int i= row+1; i<14;i++){
            if(withinBounds(i,column)){
                int[] array= {i,column};
                if (empty_space(array[0], array[1])){
                    moves.add(array); 
                }
                else{
                    //add the move when meeting its foe then stops
                    if(!alliesSpot(letter, array[0], array[1])){
                        moves.add(array); 

                    }
                    //stops when meeting ally
                    break;
                }
            }
        }
        
        //check along y-axis left side
        for (int i= column-1; i>=0;i--){
            if(withinBounds(row, i)){
                int[] array = {row, i };
                if(empty_space(array[0], array[1])){
                    moves.add(array);
                }
                else{
                    //add the move when meeting its foe then stops
                    if(!alliesSpot(letter, array[0], array[1])){
                        moves.add(array); 
                        
                    }
                    //stops when meeting ally
                    break;
                }
            }
        }

        //check along y-axis right side
        for(int i = column+1;i<14;i++){
            if(withinBounds(row+1,i)){
                int[] array = {row,i};
                if(empty_space(array[0], array[1])){
                    moves.add(array);
                }
                else{
                    //add the move when meeting its foe then stops
                    if(!alliesSpot(letter, array[0], array[1])){
                        moves.add(array); 
                        
                    }
                    break;
                }
            }
        }
        // System.out.println("rook moves length"+ moves.size());
        // for(int[] array: moves){
        //     System.out.println("this is all its moves");
        //     System.out.println(Arrays.toString(array));
        // }
        this.moves = moves;
        
        return moves;
    }
    
}
