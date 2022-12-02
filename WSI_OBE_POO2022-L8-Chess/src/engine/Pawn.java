package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.ArrayList;
import java.util.List;
// @author : obaume
public class Pawn extends Piece{

    private int facing;
    public Pawn(int x, int y, PlayerColor color) {
        super(x, y, color, PieceType.PAWN);
        if(color == PlayerColor.WHITE) facing = 1;
        else facing = -1;
    }

    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = this.x - toX;
        int deltaY = this.y - toY;
        return deltaX == -facing && deltaY >= -1 && deltaY <= 1;
    }

    @Override
    List<List<Coord>> listMove() {
        List<List<Coord>> vectors = new ArrayList<>();
        try{
            List<Coord> v = new ArrayList<>();
            v.add(new Coord(x + facing,y));
            vectors.add(v);
        } catch(RuntimeException e){System.out.println(e.getMessage());}
        return vectors;
    }

    @Override
    List<List<Coord>> listEatingMove() {
        return null;
    }
}
