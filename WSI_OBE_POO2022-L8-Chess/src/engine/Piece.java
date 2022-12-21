package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.List;

public abstract class Piece {
    boolean hasMoved;
    PlayerColor color;
    PieceType type;
    Coord coord;

    public Piece(Coord coord, PlayerColor color, PieceType type){
        this.coord = coord;
        this.color = color;
        this.type = type;
        hasMoved = false;
    }

    abstract boolean acceptedMove(int toX,int toY);

    public void move(int toX,int toY){
        coord.setCoord(toX, toY);
    }

    abstract List<List<Coord>> listMove();

    abstract List<List<Coord>> listEatingMove();

    public PieceType getType(){
        return type;
    }

    public PlayerColor getColor() {
        return color;
    }
}
