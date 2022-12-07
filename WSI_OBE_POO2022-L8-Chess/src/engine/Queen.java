package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.LinkedList;
import java.util.List;

// @author : slimani
public class Queen extends Piece {
    // region Constructor
    public Queen(int x, int y, PlayerColor color, PieceType type) {
        super(x, y, color, type);
    }
    // endregion

    // region Parameters
    private final int NUMBER_OF_VECTOR = 8;
    // endregion

    // region Methods
    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = Math.abs(toX - this.x);
        int deltaY = Math.abs(toY - this.y);

        return (deltaX != 0 && deltaY == 0 || deltaX == 0 && deltaY != 0) || (deltaX == deltaY);
    }

    @Override
    List<List<Coord>> listMove() {
        List<List<Coord>> vectors = new LinkedList<>();
        for (int i = 0; i < NUMBER_OF_VECTOR; ++i) {
            LinkedList<Coord> tempVector = new LinkedList<>();
            vectors.add(new LinkedList<Coord>());

            for (int tempX = x; tempX < 7; ++tempX) {
                for (int tempY = y; tempY < 7; ++tempY) {

                    // Forward vector (Tower move)
                    if (tempX > x && tempY == y) {
                        tempVector.add(new Coord(tempX, tempY));
                    }

                    // Backward vector (Tower move)
                    if (tempX < x && tempY == y) {
                        tempVector.add(new Coord(tempX, tempY));
                    }

                    // Right vector (Tower move)
                    if (tempX == x && tempY > y) {
                        tempVector.add(new Coord(tempX, tempY));
                    }

                    // Left vector (Tower move)
                    if (tempX == x && tempY < y)
                        tempVector.add(new Coord(tempX, tempY));

                    // Forward left vector (Bishop move)
                    if (tempX < x && tempY > y) {
                        tempVector.add(new Coord(tempX, tempY));
                    }

                    // Forward right vector (Bishop move)
                    if (tempX > x && tempY > y) {
                        tempVector.add(new Coord(tempX, tempY));
                    }

                    // Backward left vector (Bishop move)
                    if (tempX < x && tempY < y) {
                        tempVector.add(new Coord(tempX, tempY));
                    }

                    // Backward right vector (Bishop move)
                    if (tempX > x && tempY < y) {
                        tempVector.add(new Coord(tempX, tempY));
                    }
                }
            }
            vectors.add(tempVector);
        }
        return vectors;
    }


    @Override
    List<List<Coord>> listEatingMove() {
        return listMove();
    }
    // endregion
}