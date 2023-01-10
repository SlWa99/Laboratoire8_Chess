package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;
import engine.util.MoveDiag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Cette classe représente la pièce "Bishop" dans un jeu d'échecs.
 *  -----------------------------------------------------------------------------------
 **/
public class Bishop extends Piece{

    // region Contructor
    public Bishop(Coord coord, PlayerColor color) {
        super(coord, color, PieceType.BISHOP);
        md = new MoveDiag(8);
    }
    // endregion

    // region Parametre
    private MoveDiag md;
    // endregion


    // region Methods
    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = coord.getX() - toX;
        int deltaY = coord.getY() - toY;
        if(deltaY == 0 || deltaX == 0) return false;

        return Math.abs(deltaX) == Math.abs(deltaY);
    }

    @Override
    List<List<Coord>> listMove() {
    return md.listMove(coord);
    }

    @Override
    List<List<Coord>> listEatingMove() {
        return md.listEatingMove(coord);
    }
    // endregion
}
