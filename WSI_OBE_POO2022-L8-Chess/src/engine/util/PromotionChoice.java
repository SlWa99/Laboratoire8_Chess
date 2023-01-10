package engine.util;
import chess.ChessView;
import engine.Piece;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Classe permettant de créer une pièce lorsqu'un pion doit être promu.
 *  -----------------------------------------------------------------------------------
 **/

abstract public class PromotionChoice implements ChessView.UserChoice {
    // region Methods
    /**
     * Nom          : create
     * Description  : Permet de créer la pièce choisi par l'utilisateur lors d'une promotion.
     * @return      : La pièce choisie par l'utilisateur.
     **/
    public abstract Piece create();
    // endregion
}
