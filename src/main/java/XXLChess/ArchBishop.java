package XXLChess;

import java.util.*;

public class ArchBishop extends Piece{

    public ArchBishop(int x, int y, char letter) {
        super(x, y, letter);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void set_Value() {
        // TODO Auto-generated method stub
        value = 7.5;
    }

    @Override
    public double get_Value() {
        // TODO Auto-generated method stub
        return value;
    }

    @Override
    public void tick(String player_colour) {
        if(clicked)
        {
            if (destination_x*48 == this.x&&destination_y*48 == this.y){
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
        int[] possible_x = { 2, 2, 1, 1, -1, -1, -2, -2 };
        int[] possible_y = { -1, 1, -2, 2, -2, 2, -1, 1 };

        //check if it is a player piece
        if (!(isUser)){
            int[] new_ys = { 1, -1, 2, -2, 2, -2, 1, -1 };
            possible_y = new_ys;
        }

        for(int i = 0;i<14;i++){
            System.out.println(tiles[i]);
        }

        ArrayList<int[]> moves = new ArrayList<int[]>();
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

        for(int i = 0;i<14;i++){
                System.out.println(tiles[i]);
        }
        int row = x/48;
        int column = y/48;
        
        //top left diagonal moves
        for (int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--){

            if(withinBounds(i, j)){
                int [] array = {i, j};
                if (empty_space(i, j)){
                    moves.add(array); 
                }
                else{
                    //add the move when meeting its foe then stops
                    if (!alliesSpot(letter, i, j)){
                        moves.add(array); 
                    }
                    //stops when meeting ally
                    break;
                }
            }
        }

        //bottom right diagonal moves
        for(int i = row-1, j =column+1; i>=0 && j<14;i--,j++){
            // System.out.println("top right moves");
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
        for(int i = row+1, j = column-1; i<14 && j>=0;i++,j--){
            // int[] coord = {i,j};
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
        for(int i = row+1, j=column+1; i<14 && j<14; i++,j++){
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
        this.moves= moves;
        // for(int[] array: moves){
        //     System.out.println("this is all its moves");
        //     System.out.println(Arrays.toString(array));
        // }

        return moves;
        
    }
    
}
