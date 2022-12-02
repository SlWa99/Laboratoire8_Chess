package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.List;
// @author : slimani
public class Rook extends Piece{
    public Rook(int x, int y, PlayerColor color, PieceType type) {
        super(x, y, color, type);
    }

    @Override
    boolean acceptedMove(int toX, int toY) {
        return false;
    }

    @Override
    List<List<Coord>> listMove() {
        return null;
    }

    @Override
    List<List<Coord>> listEatingMove() {
        return null;
    }
}
