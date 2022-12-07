package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
// @author : obaume
public class Bishop extends Piece{

    public Bishop(int x, int y, PlayerColor color) {
        super(x, y, color, PieceType.BISHOP);
    }

    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = this.x - toX;
        int deltaY = this.y - toY;
        if(deltaY == 0 || deltaX == 0) return false;

        return Math.abs(deltaX) == Math.abs(deltaY);
    }

    @Override
    List<List<Coord>> listMove() {
        List<List<Coord>> vectors = new LinkedList<>();
        List<Coord> v = new LinkedList<>();
        int coefX = 1,coefY=1;
        for(int i = 0; i < 4; ++i){
            v = new ArrayList<>();
            for(int j = 1; j < 8; ++j){
                try{
                    v.add(new Coord(this.x + j*coefX,this.y + j*coefY));
                }catch (RuntimeException e){
                    System.out.println(e.getMessage());
                    break;
                }
            }
            vectors.add(v);
            if(i == 0 || i == 2)coefY = -1;
            if(i == 1){coefX = -1;coefY=1;}
        }
        return vectors;
    }

    @Override
    List<List<Coord>> listEatingMove() {
        return listMove();
    }
}
