package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;
import engine.util.MoveDiag;
import engine.util.MoveLin;
import java.util.LinkedList;
import java.util.List;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Cette classe représente la pièce "Queen" dans un jeu d'échecs.
 *  -----------------------------------------------------------------------------------
 **/

public class Queen extends Piece {

    // region Parameters
    MoveDiag md;
    MoveLin ml;
    // endregion

    // region Constructor
    /**
     * Nom          : Queen
     * Description  : Permet de construire une pièce de type Queen
     * @param coord : Coordonnée à laquelle il faut créer la pièce
     * @param color : Couleur de la pièce.
     * @return      : L'objet Queen construit par le constructeur
     **/
    public Queen(Coord coord, PlayerColor color) {
        super(coord, color, PieceType.QUEEN);
        md = new MoveDiag(8);
        ml = new MoveLin(8);
    }
    // endregion

    // region Methods
    @Override
    boolean acceptedMove(int toX, int toY) {
        int deltaX = Math.abs(toX - coord.getX());
        int deltaY = Math.abs(toY - coord.getY());

        return (deltaX != 0 && deltaY == 0 || deltaX == 0 && deltaY != 0) || (deltaX == deltaY);
    }

    @Override
    List<List<Coord>> listMove() {
        List<List<Coord>> list = md.listMove(coord);
        list.addAll(ml.listMove(coord));
        return list;
    }


    @Override
    List<List<Coord>> listEatingMove() {
        return listMove();
    }
    // endregion
}
