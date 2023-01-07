package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;
import engine.util.MoveKnight;
import java.util.List;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Cette classe représente la pièce "Knight" dans un jeu d'échecs.
 *  -----------------------------------------------------------------------------------
 **/

public class Knight extends Piece{

    // region Parameter
    MoveKnight mk;
    // endregion

    // region Constructor
    /**
     * Nom          : Knight
     * Description  : Permet de construire une pièce de type Knight
     * @param coord : Coordonnée à laquelle il faut créer la pièce
     * @param color : Couleur de la pièce.
     * @return      : L'objet Knight construit par le constructeur
     **/
    public Knight(Coord coord, PlayerColor color) {
        super(coord, color, PieceType.KNIGHT);
        mk = new MoveKnight(2);
    }
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
