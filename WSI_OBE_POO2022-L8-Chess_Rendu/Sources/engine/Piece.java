package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.List;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Il s'agit d'une classe abstraite qui représente le matériel d'un jeu d'échecs.
 *                 Les classes concrètent qui héritent de cette classe abstraites sont les 6 pièces
 *                 d'un jeu d'échecs traditionnel : King, Queen, Rook, Bishop, Knight, Pawn.
 *  -----------------------------------------------------------------------------------
 **/

public abstract class Piece {

    // region parameter
    boolean hasMoved;
    PlayerColor color;
    PieceType type;
    Coord coord;
    // endregion

    // region ctor
    /**
     * Nom          : Piece
     * Description  : Permet de contruire une pièce concrète en spécifiant la coordonné, la couleur et son type.
     * @param coord : Coordonné ou la pièce va être crée sur un échiquier.
     * @param color : Couleur de la pièce (blanche ou noire)
     * @param type  : Indique le type de pièce à créer (Pawn, king, queen etc.)
     * @return      : L'objet Piece construit par le constructeur
     **/
    public Piece(Coord coord, PlayerColor color, PieceType type){
        this.coord = coord;
        this.color = color;
        this.type = type;
        hasMoved = false;
    }
    // endregion

    // region Method
    /**
     * Nom         : acceptedMove
     * Description : Indique si la pièce sélectionnée peut effectuer le mouvement que
     *               l'utilisateur a joué sur l'échiquier.
     * @param toX  : Coordonné en X ou l'on souhaite se déplacer sur l'échiquier
     * @param toY  : Coordonné en Y ou l'on souhaite se déplacer sur l'échiquier
     * @return     : Booléen indiquant si oui ou non le mouvement est valide.
     **/
    abstract boolean acceptedMove(int toX,int toY);

    /**
     * Nom         : move
     * Description : Permet de déplacer la pièce sélectionnée sur l'échiquier.
     * @param toX  : Coordonné en X ou l'on souhaite se déplacer sur l'échiquier
     * @param toY  : Coordonné en Y ou l'on souhaite se déplacer sur l'échiquier
     * @return     : / void
     **/
    public void move(int toX,int toY){
        coord.setCoord(toX, toY);
    }

    /**
     * Nom         : listMove
     * Description : Permet de générer tous les mouvements qu'une pièce peut effectuer.
     * @return     : List avec tous les vecteurs de points.
     **/
    abstract List<List<Coord>> listMove();

    /**
     * Nom         : listEatingMove
     * Description : Permet de générer tous les mouvements qu'une pièce peut effectuer
     *               pour manger une pièce.
     * @return     : List avec tous les vecteurs de points.
     **/
    abstract List<List<Coord>> listEatingMove();


    /**
     * Nom         : getType
     * Description : Permet d'obtenir le type d'une pièce.
     * @return     : Le type de la pièce.
     **/
    public PieceType getType(){
        return type;
    }

    /**
     * Nom         : getColor
     * Description : Permet d'obtenir la couleur d'une pièce.
     * @return     : La couleur de la pièce.
     **/
    public PlayerColor getColor() {
        return color;
    }
    // endregion
}
