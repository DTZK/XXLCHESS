package XXLChess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends Piece{
    private boolean castle_left;
    private boolean castle_right;
    public King(int x, int y, char letter) {
        super(x, y, letter);
        castle_left = true;
        castle_right = true;
        //TODO Auto-generated constructor stub
    }

    @Override
    /**
     * set_value for King Piece
    */
    public void set_Value() {
        // TODO Auto-generated method stub
        value=99;
    }

    @Override
    /**
     * returns a value for King Piece
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
        if(clicked)
        {
            if (destination_x*48 == this.x&&destination_y*48 == this.y){
                clicked = false;
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
                    else if(alliesSpot(letter,  array[0], array[1])){
                        break;
                    }
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
                    else if(alliesSpot(letter,  array[0], array[1])){
                        break;
                    }
                }
            }
        }
        //check along the  x-axis left side
        if (castle_left){
            if (withinBounds(row-2, column)){
                int[] array = {row-2, column};
                if (first_move){
                    if( empty_space(array[0], array[1])){
                        
                        moves.add(array);
                    }
                }
            }
        }
        
        if(castle_right){
            if (withinBounds(row+2, column)){
                int[] array = {row+2, column};
                if (first_move){
                    if(empty_space(array[0], array[1])){
                        moves.add(array);
                    }
                }
            }
        }
        
        // for (int i = row -1;i > row-1;i--){
        //     if(withinBounds(i,column)){
        //         int[] array= {i,column};
        //         if (empty_space(array[0], array[1])){
        //             moves.add(array); 
        //         }
        //         else{
        //             //add the move when meeting its foe then stops
        //             if(!alliesSpot(letter, array[0], array[1])){
        //                 moves.add(array); 
        //                 break;
        //             }
        //             //stops when meeting ally
        //             break;
        //         }
        //     }
        // }

        //check along x-axis right side
        // for(int i= row+1; i<=row+1;i++){
        //     if(withinBounds(i,column)){
        //         int[] array= {i,column};
        //         if (empty_space(array[0], array[1])){
        //             moves.add(array); 
        //         }
        //         else{
        //             //add the move when meeting its foe then stops
        //             if(!alliesSpot(letter, array[0], array[1])){
        //                 moves.add(array); 
        //                 break;
        //             }
        //             //stops when meeting ally
        //             break;
        //         }
        //     }
        // }
        
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
                    else if(alliesSpot(letter,  array[0], array[1])){
                        break;
                    }
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
        
        // for(int[] array: moves){
        //     System.out.println("this is all its moves");
        //     System.out.println(Arrays.toString(array));
        // }
        this.moves=moves;
        return moves;       
    }
}
