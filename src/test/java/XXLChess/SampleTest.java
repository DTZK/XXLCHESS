package XXLChess;
import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import javax.lang.model.type.NullType;

public class SampleTest {

    @Test
    /*
    *running my pieces from this new app to test them out
    simulating what happens if a certain condtion occurs
    */ 
    public void simpleTest() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.delay(1000);
        app.setup();
        app.delay(1000); // to give time to initialise stuff before drawing begins
        //assertEquals(expected, app.powerUp(500));

        MouseEvent e = new MouseEvent(app, 100, 0, 0, 10*48, 10*48, 0, 0);
        app.mousePressed(e);
        e = new MouseEvent(app, 100, 0, 0, 12*48, 12*48, 0, 0);
        app.mousePressed(e);

        app.white_turn=true;
        app.first_clicked = false;
        app.count=2;
        e = new MouseEvent(app, 0, 0, 0, 12*48, 11*48, 0, 0);
        app.mousePressed(e);
        app.empty_space(12, 10);

        e = new MouseEvent(app, 100, 0, 0, 0*48, 13*48, 0, 0);
        app.mousePressed(e);

        e = new MouseEvent(app, 100, 0, 0, 0*48, 5*48, 0, 0);
        app.mousePressed(e);

        app.first_clicked = true;
        app.white_turn = false;
        e = new MouseEvent(app, 100, 0, 0, 0*48, 1*48, 0, 0);
        app.mousePressed(e);
        app.empty_space(12, 10);

        e = new MouseEvent(app, 100, 0, 0, 0*48, 2*48, 0, 0);
        app.mousePressed(e);

        app.first_clicked = true;
        app.white_turn = false;
        e = new MouseEvent(app, 100, 0, 0, 0*48, 0*48, 0, 0);
        app.mousePressed(e);
        app.empty_space(12, 10);

        e = new MouseEvent(app, 100, 0, 0, 0*48, 5*48, 0, 0);
        app.mousePressed(e);
        //determining if my pieces can operate similarly in App.java
        for (Piece piece: app.whitePiece){
            piece.set_Value();
            assertEquals(piece.get_Value(), piece.get_Value());
            piece.set_chosen(false);
            List<int[]> moves = piece.possibleMoves("White");

            piece.set_chosen(true);
            piece.possibleMoves("white");
            piece.tick("WHITE");
            piece.tick("WHITE");
            piece.tick("WHITE");

            char[][] tiles = new char[14][14];
            for (int i = 0; i<14;i++){
                for (int j = 0 ; j<14;j++){
                    tiles[i][j] = ' ';
                }
            }
            piece.set_chosen(true);
            moves = piece.possibleMoves("White");
            piece.tick("white");
            piece.tick("white");
            piece.tick("white");

            for (int i = 0; i<14;i++){
                for (int j = 0 ; j<14;j++){
                    tiles[i][j] = 'D';
                }
            }
            tiles[piece.getY()/48][piece.getX()/48] = piece.get_letter();
            piece.set_chosen(true);
            moves = piece.possibleMoves("White");
            piece.tick("white");
            piece.tick("white");
            piece.tick("white");

        }
        for (Piece piece: app.allPiece){
            if (piece.get_letter()=='K'){
                app.black_castling(piece);
                piece.set_Value();
                assertEquals(99.0, piece.get_Value());
                piece.set_clicked(true);
                app.draw();
            }
            if (piece.get_letter()=='H'){
                piece.set_clicked(true);
                piece.set_chosen(true);
                piece.set_Value();
                assertEquals(7.5, piece.get_Value());
                piece.tick("Black");
                app.draw();
            }

            if (piece.get_letter()=='k'){
                app.white_castling(piece);
                piece.set_Value();
                assertEquals(99.0, piece.get_Value());
                piece.set_clicked(true);
                app.draw();
            }
            if (piece.get_letter()=='p'){
                piece.set_clicked(true);
                piece.tick("white");
                piece.set_Value();
                assertEquals(1.0, piece.get_Value());
                piece.isUser(true);
                app.draw();
            }
            if (piece.get_letter()=='p'){
                piece.been_taken(true);
                piece.set_first_move(true);
                app.draw();
            }
            if (piece.get_letter()=='P'){
                app.mousePressed();
                piece.set_Value();
                assertEquals(1.0, piece.get_Value());
                app.mouseX = piece.getX();
                app.mouseY = piece.getY()-2;
                
                app.first_clicked = true;
                app.stop_game = false;
                piece.set_destination(piece.getX()/48, piece.getY()/48 -2);
                piece.set_chosen(true);
                piece.possibleMoves("white");
                piece.tick("white");
                app.draw();
            }
            if (piece.get_letter()=='n'){
                app.mousePressed();
                app.mouseX = piece.getX();
                app.mouseY = piece.getY()-2;
                piece.set_Value();
                assertEquals(2.0, piece.get_Value());
                app.first_clicked = true;
                app.stop_game = false;
                piece.set_destination(piece.getX()/48 +1, piece.getY()/48 -2);
                piece.set_chosen(true);

                assertTrue(piece.empty_space(piece.getX()/48 +1, piece.getY()/48 -2));
                assertTrue(piece.alliesSpot('n',piece.getX()/48 +1, piece.getY()/48 -2));
                
                piece.tick("white");
                app.draw();
            }
            if (piece.get_letter()=='R'){
                List<int[]> moves = piece.moves;
                piece.set_Value();
                assertEquals(5.25, piece.get_Value());
                app.draw();
            }
            
        }
        //testing if app.white_queen method works
        
        //testing if method works
        app.shifting_piece_on_board('w',0,0);
        assertEquals(app.empty_space(0, 0), app.empty_space(0, 0));
        app.placing_piece_on_board('k', 0, 0);
        assertFalse(app.empty_space(0,0));

        app.keyPressed();
        if (app.key == 'r'){
            assertEquals(0,app.whitePiece.size());
            assertEquals(0, app.blackPiece.size());
            app.keyPressed();

        }
        if (app.key!='r'){
            assertFalse(app.whitePiece.size()==0);
            assertFalse(app.stop_game==true);
            app.mousePressed(e);
            app.keyPressed();
            
        }
        if (app.key=='e'){
            assertTrue(app.stop_game);
            app.mousePressed(e);
            app.keyPressed();
        }
        if (app.key!='e'){
            assertFalse(app.stop_game);
            app.mousePressed(e);
            app.keyPressed();
        }
        assertFalse( app.alliesSpot('K', 0, 0));
        assertTrue(app.alliesSpot('k', 0, 0));


        for (Piece piece: app.allPiece){
            if (piece.get_letter()=='K'){
                piece.set_destination(8, 0);

                app.black_castling(piece);
            }
            
        }
        for (Piece piece: app.allPiece){
            if(piece.get_letter()=='K'){
                app.black_castling(piece);
                app.draw();
            }
        }

        Rook rook = new Rook(3*48,3*48,'s');

        app.blackRook.add(0,rook);

        //checking if my rook can castle if it has a different letter
        for (Piece piece: app.allPiece){
            if (piece.get_letter()=='K'){
                app.black_castling(piece);
            }
        }
        // checking if my rook can castle if first move is false
        for (Piece piece: app.allPiece){
            if (piece.get_letter()=='R'){
                piece.set_first_move(false);
                piece.is_left_rook= true;
            } 
        }

        for (Piece piece: app.allPiece){
            if (piece.get_letter()=='K'){
                app.black_castling(piece);
            }
            
        }
        e = new MouseEvent(app, 100, 0, 0, 8*48, 0*48, 0, 0);
        app.mousePressed(e);
        app.empty_space(8, 0);

        app.stop_game = true;
        app.mousePressed(e);

        for (Piece piece: app.allPiece){
            if (piece.get_letter()=='k'){
                piece.set_destination(8, 13);

                app.white_castling(piece);
            }
            
        }
        for (Piece piece: app.allPiece){
            if(piece.get_letter()=='k'){
                app.white_castling(piece);
                app.draw();
            }
        }
        
         Rook white_rook = new Rook(3*48,3*48,'s');
         app.whiteRook.add(0,white_rook);
         for (Piece piece: app.allPiece){
            if (piece.get_letter()=='k'){
                app.white_castling(piece);
            }
        }
        for (Piece piece: app.allPiece){
            if (piece.get_letter()=='r'){
                piece.set_first_move(false);
                piece.is_left_rook= true;
            } 
        }

        for (Piece piece: app.allPiece){
            if (piece.get_letter()=='k'){
                app.white_castling(piece);
            }
            
        }
        //first and second clicks are occupied
        app.first_clicked = true;
        e = new MouseEvent(e, 0, 0, 0, 0, 0, 0, 0);
        app.white_turn = true;
        app.mousePressed(e);
        
        e = new MouseEvent(e, 0, 0, 0, 0, 12*48, 0, 0);
        app.mousePressed(e);

        app.mousePressed(e);
        Piece piece = app.whitePiece.get(0);
        piece.been_taken(true);
        app.mousePressed(e);

        for (Piece p: app.blackPiece){
            if (p.get_letter()=='K'){
                p.is_check = true;
            }
            if (p.get_letter()!='K'){
                p.is_check = true;
            }
            if (p.get_letter()=='P'){
                p.been_taken(true);
            }
            if (p.get_letter()=='H'){
                p.set_destination(8, 1);
            }
        }
        for (Piece p: app.whitePiece){
            if (p.get_letter()=='k'){
                p.is_check = true;
            }
            if (p.get_letter()=='p'){
                p.been_taken(true);
            }
        }
        app.mousePressed(e);
        app.draw();

        for (Piece p: app.blackPiece){
            if (p.get_letter()=='K'){
                p.is_check = true;
            }
            if (p.get_letter()!='K'){
                p.is_check = true;
            }
            if (p.get_letter()=='P'){
                p.been_taken(true);
            }
            if (p.get_letter()=='H'){
                p.set_destination(8, 1);
            }
        }
        for (Piece p: app.whitePiece){
            if (p.get_letter()=='k'){
                p.is_check = true;
            }
            if (p.get_letter()=='p'){
                p.been_taken(true);
            }
        }
        app.first_clicked=false;
        app.mousePressed(e);

        //testing if pawn can be promoted to queen given the conditons
        for (Piece p: app.whitePiece){
            if (p.get_letter()=='k'){
                p.is_check = true;
            }
            p.is_check=  true;
            if (p.get_letter()=='p'){
                p.turns_queen = true;
                p.set_destination(p.getX()/48,app.y_pawn_promotion_white);
            }
        }
        for (Piece p: app.blackPiece){
            if (p.get_letter()=='K'){
                p.is_check = true;
            }
            p.is_check=  true;
            if (p.get_letter()=='P'){
                p.turns_queen = true;
                p.set_destination(p.getX()/48,app.y_pawn_promotion_black);
            }
        }
        
        
        
        app.first_clicked = false;
        app.mousePressed(e);

        for (Piece p: app.whitePiece){
            if (p.get_letter()=='k'){
                p.is_check = true;
            }
            if (p.get_letter()=='P'){
                p.turns_queen = true;
                p.set_destination(p.getX()/48,app.y_pawn_promotion_black);
            }
            
        }
        app.first_clicked = true;
        app.mousePressed(e);

        for (Piece p: app.blackPiece){
            if (p.get_letter()=='K'){
                p.is_check = true;
            }
            
            if (p.get_letter()=='P'){
                p.turns_queen = true;
                p.set_destination(p.getX()/48,app.y_pawn_promotion_black);
            }
        }
        app.first_clicked = true;
        app.mousePressed(e);
        app.draw();

        for (Piece p: app.blackPiece){
            if (p.get_letter()=='K'){
                p.is_check = false;
            }

        }
        app.first_clicked = true;
        app.mousePressed(e);
        app.draw();

        for (Piece p: app.blackPiece){
            if (p.get_letter()=='K'){
                p.is_check = true;
                p.letter = 'L';
            }

        }
        app.first_clicked = false;
        app.mousePressed(e);
        app.draw();

        for (Piece p: app.blackPiece){
            if (p.get_letter()=='K'){
                p.is_check = false;
            }
            p.is_check = true;

        }
        app.first_clicked = true;
        app.mousePressed(e);
        app.draw();

        for (Piece p: app.blackPiece){
            if (p.get_letter()=='P'){
                p.is_check = false;
            }
            p.is_check = true;

        }
        app.first_clicked = true;
        app.mousePressed(e);
        app.draw();
        
        app.tiles[1][0] = 'h';
        for (Piece p: app.whitePiece){
            p.set_chosen(true);
            p.set_destination(1, 0);
        }
        app.tiles[0][1] = 'K';
        app.draw();

        //invalid test for pawn promotions to queen
        for (Piece p: app.whitePiece){
            if (p.get_letter()=='p'){
                p.set_chosen(true);
                p.turns_queen = true;
                p.set_destination(0, 7*48);
            }
        }
        e = new MouseEvent(e, 0, 0, 0, 0, 7*48, 0, 0);
        app.mousePressed(e);

        for (Piece p: app.blackPiece){
            if (p.get_letter()=='P'){
                p.set_chosen(true);
                p.turns_queen = true;
                p.set_destination(0, 6*48);
            }
        }
        e = new MouseEvent(e, 0, 0, 0, 0, app.y_pawn_promotion_black*48, 0, 0);
        app.mousePressed(e);

        for (Piece p: app.whitePiece){
            if (p.get_letter()=='p'){
                p.set_chosen(true);
                p.turns_queen = true;
                p.set_destination(11, app.y_pawn_promotion_white);
            }
            if (p.get_letter()=='k'){
                p.been_taken(true);
            }
        }
        app.white_queen_setup(piece);
        app.first_clicked = false;
        e = new MouseEvent(e, 0, 0, 0, 0, 7*48, 0, 0);
        app.mousePressed(e);

        for (Piece p: app.blackPiece){
            if (p.get_letter()=='P'){
                
                p.set_chosen(true);
                p.turns_queen = true;
                
                p.set_destination(2, app.y_pawn_promotion_black);
            }
            if (p.get_letter()=='K'){
                p.been_taken(true);
            }
        }
        app.black_queen_setup(piece);
        app.first_clicked = false;
        e = new MouseEvent(e, 0, 0, 0, 0, app.y_pawn_promotion_black*48, 0, 0);
        app.mousePressed(e);

        
        
    }

}
