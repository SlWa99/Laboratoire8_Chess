package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;
import engine.util.MoveDiag;
import engine.util.MoveLin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Cette classe représente la pièce "King" dans un jeu d'échecs.
 *  -----------------------------------------------------------------------------------
 **/
public class King extends Piece{

    // coordonnées du petit rook et grand rook
    private Coord lCastle, bCastle;
    public King(Coord coord, PlayerColor color) {
        super(coord, color, PieceType.KING);
        if(color == PlayerColor.WHITE){
            lCastle = new Coord(6,0);
            bCastle = new Coord(2,0);
        } else{
            lCastle = new Coord(6,7);
            bCastle = new Coord(2,7);
        }
        md = new MoveDiag(1);
        ml = new MoveLin(1);
    }

    // region parametre
    MoveDiag md;
    MoveLin ml;
    //endregion

    /***
     * @author Oscar Baume
     * @brief La fonction retourne si le mouvement peut être fait.
     * @param toX : coordonnée X de la destination du mouvement
     * @param toY : coordonnée Y de la destination du mouvement
     * @return boolean qui indique si le mouvement peut être fait
     */
    @Override
    boolean acceptedMove(int toX, int toY) {
        // on fait les delta pour x et y
        int deltaX = coord.getX() - toX;
        int deltaY = coord.getY() - toY;
        // si on se déplace pas le mouvement n'est pas accepté
        if(deltaY == deltaX && deltaY == 0) return false;

        // le roi pouvant se deplacer d'une case dans les 8 directions
        // si le delta^2 est égale à 0 ou à 1 alors on peut se deplacer là (1ère ligne du return)
        // si le roi n'a pas bougé et qu'il veut rooker alors il peut se deplacer là  (2e et 3e ligne du return)
        return (deltaX * deltaX <= 1 && deltaY * deltaY <= 1) ||
               (!hasMoved && ((color == PlayerColor.WHITE) && toY == 0 && (toX == 2 || toX == 6) ||
               ((color == PlayerColor.BLACK) && toY == 7 && (toX == 2 || toX == 6))));
    }

    /***
     * @author Oscar Baume
     * @brief fonction qui retourne les mouvements possible de la piece
     * @return une liste de liste de coordonée qui correspont au "vecteur" de deplacement de la pièce
     */
    @Override
    List<List<Coord>> listMove() {
        // le roi peut se déplacer dans les 8 directions d'une case
        // on construit alors une liste composées de 8 listes avec chacune d'elle la coordonnées du mouvement
        List<List<Coord>> vectors = new LinkedList<>();
        List<Coord> v = new LinkedList<>();
        for(int i = -1; i < 2; ++i){
            for(int j = -1; j < 2; ++j){
                if(!(i==0&&j==0)){
                    try{
                        v = new ArrayList<Coord>();
                        v.add(new Coord(coord.getX() + i, coord.getY() +j));
                        vectors.add(v);
                    }catch (RuntimeException e){System.out.println(e.getMessage());}
                }
            }
        }
        // si le roi n'a pas bougé alors il peut rooker
        if(!hasMoved){
            v = new ArrayList<>();
            v.add(lCastle);
            vectors.add(v);
            v = new ArrayList<>();
            v.add(bCastle);
            vectors.add(v);
        }
        return vectors;
    }

    /***
     * @author Oscar Baume
     * @brief fonction qui retourne les coordonnées ou le roi peut manger une pièce adverse.
     * @return les coordonées au le roi peut manger une pièce adverse.
     */
    @Override
    List<List<Coord>> listEatingMove() {
        // le roi peut se déplacer dans les 8 directions d'une case
        // on construit alors une liste composées de 8 listes avec chacune d'elle la coordonnées du mouvement
        List<List<Coord>> vectors = new ArrayList<>();
        List<Coord> v = new ArrayList<>();
        for(int i = -1; i < 2; ++i){
            for(int j = -1; j < 2; ++j){
                if(!(i==0&&j==0)){
                    try{
                        v = new ArrayList<Coord>();
                        v.add(new Coord(coord.getX() + i, coord.getY() +j));
                        vectors.add(v);
                    }catch (RuntimeException e){System.out.println(e.getMessage());}
                }
            }
        }
        return vectors;
    }
}
