package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.List;

public abstract class Piece {
    boolean hasMoved;
    PlayerColor color;
    PieceType type;
    Coord coord;

    /**
     * Constructeur d'une pièce
     * @param coord coordonnée initial de la pièce
     * @param color couleur de la pièce
     * @param type type de pièce
     */
    public Piece(Coord coord, PlayerColor color, PieceType type){
        this.coord = coord;
        this.color = color;
        this.type = type;
        hasMoved = false;
    }

    /**
     * Méthode qui retourne si la pièce peut se déplacer vers (toX,toY)
     * @param toX position x de déstination
     * @param toY position y de déstination
     * @return si le déplacer est accepté
     */
    abstract boolean acceptedMove(int toX,int toY);

    /**
     * Méthode qui déplace la pièce vers (toX,toY)
     * @param toX position x de déstination
     * @param toY position y de déstination
     */
    public void move(int toX,int toY){
        coord.setCoord(toX, toY);
    }

    /**
     * Méthode qui retourne tout les mouvements de la pièce
     * @return liste des mouvements
     */
    abstract List<List<Coord>> listMove();

    /**
     * Méthode qui retourne la liste de mouvements dans lesquels la pièce peut manger
     * @return liste des mouvements qui mange
     */
    abstract List<List<Coord>> listEatingMove();

    /**
     * Méthode qui retourne le type de la pièce
     * @return type de la pièce
     */
    public PieceType getType(){
        return type;
    }

    /**
     * Méthode qui retourne la couleur de la pièce
     * @return couleur de la pièce 
     */
    public PlayerColor getColor() {
        return color;
    }
}
