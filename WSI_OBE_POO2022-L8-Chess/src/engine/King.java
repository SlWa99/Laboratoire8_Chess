package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.ArrayList;
import java.util.List;
// @author : obaume
public class King extends Piece{

    public King(int x, int y, PlayerColor color) {
        super(x, y, color, PieceType.KING);
    }

    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = this.x - toX;
        int deltaY = this.y - toY;

        return ((deltaX * deltaX) <= 1 && deltaY * deltaY <= 1) ||
               (!hasMoved && ((color == PlayerColor.WHITE) && toY == 0 && (toX == 0 || toX == 7) ||
               ((color == PlayerColor.BLACK) && toY == 7 && (toX == 0 || toX == 7))));
    }

    @Override
    List<List<Coord>> listMove() {
        List<List<Coord>> vectors = new ArrayList<>();
        List<Coord> v = new ArrayList<>();
        for(int i = -1; i < 2; ++i){
            for(int j = -1; j < 2; ++j){
                if(!(i==0&&j==0)){
                    try{
                        v = new ArrayList<Coord>();
                        v.add(new Coord(this.x + i, this.y +j));
                        vectors.add(v);
                    }catch (RuntimeException e){System.out.println(e.getMessage());}

                }
            }
        }
        return vectors;
    }

    @Override
    List<List<Coord>> listEatingMove() {
        return listMove();
    }
}
