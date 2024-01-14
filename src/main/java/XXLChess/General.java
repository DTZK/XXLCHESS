package XXLChess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class General extends Piece{

    public General(int x, int y, char letter) {
        super(x, y, letter);
    }

    @Override
    public void set_Value() {
        value = 5;
    }

    @Override
    public double get_Value() {
        return value;
    }

    @Override
    public void tick(String player_colour) {
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
    public List<int[]> possibleMoves(String player_colour) {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        // for(int i = 0;i<14;i++){
        //         System.out.println(tiles[i]);
        //     }
        int row = x/48;
        int column = y/48;
        //top left diagonal moves
        for (int i = row - 1, j = column - 1; i >= row-1 && j >= column-1; i--, j--){
            if(withinBounds(i, j)){
                int [] array = {i, j};
                if (empty_space(i, j)){
                    moves.add(array); 
                }
                else{
                    //add the move when meeting its foe then stops
                    if (!alliesSpot(letter, i, j)){
                        moves.add(array); 
                        break;
                    }
                    //stops when meeting ally
                    else if (alliesSpot(letter, i, j)){
                        break;
                    }
                }
            }
        }

        //bottom right diagonal moves
        for(int i = row-1, j =column+1; i>=row-1 && j<column+2;i--,j++){
            

            if(withinBounds(i, j)){
                int [] array = {i, j};
                if (empty_space(i, j)){
                    moves.add(array); 
                }
                else{
                    if (!alliesSpot(letter, i, j)){
                        moves.add(array);
                         
                    }
                    break;
                }
            }
        }

        //top right diagonal moves
        for(int i = row+1, j = column-1; i<row+2 && j>=column-1;i++,j--){

            if(withinBounds(i, j)){
                int [] array = {i, j};
                if (empty_space(i, j)){
                    moves.add(array); 
                }
                else{
                    if (!alliesSpot(letter, i, j)){
                        moves.add(array); 
                    }
                    break;
                }
            }
        }

        //bottom right diagonal moves
        for(int i = row+1, j=column+1; i<row+2 && j<column+2; i++,j++){
            if(withinBounds(i, j)){
                int [] array = {i, j};
                if (empty_space(i, j)){
                    moves.add(array); 
                }
                else{
                    if (!alliesSpot(letter, i, j)){
                        moves.add(array); 
                    }
                    break;
                }
            }
        }
 
        //check along y-axis left side
        for (int i= column-1; i>=column-1;i--){
            if(withinBounds(row, i)){
                int[] array = {row, i };
                if(empty_space(array[0], array[1])){
                    moves.add(array);
                }
                else{
                    //add the move when meeting its foe then stops
                    if(!alliesSpot(letter, array[0], array[1])){
                        moves.add(array); 
                        break;
                    }
                    //stops when meeting ally
                    break;
                }
            }
        }

        //check along y-axis right side
        for(int i = column+1;i<column+2;i++){
            if(withinBounds(row+1,i)){
                int[] array = {row,i};
                if(empty_space(array[0], array[1])){
                    moves.add(array);
                }
                else{
                    //add the move when meeting its foe then stops
                    if(!alliesSpot(letter, array[0], array[1])){
                        moves.add(array); 
                        break;
                    }
                    //stops when meeting ally
                    break;
                }
            }
        }

        for (int i = row -1;i>=row-1;i--){
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
        for(int i= row+1; i<row+2;i++){
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
        for (int i= column-1; i>=column-1;i--){
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
        for(int i = column+1;i<column+2;i++){
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
        // System.out.println("General moves "+ moves.size());

        this.moves = moves;
        return moves;     
    }
    
    
    
}
