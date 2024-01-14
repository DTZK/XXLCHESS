package XXLChess;

//import org.reflections.Reflections;
//import org.reflections.scanners.Scanners;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PFont;
import processing.event.MouseEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.awt.Font;
import java.io.*;
import java.util.*;


public class App extends PApplet {

    public static final int SPRITESIZE = 480;
    public static final int CELLSIZE = 48;
    public static final int SIDEBAR = 120;
    public static final int BOARD_WIDTH = 14;
    public static float movement_speed;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE;
    public String player_colour;

    public static final int FPS = 60;
    public boolean stop_game = false;

    public boolean first_clicked = true;
    private boolean start_board = true;
    private Piece pawn;
    private Piece amazon;
    public int count;
    private Piece king;
    private Piece general;
    private Piece chancellor;
    private Piece queen;
    private Piece bishop;
    private Piece camel;
    private Piece knight;
    private Piece rook;
    private Piece archbishop;
    public int y_pawn_promotion_white;
    public int y_pawn_promotion_black;
    public ArrayList<Piece> whiteRook= new ArrayList<Piece>();
    public ArrayList<Piece> blackRook= new ArrayList<Piece>();
    public ArrayList<Piece> blackPiece = new ArrayList<Piece>();
    public ArrayList<Piece> whitePiece = new ArrayList<Piece>();
    public ArrayList<Piece> allPiece = new ArrayList<Piece>();
    private String configPath;
    public char[][] tiles;
    public boolean white_turn = true;
    private boolean correct_click = false;

    // public AudioClip castle_clip;
    // public URL castle;

    // public URL notify;
    // public AudioClip notify_clip;

    // public URL capture;
    // public AudioClip capture_clip;

    // public URL move;
    // public AudioClip move_clip;
    
    public App() {
        this.configPath = "config.json";
    }
    /**
     * method to create the 14x14 board
    */
    
     public void ColourReset(){
        for (int x = 0; x < WIDTH -SIDEBAR; x= x + 48){
            int w = x/48;
            int y= 0;
            if (w%2==0)
                for (int i = 0; i < HEIGHT; i= i +48){
                    int counter = i /48;
                    if (counter%2 ==0){
                        fill(255, 233,197);
                        noStroke();
                        rect(x, y, CELLSIZE, CELLSIZE);
                    }
                    else{
                        fill(209, 133, 12);
                        noStroke();
                        rect(x, y, CELLSIZE, CELLSIZE);
                    }
                    y = y + 48;
                }
            else{
                for (int i = 0; i < HEIGHT; i= i +48){
                    int counter = i /48;
                    if (counter%2 ==0){
                        fill(209, 133, 12);
                        noStroke();
                        rect(x, y, CELLSIZE, CELLSIZE);
                    }
                    else{
                        fill(255, 233,197);
                        noStroke();
                        rect(x, y, CELLSIZE, CELLSIZE);
                    }
                    y = y + 48;
                }
            }
        }
    }
    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
        
    }

    // public void Notify(){
    //     notify = App.class.getResource("notify_1.wav");
    //     notify_clip = Applet.newAudioClip(notify); 
    //     notify_clip.play();
    // }

    // public void Castle(){
    //     castle = App.class.getResource("castle.wav");
    //     castle_clip = Applet.newAudioClip(castle); 
    //     castle_clip.play();
    // }

    // public void move(){
    //     move = App.class.getResource("move.wav");
    //     move_clip = Applet.newAudioClip(move); 
    //     move_clip.play();
    // }

    // public void Capture(){
    //     capture = App.class.getResource("capture.wav");
    //     capture_clip = Applet.newAudioClip(capture); 
    //     capture_clip.play();
    // }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);
        tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        // Load images during setup

        // PImage spr = loadImage("src/main/resources/XXLChess/"+...);

        // load config
        JSONObject conf = loadJSONObject(new File(this.configPath));
        String layout = conf.getString("layout");
        player_colour = conf.getString("player_colour");
        movement_speed = conf.getFloat("piece_movement_speed");
        int counter = 0 ;
        File f = new File(layout);
        try{
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()){
                counter++;
                scan.nextLine();
            }
            scan.close();
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
            System.exit(1);
        }

        String []file_content = new String[counter];

        try {
            Scanner valid_file = new Scanner(f);
            for(int i = 0; i< counter; i++){
                file_content[i] = valid_file.nextLine();
            } 
            valid_file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File error!");
            System.exit(0);
        }
        player_colour = conf.getString("player_colour");
        Piece_setup(counter, file_content);  
        if (player_colour.equals("white")){
      
            y_pawn_promotion_white= 7;
            y_pawn_promotion_black=6;
            for (Piece piece: whitePiece){
                piece.isUser(true);
            }
            for (Piece piece: blackPiece){
                piece.isUser(false);
            }
        }
        else{
            y_pawn_promotion_white= 6;
            y_pawn_promotion_black=7;
            for (Piece piece: whitePiece){
                piece.isUser(false);
            }
            for (Piece piece: blackPiece){
                piece.isUser(true);
            }
        }
    }
    /**
     * empties out a specified tile
    */
    public void shifting_piece_on_board(char letter, int x, int y){
        tiles[y][x] = ' ';
    }
    /**
     * inserts piece letter on the specified tile
    */
    public void placing_piece_on_board(char letter, int new_x, int new_y){
        tiles[new_y][new_x] = letter;
    } 

    /**
     * load the sprite images and initialised them to their respective class 
    */
    public void Piece_setup(int counter, String[] file_content){
        // Notify();
        for (int i = 0; i < counter; i++){
            char[] letter = file_content[i].toCharArray();
            for(int j = 0; j < letter.length;j++){
                if (letter[j] == 'P'){
                    int y = (i*48);
                    int x= (j*48);
                    pawn = new Pawn(x,y, letter[j]);
                    pawn.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    if (j==12 || j == 1){
                        pawn.set_first_move(true);
                    }
                    pawn.setSprite(loadImage("src/main/resources/XXLChess/b-pawn.png"));
                    blackPiece.add(pawn);
                    allPiece.add(pawn);
                }
                if (letter[j] == 'p'){
                    int y = (i*48);
                    int x= (j*48);
                    pawn = new Pawn(x,y, letter[j]);
                    pawn.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    if (j==12 || j == 1){
                        pawn.set_first_move(true);
                    }
                    pawn.setSprite(loadImage("src/main/resources/XXLChess/w-pawn.png"));
                    whitePiece.add(pawn);
                    allPiece.add(pawn);
                }
                if (letter[j] == 'C'){
                    int y = (i*48);
                    int x= (j*48);
                    camel = new Camel(x,y, letter[j]);
                    camel.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    camel.setSprite(loadImage("src/main/resources/XXLChess/b-camel.png"));
                    blackPiece.add(camel);
                    allPiece.add(camel);
                }

                if (letter[j] == 'c'){
                    int y = (i*48);
                    int x= (j*48);
                    camel = new Camel(x,y, letter[j]);
                    camel.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    camel.setSprite(loadImage("src/main/resources/XXLChess/w-camel.png"));
                    whitePiece.add(camel);
                    allPiece.add(camel);
                }

                if (letter[j] == 'N'){
                    int y = (i*48);
                    int x= (j*48);
                    knight = new Knight(x,y, letter[j]);
                    knight.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    knight.setSprite(loadImage("src/main/resources/XXLChess/b-knight.png"));
                    blackPiece.add(knight);
                    allPiece.add(knight);
                }

                if (letter[j] == 'n'){
                    int y = (i*48);
                    int x= (j*48);
                    knight = new Knight(x,y, letter[j]);
                    knight.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    knight.setSprite(loadImage("src/main/resources/XXLChess/w-knight.png"));
                    whitePiece.add(knight);
                    allPiece.add(knight);
                }
                if (letter[j] == 'B'){
                    int y = (i*48);
                    int x= (j*48);
                    bishop = new Bishop(x,y, letter[j]);
                    bishop.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    bishop.setSprite(loadImage("src/main/resources/XXLChess/b-bishop.png"));
                    blackPiece.add(bishop);
                    allPiece.add(bishop);
                }

                if (letter[j] == 'b'){
                    int y = (i*48);
                    int x= (j*48);
                    bishop = new Bishop(x,y, letter[j]);
                    bishop.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    bishop.setSprite(loadImage("src/main/resources/XXLChess/w-bishop.png"));
                    whitePiece.add(bishop);
                    allPiece.add(bishop);
                }

                if(letter[j] == 'R'){
                    int y = (i*48);
                    int x= (j*48);
                    rook = new Rook(x,y, letter[j]);
                    rook.insert_tiles(letter[j], x/48, y/48);
                    rook.set_first_move(true);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    rook.setSprite(loadImage("src/main/resources/XXLChess/b-rook.png"));
                    blackPiece.add(rook);
                    allPiece.add(rook);
                    blackRook.add(rook);
                }

                if(letter[j] == 'r'){
                    int y = (i*48);
                    int x= (j*48);
                    rook = new Rook(x,y, letter[j]);
                    rook.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    rook.setSprite(loadImage("src/main/resources/XXLChess/w-rook.png"));
                    whitePiece.add(rook);
                    rook.set_first_move(true);
                    allPiece.add(rook);
                    whiteRook.add(rook);
                }
                if(letter[j] == 'H'){
                    int y = (i*48);
                    int x= (j*48);
                    archbishop = new ArchBishop(x,y, letter[j]);
                    archbishop.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    archbishop.setSprite(loadImage("src/main/resources/XXLChess/b-archbishop.png"));
                    blackPiece.add(archbishop);
                    allPiece.add(archbishop);
                }

                if(letter[j] == 'h'){
                    int y = (i*48);
                    int x= (j*48);
                    archbishop = new ArchBishop(x,y, letter[j]);
                    archbishop.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    archbishop.setSprite(loadImage("src/main/resources/XXLChess/w-archbishop.png"));
                    whitePiece.add(archbishop);
                    allPiece.add(archbishop);
                }

                if (letter[j] == 'K'){
                    int y = (i*48);
                    int x= (j*48);
                    king = new King(x,y, letter[j]);
                    king.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    king.setSprite(loadImage("src/main/resources/XXLChess/b-king.png"));
                    blackPiece.add(king);
                    allPiece.add(king);
                }
                if (letter[j] == 'k'){
                    int y = (i*48);
                    int x= (j*48);
                    king = new King(x,y, letter[j]);
                    king.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    king.setSprite(loadImage("src/main/resources/XXLChess/w-king.png"));
                    whitePiece.add(king);
                    allPiece.add(king);
                }
                if (letter[j] == 'G'){
                    int y = (i*48);
                    int x= (j*48);
                    general = new General(x,y, letter[j]);
                    general.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    general.setSprite(loadImage("src/main/resources/XXLChess/b-knight-king.png"));
                    blackPiece.add(general);
                    allPiece.add(general);
                }
                if(letter[j] == 'g'){
                    int y = (i*48);
                    int x= (j*48);
                    general = new General(x,y, letter[j]);
                    general.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    general.setSprite(loadImage("src/main/resources/XXLChess/w-knight-king.png"));
                    whitePiece.add(general);
                    allPiece.add(general);
                }
                if(letter[j] == 'E'){
                    int y = (i*48);
                    int x= (j*48);
                    chancellor = new Chancellor(x,y, letter[j]);
                    chancellor.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    chancellor.setSprite(loadImage("src/main/resources/XXLChess/b-chancellor.png"));
                    blackPiece.add(chancellor);
                    allPiece.add(chancellor);
                }
                if(letter[j] == 'e'){
                    int y = (i*48);
                    int x= (j*48);
                    chancellor = new Chancellor(x,y, letter[j]);
                    chancellor.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    chancellor.setSprite(loadImage("src/main/resources/XXLChess/w-chancellor.png"));
                    whitePiece.add(chancellor);
                    allPiece.add(chancellor);
                }
                if(letter[j] == 'A'){
                    int y = (i*48);
                    int x= (j*48);
                    amazon = new Amazon(x,y, letter[j]);
                    amazon.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    amazon.setSprite(loadImage("src/main/resources/XXLChess/b-amazon.png"));
                    blackPiece.add(amazon);
                    allPiece.add(amazon);
                }
                if(letter[j] == 'a'){
                    int y = (i*48);
                    int x= (j*48);
                    amazon = new Amazon(x,y, letter[j]);
                    amazon.insert_tiles(letter[j], x/48, y/48);
                    placing_piece_on_board(letter[j], x/48, y/48);
                    amazon.setSprite(loadImage("src/main/resources/XXLChess/w-amazon.png"));
                    whitePiece.add(amazon);
                    allPiece.add(amazon);
                }
            }
        }   
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(){
        if(key == 'r'){
            //play music
            whiteRook= new ArrayList<Piece>();
            blackRook= new ArrayList<Piece>();
            blackPiece = new ArrayList<Piece>();
            whitePiece = new ArrayList<Piece>();
            allPiece = new ArrayList<Piece>();
            white_turn = true;
            first_clicked  =true;
            ColourReset();
            setup();
        }
        if (key == 'e'){
           stop_game = true;
        }
            
    }
    /**
     * Check if tile selected occupies a friend or(empty space or foe) and returns a boolean
     * true if ally and false if empty or foe
    */
    public boolean alliesSpot(char letter, int x, int y){
        char spot = tiles[y][x];
        boolean same_case = Character.isUpperCase(spot)== Character.isUpperCase(letter);
        if (same_case){
            return true;
        }
        else{
            return false;
        } 
    }

    /**
     * Returns a boolean if tile selected is an empty space
    */
    public boolean empty_space( int x, int y){
        if (tiles[y][x]==' '){
            return true;
        }
        return false;
    }
    /**
     * when white pawn reaches onto a certain rank, it will become a queen
    */
    public void white_queen_setup(Piece piece){
        queen = new Queen(piece.get_destination_x()*48, piece.get_destination_y()*48, 'q');
        queen.setSprite(loadImage("src/main/resources/XXLChess/w-queen.png"));
        allPiece.add(queen);
        piece.been_taken(true);
        whitePiece.add(queen);
        shifting_piece_on_board(queen.get_letter(), piece.getX()/48, piece.getY()/48);
        placing_piece_on_board('q', piece.get_destination_x(), piece.get_destination_y());
        queen.insert_tiles(' ', queen.getX()/48, queen.getY()/48);
        queen.insert_tiles(queen.get_letter(), queen.getX()/48, queen.getY()/48);
    }
    /**
     * when black pawn reaches onto a certain rank, it will become a queen
    */
    public void black_queen_setup(Piece piece){
        queen = new Queen(piece.get_destination_x()*48, piece.get_destination_y()*48, 'Q');
        queen.setSprite(loadImage("src/main/resources/XXLChess/b-queen.png"));
        allPiece.add(queen);
        piece.been_taken(true);
        blackPiece.add(queen);
        shifting_piece_on_board(queen.get_letter(), piece.getX()/48, piece.getY()/48);
        placing_piece_on_board('Q', piece.get_destination_x(), piece.get_destination_y());

    }
    /**
     * method for when white king wants to castle
    */
    public void white_castling(Piece piece){
        int row = piece.get_destination_x();
        for (Piece black: whiteRook){
            if (row<7){
                if (black.first_move==true && black.is_left_rook){
                    black.set_castling(true);
                    System.out.println("castle left");
                    black.set_clicked(true);
                    shifting_piece_on_board(black.get_letter(), black.getX()/48, black.getY()/48);
                    placing_piece_on_board(black.get_letter(), piece.destination_x+1, piece.destination_y); 
                    black.set_destination(piece.destination_x+1, piece.destination_y);
                    // Castle();
                    break;
                }
            }
            
            if (black.first_move==true&& black.is_right_rook){
                black.set_castling(true);
                System.out.println("castle right");
                black.set_clicked(true);

                shifting_piece_on_board(black.get_letter(), black.getX()/48, black.getY()/48);
                placing_piece_on_board(black.get_letter(), piece.destination_x-1, piece.destination_y); 
                black.set_destination(piece.destination_x-1, piece.destination_y);
                // Castle();
                break;
            }
        }
    }
    /**
     * method for when black king wants to castle
    */
    public void black_castling(Piece piece){
        int row = piece.get_destination_x();
        for (Piece black: blackRook){
            if (black.get_letter()=='R'){
                if (row<7){
                    if (black.first_move==true && black.is_left_rook){
                        black.set_castling(true);
                        System.out.println("castle left");
                        black.set_clicked(true);
                        shifting_piece_on_board(black.get_letter(), black.getX()/48, black.getY()/48);
                        placing_piece_on_board(black.get_letter(), piece.destination_x+1, piece.destination_y); 
                        black.set_destination(piece.destination_x+1, piece.destination_y);
                        // Castle();
                        break;
                    }
                }
                else{
                    if (black.first_move==true&& black.is_right_rook){
                        black.set_castling(true);
                        System.out.println("castle right");
                        black.set_clicked(true);
                        shifting_piece_on_board(black.get_letter(), black.getX()/48, black.getY()/48);
                        placing_piece_on_board(black.get_letter(), piece.destination_x-1, piece.destination_y); 
                        black.set_destination(piece.destination_x-1, piece.destination_y);
                        // Castle();
                        break;
                    }
                }
                
            }
        }
    }

    @Override
    /**
     * method for when a mouse is pressed
    */
    public void mousePressed(MouseEvent e) {
        int x = e.getX()/CELLSIZE;
        int y = e.getY()/CELLSIZE;
        correct_click = false;
        
        if(!stop_game){
            // for (Piece piece: blackPiece){
            //     if (piece.get_check()&&piece.get_letter()=='K'){
            //         fill(255,0,0);
            //         rect(piece.getX(), piece.getY(),48,48);
            //     }
            // }

            // for (Piece piece: whitePiece){
            //     if (piece.get_check()&&piece.get_letter()=='k'){
            //         fill(255,0,0);
            //         rect(piece.getX(), piece.getY(),48,48);
            //     }
            // }
            if(!empty_space(x, y)){
                count++;
                fill(124,252,0);
                rect(x*48,y*48,48,48);
                if (white_turn){
                    if (count>1){
                        ColourReset();
                        fill(124,252,0,10);
                        rect(x*48,y*48,48,48);
                        count = 0;
                    }
                }
                else{
                    if(count>1){
                        ColourReset();
                        fill(124,252,0,10);
                        rect(x*48,y*48,48,48);
                        count = 0;
                    }
                }
            }
            if (first_clicked){
                System.out.println("FIRST clicked!");

                for (Piece piece: blackPiece){
                    if (piece.get_check()){
                        fill(255,0,0);
                        rect(piece.getX(), piece.getY(),48,48);
                    }
                }
           
                if (white_turn){
                    System.out.println("White turn");
                    for (Piece piece: whitePiece){
                        for (Piece k: blackPiece){
                            if (k.get_letter()=='K'&&k.get_check()){
                                fill(255,0,0);
                                rect(k.getX(), k.getY(),48,48);
                            }
                        }
                        piece.put_tiles(tiles);
                        if (piece.getX()/48 == x && piece.getY()/48 ==y){
                            System.out.println();
                            correct_click = true;
                            fill(170,180,90,10);
                            rect(piece.getX()/48, piece.getY()/48, 48,48);
                            List<int[]> moves = piece.possibleMoves(player_colour);
                            
                            piece.set_chosen(true);
                            if (moves.size()==0){
                                correct_click = false;
                                piece.set_chosen(false);
                            } 
    
                            if (correct_click){
                                first_clicked = false;
                                white_turn = false;
                            }
                        }
                        
                    }
                    for (Piece piece: allPiece){
                        piece.possibleMoves(player_colour);
                    }           
                    
                }
                else{
                    System.out.println("Black turn");
                    for (Piece piece: blackPiece){
                        for (Piece k: blackPiece){
                            if (k.get_letter()=='K'&&k.get_check()){
                                
                                fill(255,0,0);
                                rect(k.getX(), k.getY(),48,48);
                            }
                        }
                        for (Piece k: whitePiece){
                            if (k.get_check()){
                                fill(255,0,0);
                                rect(k.getX(), k.getY(),48,48);
                            }
                        }
                        piece.put_tiles(tiles);
                        if (piece.getX()/48 == x && piece.getY()/48 ==y){
                            correct_click = true;
                            List<int[]> moves = piece.possibleMoves(player_colour);
                            for (int i = 0; i< moves.size();i++){
                                System.out.println("possible moves");
                                int []coord = moves.get(i);
                                System.out.println(coord[0] + " " + coord[1]);
                            }
                            piece.set_chosen(true);
                            if (moves.size()==0){
                                correct_click = false;
                                piece.set_chosen(false);
                            } 
                            
                            if (correct_click){
                                first_clicked = false;
                                white_turn = true;
                            }            
                        }
                    }
                }
                for(Piece piece:allPiece){
                    piece.possibleMoves("whtie");
                }
            }
    
            else{
                System.out.println("This is for second click");
                for (Piece k: blackPiece){
                    if (k.get_letter()=='K'&&k.get_check()){
                        
                        fill(255,0,0);
                        rect(k.getX(), k.getY(),48,48);
                    }
                }
                for (Piece piece: whitePiece){
                    if (piece.get_check()){
                        fill(255,0,0);
                        rect(piece.getX(), piece.getY(),48,48);
                    }
                }
                for (Piece piece: allPiece){
                    piece.put_tiles(tiles);
                    piece.possibleMoves(player_colour);
                    if (piece.get_chosen()){
                        List<int[]> moves = piece.moves;
                        piece.set_chosen(false);
                        for (int i = 0; i< moves.size();i++){
                            int[] coord = moves.get(i);
                            if (x==coord[0]&& y==coord[1]){
                                piece.set_clicked(true);
                                correct_click = true;
                                piece.set_destination(x, y);
                                
                                if (piece.get_letter()=='p'){
 
                                    if (piece.destination_y==y_pawn_promotion_white){
   
                                        piece.turns_queen=true;
                                    }
                                }
                                if (piece.get_letter()=='P'){
    
                                    if (piece.destination_y==y_pawn_promotion_black){

                                        piece.turns_queen=true;
                                    }
                                }
                                // move();
                                if (!empty_space(x, y)){
                                    if (!alliesSpot(piece.get_letter(), x, y)){
                                        for (Piece eliminate: allPiece){
                                            if (eliminate.getX()/48 == x && eliminate.getY()/48 ==y){
                                                eliminate.been_taken(true);
                                            }
                                        }
                                    }
                                piece.insert_tiles(' ', piece.getX()/48, piece.getY()/48);
                                piece.insert_tiles(piece.get_letter(), x,y);
                                shifting_piece_on_board(piece.get_letter(), piece.getX()/48, piece.getY()/48);
                                placing_piece_on_board(piece.get_letter(), x, y); 
                                }
                                if (tiles[coord[1]][coord[0]]=='K'){
                                    for (Piece k: blackPiece){
                                        if (k.get_letter()=='K'){
                                            k.set_check(true);
                                            fill(255,0,0);
                                            rect(k.getX(), k.getY(),48,48);
                                        }
                                    }
                                }
                            }
                        }
                        
                        //check the location
                        if (piece.get_letter()=='K'){
                            System.out.println("CASTLING");
                            int diff_x = Math.abs(piece.get_destination_x() - (piece.getX()/48));
                            //determine left or right castling
                            if (diff_x==2){
                                black_castling(piece);
                            }
                        }
                        if (piece.get_letter()=='k'){
                            System.out.println("CASTLING");
                            int diff_x = Math.abs(piece.get_destination_x() - (piece.getX()/48));
                            //determine left or right castling
                            if (diff_x==2){
                                white_castling(piece);
                            }
                        }
                        if (correct_click){
                            first_clicked = true;
                        }
                        else{
                            first_clicked = false;
                        }
                        
                    }
    
                    } 
                               
                    for (int i = allPiece.size()-1; i>=0;i--){
                        if (allPiece.get(i).get_taken()){
                            Piece piece = allPiece.get(i);
                            allPiece.remove(piece);
                        }
                        
                    } 

                    for (int i = whitePiece.size()-1; i>=0;i--){
                        Piece piece = whitePiece.get(i);
                        if (piece.get_letter()=='p'&&piece.turns_queen == true){
                            white_queen_setup(piece);
                        }
                        if(piece.get_letter()=='k'){
                            System.out.println("This is for white king check");
                            System.out.println(piece.get_check());
                        }
                        if (whitePiece.get(i).get_taken()){
                            if(piece.get_letter()=='k'){
                                piece.set_check(true);
                                fill(255,0,0);
                                rect(piece.getX(), piece.getY(),48,48);
                                stop_game = true;
                            }
                            whitePiece.remove(piece);
                            // Capture();
                        }
                    }
                    for (int i = blackPiece.size()-1; i>=0;i--){
                        Piece piece = blackPiece.get(i);
                        if (piece.get_letter()=='P'&&piece.turns_queen == true){
                            black_queen_setup(piece);
                        }
                        if (blackPiece.get(i).get_taken()){
                            
                            if(piece.get_letter()=='K'){
                                piece.set_check(true);
                                fill(255,0,0);
                                rect(piece.getX(), piece.getY(),48,48);
                                stop_game = true;
                            }
                            blackPiece.remove(piece);
             
                        }
                    }
                }
                for(Piece piece: whitePiece){
                    List<int[]> moves = piece.possibleMoves(player_colour);
                    for (int i = 0; i< moves.size();i++){
                        int[] coord = moves.get(i);
                        for(Piece pieces: blackPiece){
                            if (pieces.getX()==coord[0]&& pieces.getY()==coord[1]&&pieces.get_letter()=='K'){
                                piece.set_check(true);
                                fill(255,0,0);
                                rect(pieces.getX(), pieces.getY(),48,48);
                            }
                        }   
                    }
                }
                for(Piece piece: blackPiece){
                    List<int[]> moves = piece.possibleMoves(player_colour);
                    for (int i = 0; i< moves.size();i++){
                        int[] coord = moves.get(i);
                        for(Piece pieces: whitePiece){
                            if (pieces.getX()==coord[0]&& pieces.getY()==coord[1]&&pieces.get_letter()=='K'){
                                piece.set_check(true);
                                fill(255,0,0);
                                rect(pieces.getX(), pieces.getY(),48,48);
                            }
                        }   
                    }
                }
        } 
        
        
}

    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw() {
        if (start_board){
            ColourReset();
            start_board = false;
        }
    
        for (Piece e: allPiece){
            if (e.clicked){
                //previously moved piece 
                e.tick(player_colour);
                ColourReset();
                fill(255,255,0,90);
                rect(e.prev_x,e.prev_y,48,48);
                fill(255,255,0,90);
                rect(e.destination_x*48,e.destination_y*48,48,48);
            }

            for (Piece piece: blackPiece){
                if (piece.get_check()){
                    fill(255,0,0);
                    rect(piece.getX(), piece.getY(),48,48);
                }
            }

            for (Piece piece: whitePiece){
                if (piece.get_check()){
                    fill(255,0,0);
                    rect(piece.getX(), piece.getY(),48,48);
                }
            }
            
            if (e.chosen){
                List<int[]> moves = e.get_moves();
                for (int i = 0; i< moves.size();i++){
                    int[] coord = moves.get(i);
                    fill(170,180,90,10);
                    noStroke();
                    rect(e.getX(), e.getY(),48,48);
                    if (coord[1]%2==0){
                        fill(177,232,238,10);
                        noStroke();
                        rect(coord[0]*48, coord[1]*48,48,48);
                    }
                    else{
                        fill(177,232,238,10);
                        noStroke();
                        rect(coord[0]*48, coord[1]*48,48,48);
                    }

                    if (!empty_space(coord[0], coord[1])  && !alliesSpot(e.get_letter(), coord[0], coord[1])){

                        if(tiles[coord[1]][coord[0]] == 'k' || tiles[coord[1]][coord[0]]=='K'){
                            if(tiles[coord[1]][coord[0]] == 'k'){
                                for (Piece piece: whitePiece){
                                    if(piece.get_letter()=='k'){
                                        piece.set_check(true);
                                        fill(255,0,0);
                                        rect(coord[0]*48, coord[1]*48,48,48);
                                    }
                                }
                            }
                            if (tiles[coord[1]][coord[0]] == 'K'){
                                for (Piece piece: blackPiece){
                                    if(piece.get_letter()=='K'){
                                        piece.set_check(true);
                                        fill(255,0,0);
                                        rect(coord[0]*48, coord[1]*48,48,48);
                                    }
                                }
                            }
                            
                        }
                        else{
                            fill(255, 204, 203,200);
                            noStroke();
                            rect(coord[0]*48, coord[1]*48,48,48);
                        }  
                    }
                }
                
            }
        }

        for (Piece e: allPiece){
            if (!e.get_taken()){
                e.draw(this);
            }
        }  
             
    }
    
    // Add any additional methods or attributes you want. Please put classes in different files.
    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }
}