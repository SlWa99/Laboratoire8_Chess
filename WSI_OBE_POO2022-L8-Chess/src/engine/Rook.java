package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;
import engine.util.MoveLin;

import java.util.LinkedList;
import java.util.List;

// @author : slimani
public class Rook extends Piece {
    // region Constructor
    public Rook(Coord coord, PlayerColor color) {
        super(coord, color, PieceType.ROOK);
        ml = new MoveLin(8);
    }
    // endregion

    // region Parameters
    MoveLin ml;
    // endregion

    // region Methods
    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = Math.abs(toX - coord.getX());
        int deltaY = Math.abs(toY - coord.getY());

        return deltaX != 0 && deltaY == 0 || deltaX == 0 && deltaY != 0;
    }

    @Override
    List<List<Coord>> listMove() {
        return ml.listMove(coord);
    }

    @Override
    List<List<Coord>> listEatingMove() {
        return listMove();
    }

    // endregion
}
