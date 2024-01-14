package XXLChess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


public class GeneralTest {
    @Test
    public void check_camel(){
        Camel general = new Camel(7*48, 5*48, 'g');
        general.set_Value();
        assertEquals(general.get_Value(), general.get_Value());
        List<int[]>moves = general.possibleMoves("white");
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        general.put_tiles(tiles);
        moves = new ArrayList<int[]>();
        moves = general.possibleMoves("white");
        general.set_clicked(true);
        //top right
        general.set_destination(8, 2);
        general.tick("white");
        general.tick("white");
        general.tick("white");
        //bottom right
        general.set_destination(8, 8);
        general.tick("white");
        general.tick("white");
        general.tick("white");
        //top left
        general.set_destination(7-1, 5+3);
        general.tick("white");
        general.tick("white");
        general.tick("white");
        //bottom left
        general.set_destination(7-1, 5-3);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(7-3, 5-1);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(7-3, 6);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(7+3, 7);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(4, 7);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(7, 5);
        general.tick("white");

        tiles[8][2] = 'K';
        tiles[8][8] = 'H';
        tiles[7-1][5+3] = 'N';
        tiles[7-1][5-3] = 'N';
        tiles[7-3][5+1] = 'N';
        tiles[7-3][5-1] = 'N';
        tiles[10][5+1] = 'N';
        tiles[10][5-1] = 'N';
        moves = general.possibleMoves("white");

        tiles[8][2] = 'k';
        tiles[8][8] = 'h';
        tiles[7-1][5+3] = 'n';
        tiles[7-1][5-3] = 'n';
        tiles[7-3][5+1] = 'n';
        tiles[7-3][5-1] = 'n';
        tiles[10][5+1] = 'n';
        tiles[10][5-1] = 'n';
        moves = general.possibleMoves("white");
        assertEquals(general.possibleMoves("white").size(), moves.size());

        general.set_clicked(false);
        general.tick("white");
    }
    @Test
    public void check_general(){
        General general = new General(3*48, 10*48, 'g');
        general.set_Value();
        assertEquals('g', general.get_letter());
        assertEquals(5 , general.get_Value());

        assertEquals(general.getX(), general.current_x);
        assertEquals(general.getY(), general.current_y);

        general.isUser(false);
        assertEquals(false, general.get_isUser());

        general.isUser(true);
        assertEquals(true, general.get_isUser());

        general.set_clicked(false);
        assertEquals(false, general.get_clicked());
        
        general.set_chosen(false);
        assertEquals(false, general.get_chosen());

        general.tick("white");
        general.set_destination(5*48, 7*48);

        general.set_castling(false);
        assertFalse(general.castling);

        general.set_first_move(true);
        assertFalse(!general.get_first_move());
        
        try {
            Thread.sleep(1000);
            assertEquals(3*48, general.getX());
            assertEquals(10*48, general.getY());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(5*48, general.get_destination_x());
        assertEquals(7*48, general.get_destination_y());

        general.set_clicked(true);
        assertEquals(true, general.get_clicked());

        general.set_chosen(true);
        assertEquals(true, general.get_chosen());
        general.set_destination(4*48, 7*48);

        assertEquals(4*48, general.get_destination_x());
        assertEquals(7*48, general.get_destination_y());

        general.tick("black");
        general.tick("black");
        general.tick("black");
        general.tick("black");
        general.tick("black");
        general.tick("black");
        general.tick("black");
        general.tick("black");
    }

    
    /*
    *
    */ 
    @Test
    public void general_possiblemoves(){
        General general = new General(13*48, 5*48, 'g');
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        general.put_tiles(tiles);
        general.isUser(true);
        List<int[]> move = new ArrayList<int[]>();
        move = general.possibleMoves("white");
        assertEquals(9, move.size());
        assertEquals(9, general.get_moves().size());
        int row = 13;
        int column = 5;
        assertEquals(row, general.getX()/48);
        assertEquals(column, general.getY()/48);

        tiles[10][4] = 'n';
        move = general.possibleMoves("white");
        assertEquals(9, move.size());
        general.put_tiles(tiles);
        general.alliesSpot('g', 13,4);
        assertEquals(true, general.alliesSpot('g', 13,4));
        general.been_taken(false);
        assertFalse(general.get_taken());

        tiles[10][4] = 'K';
        assertEquals(9, move.size());
        general.put_tiles(tiles);
        general.alliesSpot('g', 13,4);
        assertEquals(true, general.alliesSpot('g', 13,4));
        general.been_taken(false);
        assertFalse(general.get_taken());

        move = general.possibleMoves("white");
        general.set_destination(13, 5);
        general.set_clicked(true);
        general.tick("white");
    }

    /*
     * all directions of general testing with click on
     */
    @Test
    public void CreateGeneral(){
        General general = new General(7*48, 5*48, 'g');
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        general.put_tiles(tiles);
        List<int[]> moves = new ArrayList<int[]>();
        moves = general.possibleMoves("white");
        general.set_clicked(true);
        //top right
        general.set_destination(8, 2);
        general.tick("white");
        general.tick("white");
        general.tick("white");
        //bottom right
        general.set_destination(8, 8);
        general.tick("white");
        general.tick("white");
        general.tick("white");
        //top left
        general.set_destination(7-1, 5+3);
        general.tick("white");
        general.tick("white");
        general.tick("white");
        //bottom left
        general.set_destination(7-1, 5-3);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(7-3, 5-1);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(7-3, 6);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(7+3, 7);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(4, 7);
        general.tick("white");
        general.tick("white");
        general.tick("white");

        general.set_destination(7, 5);
        general.tick("white");

        tiles[8][2] = 'K';
        tiles[8][8] = 'H';
        tiles[7-1][5+3] = 'N';
        tiles[7-1][5-3] = 'N';
        tiles[7-3][5+1] = 'N';
        tiles[7-3][5-1] = 'N';
        tiles[10][5+1] = 'N';
        tiles[10][5-1] = 'N';
        moves = general.possibleMoves("white");
    }

    
    /*
     * 
     */
    @Test
    public void CreateKnight() {
        Knight knight = new Knight(2*48, 13*48, 'n');
        assertEquals(2*48,knight.getX());
        assertEquals(13*48, knight.getY());
        assertEquals('n', knight.get_letter());

        List<int[]> move = new ArrayList<int[]>();
        move = knight.possibleMoves("white");
        assertEquals(0, move.size());

        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        tiles[12][5] = 'k';
        knight.put_tiles(tiles);
        move = new ArrayList<int[]>();
        move = knight.possibleMoves("white");
        knight.isUser(true);
        assertEquals(true, knight.get_isUser());
        assertEquals(knight.possibleMoves("white").size(), move.size());
        knight.isUser(false);
        assertEquals(false, knight.get_isUser());

        tiles[12][5] = 'K';
        knight.put_tiles(tiles);
        move = knight.possibleMoves("white");
        assertEquals(!true, knight.alliesSpot('n', 5,12));
        assertEquals(4, move.size());

        knight.set_clicked(false);
        assertFalse(knight.get_clicked());
        knight.set_destination(2, 13);
        assertEquals(knight.get_destination_x(), knight.getX()/48);
        assertEquals(knight.get_destination_y(), knight.getY()/48);
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        
        
    }

    /*
     * checking the test cases for knight
     */
    @Test
    public void knight_tick(){
        Knight knight = new Knight(2*48, 13*48, 'n');
        knight.set_Value();
        assertEquals(2.0, knight.get_Value());
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        tiles[10][5] = 'A';
        knight.put_tiles(tiles);
        List<int[]> move = knight.possibleMoves("white");
        assertEquals(4, move.size());
        assertEquals(true, !knight.alliesSpot('n', 5, 10));
        knight.been_taken(false);
        assertEquals(false, knight.get_taken());
        knight.set_chosen(true);
        assertEquals(true, knight.get_chosen());
        knight.set_clicked(true);
        knight.set_destination(1, 11);
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        
        int control = knight.getX()/48;
        int increment = Math.abs(knight.get_destination_x()- control)*6;

        // int divided_current_x = knight.getX()/48;
        // assertEquals(divided_current_x, knight.current_x/48);
        assertEquals(knight.get_destination_x()<knight.getX()/48, knight.get_destination_x()<knight.getX()/48);
        assertEquals(true, knight.get_destination_y()<knight.getY()/48);
        knight.x = knight.destination_x*48;
        knight.y = knight.destination_y*48;
        assertEquals(knight.destination_x*48, knight.x);
        assertEquals(knight.destination_y*48, knight.y);
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
    }

    /*
    * detecting emeny upper right with clicked on
    */ 
    @Test
    public void knight_enemyspotted_tick(){
        Knight knight = new Knight(0, 13*48, 'n');
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        tiles[11][1] = 'K';
        knight.put_tiles(tiles);
        assertEquals(false, knight.alliesSpot('n', 1, 11));
        List moves = knight.possibleMoves("white");
        assertEquals(2, moves.size());
        knight.set_clicked(true);
        knight.set_destination(2, 11);
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.update_previous_coordinate_x(knight.get_destination_x()*48);
        knight.update_previous_coordinate_x(knight.get_destination_y()*48);
    }

    /*
    * detecting emeny upper right with clicked off
    */ 
    @Test
    public void knight_enemy_tick(){
        Knight knight = new Knight(0, 13*48, 'n');
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        tiles[11][1] = 'K';
        knight.put_tiles(tiles);
        assertEquals(false, knight.alliesSpot('n', 1, 11));
        List moves = knight.possibleMoves("white");
        assertEquals(2, moves.size());
        knight.set_clicked(false);
        knight.set_destination(2, 11);
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.update_previous_coordinate_x(knight.get_destination_x()*48);
        knight.update_previous_coordinate_x(knight.get_destination_y()*48);
    }
     /*
    * detecting emeny lower right with clicked off
    */ 
    @Test
    public void knight_move_right_down_on(){
        Knight knight = new Knight(0, 13*48, 'n');
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        tiles[11][1] = 'K';
        knight.put_tiles(tiles);
        assertEquals(false, knight.alliesSpot('n', 1, 11));
        List moves = knight.possibleMoves("white");
        assertEquals(2, moves.size());
        knight.set_clicked(false);
        knight.set_destination(2, 11);
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.update_previous_coordinate_x(knight.get_destination_x()*48);
        knight.update_previous_coordinate_x(knight.get_destination_y()*48);
    }

    /*
    * detecting emeny lower left with clicked on 
    */ 
    @Test
    public void knight_move_right_down_off(){
        Knight knight = new Knight(6*48, 9*48, 'n');
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        knight.set_clicked(true);
        knight.set_destination(4, 11);
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.tick("white");
        knight.update_previous_coordinate_x(knight.get_destination_x()*48);
        knight.update_previous_coordinate_x(knight.get_destination_y()*48);
    }

    /*
    * initialising 
    */ 
    public void archbishop_move_right_down_off(){
        ArchBishop archBishop = new ArchBishop(6*48, 9*48, 'h');
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        List<int[]> moves = archBishop.possibleMoves("white");
        for (int i = 0; i<moves.size();i++){
            int [] coord = moves.get(i);
            archBishop.set_destination(coord[0], coord[1]);
            archBishop.set_clicked(true);
            archBishop.tick("white");
            archBishop.tick("white");
            archBishop.tick("white");
            archBishop.tick("white");
            archBishop.tick("white");
            archBishop.tick("white");
            archBishop.tick("white");
            archBishop.tick("white");
        }
        archBishop.set_destination(6, 9);
        archBishop.tick("white");

        archBishop.set_destination(6, 9);
        archBishop.tick("white");
    }
    @Test
    public void test_queen(){
        Queen queen = new Queen(0, 0, 'q');
        char[][] tiles = new char[14][14];
        for (int i = 0; i<14;i++){
            for (int j = 0 ; j<14;j++){
                tiles[i][j] = ' ';
            }
        }
        List<int[]> moves = queen.possibleMoves("white");
        queen.tick("white");
        queen.set_chosen(true);
        queen.set_destination(0, 0);
        queen.set_clicked(true);
        queen.tick("white");

        queen.set_destination(10, 10);
        queen.tick("white");
        queen.tick("white");
        queen.tick("white");

        tiles[10][10] = 'K';
        queen.set_chosen(true);
        moves = queen.possibleMoves("white");
        queen.set_clicked(true);
    }
}
