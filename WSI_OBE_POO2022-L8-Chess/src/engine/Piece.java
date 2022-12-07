package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.List;

public abstract class Piece {
    int x;
    int y;
    boolean hasMoved;
    PlayerColor color;
    PieceType type;

    public Piece(int x, int y, PlayerColor color, PieceType type){
        this.x = x;
        this.y = y;
        this.color = color;
        this.type = type;
        hasMoved = false;
    }

    abstract boolean acceptedMove(int toX,int toY);

    public void move(int toX,int toY){
        x = toX;
        y = toY;
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
