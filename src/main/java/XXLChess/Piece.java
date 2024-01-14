package XXLChess;

import processing.core.PImage;
import processing.core.PApplet;
import java.util.*;

public abstract class Piece {
    protected PImage sprite;

    protected boolean isUser;
    protected boolean castling;
    protected int current_x;
    protected int current_y;

    protected int prev_x;
    protected int prev_y;

    protected int x;
    protected int y;

    protected boolean chosen;
    protected float speed =  App.movement_speed;
    protected boolean clicked;
    protected boolean hasLegalMoves;
    protected boolean taken;
    protected boolean is_check;

    protected char letter;

    protected boolean is_left_rook;
    protected boolean is_right_rook;

    protected double value;

    protected boolean first_move;

    protected int destination_x;

    protected int destination_y;

    protected List<int[]> moves;
    protected boolean turns_queen;
    protected char[][] tiles = new char [14][14]; 

    /**
     * sets sprite
    */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    public Piece(int x, int y, char letter){
        this.x = x;
        this.chosen = false;
        this.taken = false;
        this.y= y;
        this.clicked = false;
        this.letter=letter;
        this.first_move = true;
        this.moves = new ArrayList<int[]>();
        this.current_x = x;
        this.current_y= y;
        this.hasLegalMoves = false;
    }

    /**
     * sets a boolean if it is in check
    */
    public void set_check(boolean x){
        is_check = x;
    }
    /**
     * returns a boolean if a piece is in check
     * true if in check
    */
    public boolean get_check(){
        return is_check;
    }
    /**
     * inserts letter onto tiles
    */
    public void insert_tiles(char letter, int x, int y){
        tiles[y][x] = letter;
    }

    /**
     * sets a boolean for piece to 
    */
    public void isUser(boolean x){
        isUser = x;
    }

    /**
     * returns a boolean if it is a user piece
     * true if is user piece
    */
    public boolean get_isUser(){
        return isUser;
    }
    /**
     * returns a boolean if it is a left_rook 
     * true if left
    */
    public boolean is_rook_leftside(){
        return is_left_rook;
    }
     /**
     * returns a boolean if it is a right_rook 
     * true if right
    */
    public boolean is_rook_rightside(){
        return is_right_rook;
    }
     /**
     * sets tiles into tiles
    */
    public void put_tiles(char[][]tiles){
        this.tiles = tiles;
    }
     /**
     * returns boolean if selected tile is empty  or occupied by friend or foe
     * false if empty and foe and true if ally
    */
    public boolean alliesSpot(char letter, int x, int y){
        char spot = tiles[y][x];
        
        if (Character.isUpperCase(spot)== Character.isUpperCase(letter)){
            return true;
        }
        else{
            return false;
        } 
    }
    
    /**
     * returns boolean if selected tile is empty 
     * true if empty
    */
    public boolean empty_space( int x, int y){
        if (tiles[y][x]==' '){
            return true;
        }
        return false;
    }

    // public void check_board(){
    //     for (int i = 0; i < tiles.length;i++){
    //         System.out.println(tiles[i]);
    //     }
    // }
    /**
     * returns boolean if a piece has legal moves
    */
    public boolean check_LegalMoves(){
        return hasLegalMoves;
    }

    /**
     * sets current x coordinates to current_x
    */
    public void update_previous_coordinate_x(int x){
        current_x = x;
    }

    /**
     * sets current y coordinates to current_y
    */
    public void update_previous_coordinate_y(int y){
        current_y = y;
    }
    /**
     * set a boolean if a piece has been taken
    */
    public void been_taken(boolean x){
        taken = x;
    }
    /**
     * returns a boolean if a piece is taken
    */
    public boolean get_taken(){
        return taken;
    }
    /**
     * set a boolean for the first_move of a piece
    */
    public void set_first_move(boolean check){
        first_move = check;
    }

    /**
     * returns where the piece is at its first move
    */
    public boolean get_first_move(){
        return first_move;
    }
    /**
     * set a boolean to chosen for piece
    */
    public void set_chosen(boolean x){
        chosen = x;
    }

    /**
     * returns if a piece is chosen
    */
    public boolean get_chosen(){
        return chosen;
    }

    /**
     * set the coordinates where the piece is heading to
    */
    public void set_destination(int x, int y){
        destination_x = x;
        destination_y = y;
        prev_x = this.x;
        prev_y = this.y;
    }

    /**
     * returns where the piece is going to reach on the y axis
    */
    public int get_destination_y(){
        return destination_y;
    }

    /**
     * returns where the piece is going to reach on the x axis
    */
    public int get_destination_x(){
        return destination_x;
    }

    /**
     * returns letter of the piece
    */
    public char get_letter(){
        return letter;
    }
    /**
     * returns x coordinates scaled by 48
    */
    public int getX() {
        return x;
    }
    /**
     * returns y coordinates scaled by 48
    */
    public int getY() {
        return y;
    }
    /**
     * assigns a boolean to clicked
    */
    public void set_clicked(boolean x){
        clicked = x;
    }

    /**
     * returns a boolean if piece is clicked
    */
    public boolean get_clicked(){
        return clicked;
    }
    

    //check if clicked square is within bounds
    /**
     * check if clicked square is within bounds
    */
    public boolean withinBounds(int x, int y){
        if (x >= 0 && y >= 0 && x < 14 && y < 14) {
            return true;
          }
          return false;
      
    }

    /**
     * method to return moves of a piece
    */
    public List<int[]> get_moves(){
        return moves;
    }

    /**
     * method to set a boolean to castling
    */
    public void set_castling(boolean x){
        castling = x;
    }

    /**
     * method to return if king can castle or not
    */
    public boolean isCastling(){
        return castling;
    }

    // public abstract ArrayList<int[]> highlightMoves();

    /**
     * abstract method to set value to piece
    */
    public abstract void set_Value();

    /**
     * abstract method to return value 
    */
    public abstract double get_Value();

    /**
     * abstract method list all movement
    */
    public abstract void tick(String player_colour);

    /**
     * method list all possible moves 
    */
    public abstract List<int[]> possibleMoves(String player_colour);

    /**
     * draws the sprite
    */
    public void draw(PApplet app) {
        // The image() method is used to draw PImages onto the screen.
        // The first argument is the image, the second and third arguments are coordinates
        app.image(this.sprite, this.x, this.y, App.CELLSIZE, App.CELLSIZE);
    }
}