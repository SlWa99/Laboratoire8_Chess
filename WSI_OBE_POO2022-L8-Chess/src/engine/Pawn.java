package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;
import engine.util.MoveDiag;
import engine.util.MoveLin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
// @author : obaume
public class Pawn extends Piece{
    // region Constructor
    public Pawn(Coord coord, PlayerColor color) {
        super(coord, color, PieceType.PAWN);
        if(color == PlayerColor.WHITE) facing = 1;
        else facing = -1;
        md = new MoveDiag(1);
        ml = new MoveLin(2);
    }
    // endregion


    // region Parameter
    MoveLin ml;
    MoveDiag md;
    private int facing;
    // endregion


    // region Methods
     @Override
     public void move(int toX,int toY){
         super.move(toX,toY);
         ml = new MoveLin(1);
     }

    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = coord.getX() - toX;
        int deltaY = coord.getY() - toY;
        return (!hasMoved && deltaY == -2*facing && deltaX == 0) || (deltaY == -facing && deltaX >= -1 && deltaX <= 1);
    }

    @Override
    List<List<Coord>> listMove() {
        List<List<Coord>> vect = ml.listMove(coord);
        List<List<Coord>> out = new LinkedList<>();
        for(List<Coord> moves : vect){
            List<Coord> v = new LinkedList<>();
            for(Coord c : moves){
                if(acceptedMove(c.getX(),c.getY())){
                    v.add(c);
                }
                out.add(v);
            }
        }
        return out;
    }

    @Override
    List<List<Coord>> listEatingMove() {
        List<List<Coord>> vect = md.listMove(coord);
        List<List<Coord>> out = new LinkedList<>();
        for(List<Coord> moves : vect){
            List<Coord> v = new LinkedList<>();
            for(Coord c : moves){
                if(acceptedMove(c.getX(),c.getY())){
                    v.add(c);
                }
                out.add(v);
            }
        }
        return out;
    }
    // endregion
}
