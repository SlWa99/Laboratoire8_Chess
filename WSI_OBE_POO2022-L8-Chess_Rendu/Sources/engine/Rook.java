package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;
import engine.util.MoveLin;
import java.util.LinkedList;
import java.util.List;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Cette classe représente la pièce "Rook" dans un jeu d'échecs.
 *  -----------------------------------------------------------------------------------
 **/

public class Rook extends Piece {

    // region Parameters
    MoveLin ml;
    // endregion

    // region Constructor
    /**
     * Nom          : Rook
     * Description  : Permet de construire une pièce de type Rook
     * @param coord : Coordonnée à laquelle il faut créer la pièce
     * @param color : Couleur de la pièce.
     * @return      : L'objet Rook construit par le constructeur
     **/
    public Rook(Coord coord, PlayerColor color) {
        super(coord, color, PieceType.ROOK);
        ml = new MoveLin(8);
    }
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
