package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.List;
// @author : slimani
public class Knight extends Piece{

    // region Constructor
    public Knight(int x, int y, PlayerColor color) {
        super(x, y, color, PieceType.KNIGHT);
    }
    // endregion

    // region Methods
    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = Math.abs(toX - this.x);
        int deltaY = Math.abs(toY - this.y);

        return deltaX == 2 && deltaY == 1  || deltaX == 1 && deltaY == 2;
    }

    @Override
    List<List<Coord>> listMove() {
        return null;
    }

    @Override
    List<List<Coord>> listEatingMove() {
        return listMove();
    }
    // endregion
}
