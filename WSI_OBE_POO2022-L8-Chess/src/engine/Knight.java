package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;
import engine.util.MoveKnight;

import java.util.List;
// @author : slimani
public class Knight extends Piece{

    // region Constructor
    public Knight(Coord coord, PlayerColor color) {
        super(coord, color, PieceType.KNIGHT);
        mk = new MoveKnight(2);
    }
    // endregion

    // region Parameter
    MoveKnight mk;
    // endregion

    // region Methods
    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = Math.abs(toX - coord.getX());
        int deltaY = Math.abs(toY - coord.getY());

        return deltaX == 2 && deltaY == 1  || deltaX == 1 && deltaY == 2;
    }

    @Override
    List<List<Coord>> listMove() {
        return mk.listMove(coord);
    }

    @Override
    List<List<Coord>> listEatingMove() {
        return mk.listEatingMove(coord);
    }
    // endregion
}
