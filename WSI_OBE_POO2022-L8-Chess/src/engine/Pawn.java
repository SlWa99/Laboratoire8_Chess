package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.ArrayList;
import java.util.LinkedList;
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
        return (!hasMoved && deltaY == -2*facing && deltaX == 0) || (deltaY == -facing && deltaX >= -1 && deltaX <= 1);
    }

    @Override
    List<List<Coord>> listMove() {
        List<List<Coord>> vectors = new LinkedList<>();
        try{
            List<Coord> v = new LinkedList<>();
            v.add(new Coord(x,y + facing));
            if(!hasMoved){
                v.add(new Coord(x,y + 2*facing));
            }
            vectors.add(v);
        } catch(RuntimeException e){System.out.println(e.getMessage());}
        return vectors;
    }

    @Override
    List<List<Coord>> listEatingMove() {
        List<List<Coord>> vectors = new LinkedList<>();
        List<Coord> v;
        for(int i = -1; i < 2;i += 2){
            try{
                v = new LinkedList<>();
                v.add(new Coord(x + i,y + facing));
                vectors.add(v);
            } catch(RuntimeException e){System.out.println(e.getMessage());}
        }
        return vectors;
    }
}
