package XXLChess;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(int x, int y, char letter) {
        super(x, y, letter);  
    }

    @Override
    /**
     * set_value for Knight Piece
    */
    public void set_Value() {
        // TODO Auto-generated method stub
        value =2.0;
    }

    @Override
    /**
     * returns a value for Knight Piece
    */
    public double get_Value() {
        // TODO Auto-generated method stub
        return value;
    }

    @Override
    /**
     * method for animation and make the piece move
    */
    public void tick(String player_colour) {
        // TODO Auto-generated method stub
        if(clicked)
        {
            if (destination_x*48 == this.x&&destination_y*48 == this.y){
                clicked = false;
            }
            if (moves.size()==0){
                clicked = false;
            }

            int divided_current_x = current_x/48;
            int divided_current_y = current_y/48;
            // calc difference of 
            float increment_x = Math.abs(destination_x - divided_current_x)*speed;
            float increment_y = Math.abs(destination_y - divided_current_y)*speed;

            if (current_x < destination_x*48){
                if (destination_x*48 > this.x){
                    
                    this.x+= increment_x;
                }

                if (destination_x*48 == this.x){
                    
                    update_previous_coordinate_x(this.x);
                }
            }
            if (current_x > destination_x*48){
                if (destination_x*48 < this.x){
                    
                    this.x-= increment_x;
                }
                if (destination_x*48 == this.x){
                    update_previous_coordinate_x(this.x);
                }
            }

            
            if (current_y < destination_y*48){
                
                if (destination_y*48 > this.y){
                    
                    this.y+=increment_y;
                }
                if (destination_y*48 == this.y){
                    update_previous_coordinate_y(this.y);
                }
            }
            else if (current_y > destination_y*48){
                
                if (destination_y*48 < this.y){
                    this.y-=increment_y;
                }
                if (destination_y*48 == this.y){
                    update_previous_coordinate_y(this.y);
                    
                }
            }
        }
    }

    @Override
    /**
     * list all possible moves
    */
    public List<int[]> possibleMoves(String player_colour) {
        ArrayList<int[]> moves = new ArrayList<>();
        
        int[] possible_x = { 2, 2, 1, 1, -1, -1, -2, -2 };
        int[] possible_y = { -1, 1, -2, 2, -2, 2, -1, 1 };

        //check if it is a player piece
        if (!(isUser)){
            int[] new_ys = { 1, -1, 2, -2, 2, -2, 1, -1 };
            possible_y = new_ys;
        }
    

        for (int i = 0; i<8;i++){
            int new_x = x/48 + possible_x[i];
            int new_y = y/48 - possible_y[i];

            //Check if units are on the board
            if (withinBounds(new_x, new_y)){
                int [] array = {new_x, new_y};
                if (empty_space(new_x, new_y)){
                    moves.add(array); 
                }
                else{
                    if (!alliesSpot(letter, new_x, new_y)){
                        moves.add(array); 
                    }
                }
            }
        }

        this.moves = moves;
        return moves;
    }
}
