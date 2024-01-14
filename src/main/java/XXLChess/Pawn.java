package XXLChess;

import java.util.*;

public class Pawn extends Piece {
    private boolean attack_left;
    private boolean attack_right;
    public Pawn(int x, int y, char letter) {
        super(x, y, letter);
        attack_left = false;
        attack_right  = false;
    }
 
    public void set_Value() {
        value = 1.0;
    }

    public double get_Value() {
        return value;
    }
 
    public void tick(String player_colour){
        if (clicked){
            if (destination_x*48 == this.x&&destination_y*48 == this.y){
                clicked = false;
                first_move = false;
            }
            if (moves.size()==0){
                clicked = false;
            }

            int divided_current_x = current_x/48;
            int divided_current_y = current_y/48;
            
            float increment_y = Math.abs(destination_y - divided_current_y)*speed;
            if (isUser){
                if (attack_left&&enableATtack(divided_current_x-1, divided_current_y-1) ){
                    this.x -= increment_y;
                    if (destination_x*48 == this.x){
                        update_previous_coordinate_x(this.x);
                        attack_left = false;
                    }
    
                }
                if (attack_right&&enableATtack(divided_current_x+1, divided_current_y-1)){
                    this.x += increment_y;
                    if (destination_x*48 == this.x){
                        update_previous_coordinate_x(this.x);
                    }
                    attack_right = false;
                }
            }
            else{
                if (attack_left&&enableATtack(divided_current_x-1, divided_current_y+1) ){
                    this.x -= increment_y;
                    if (destination_x*48 == this.x){
                        update_previous_coordinate_x(this.x);
                        attack_left = false;
                    }
    
                }
                if (attack_right&&enableATtack(divided_current_x+1, divided_current_y +1)){
                    this.x += increment_y;
                    if (destination_x*48 == this.x){
                        update_previous_coordinate_x(this.x);
                    }
                    attack_right = false;
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

    public boolean enableATtack(int x, int y){
        for (int[] coord : moves){
            if(coord[0]== x && coord[1] ==y){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<int[]> possibleMoves(String player_colour) {
        attack_left = false;
        attack_right = false;
        int[] possible_y = {1};
        //check if it is its first move
        if (first_move){
            int[] new_y = {1,2};
            possible_y = new_y;
        }

        if (!(isUser)){
            int[] new_y = {-1};
            possible_y = new_y;
            if (first_move){
                int[] more_move = {-1,-2};
                possible_y = more_move;
            }
            attack_left = false;
            attack_right = false;
        }
        
        ArrayList<int[]> moves = new ArrayList<>();

        for (int i = 0; i < possible_y.length;i++){
            int new_x = x/48;
            int new_y = y/48 - possible_y[i];
            if (isUser){
                if( withinBounds((x/48)-1, (y/48)-1)){
                    if (!alliesSpot(letter,(x/48)-1, (y/48)-1)&& !empty_space((x/48)-1, (y/48)-1)){
                        System.out.println("can attack");
                        int [] attacking_pos = {(x/48)-1, (y/48)-1};
                        if(Math.abs(new_y-(y/48))==1){
                            System.out.println("attack added");
                            attack_left= true;
                            moves.add(attacking_pos);
                        }
                    }
                }
    
                if( withinBounds((x/48)+1, (y/48)-1)){
                    System.out.println();
                    if (!alliesSpot(letter, (x/48)+1, (y/48)-1)&&!empty_space((x/48)+1, (y/48)-1)){
                        System.out.println("ENemy deteced");
                        int [] attacking_pos = {(x/48)+1, (y/48)-1};
                        if(Math.abs(new_y-(y/48))==1){
                            attack_right = true;
                            moves.add(attacking_pos);
                        }
                    }
                }
            }
            else{
                if( withinBounds((x/48)-1, (y/48)+1)){
                    if (!alliesSpot(letter, (x/48)-1, (y/48)+1)&& !empty_space((x/48)-1, (y/48)+1)){
                        System.out.println("can attack");
                        int [] attacking_pos = {(x/48)-1, (y/48)+1};
                        if(Math.abs(new_y-(y/48))==1){
                            System.out.println("attack added");
                            attack_left= true;
                            moves.add(attacking_pos);
                        }
                    }
                }
    
                if( withinBounds((x/48)+1, (y/48)+1)){
                    if (!alliesSpot(letter, (x/48)+1, (y/48)+1)&&!empty_space((x/48)+1, (y/48)+1)){
                        int [] attacking_pos = {(x/48)+1, (y/48)+1};
                        if(Math.abs(new_y-(y/48))==1){
                            attack_right = true;
                            moves.add(attacking_pos);
                        }
                    }
                }
            }
            
 
            if (withinBounds(new_x, new_y)){
                int [] array = {new_x, new_y};
                if (empty_space(new_x, new_y)){
                    moves.add(array); 
                }
                
                else{
                    if (!alliesSpot(letter, new_x, new_y)){ 
                        break;
                    }
                }
            }
        }
        // for(int[] array: moves){
        //     System.out.println("this is all its moves");
        //     System.out.println(Arrays.toString(array));
        // }

        if (moves.size()!=0){
            hasLegalMoves = true;
        }
        else{
            hasLegalMoves = false;
        }

        this.moves = moves;
        return moves;
    }  
}