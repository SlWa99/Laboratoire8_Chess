package engine.util;
import java.util.List;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Il s'agit d'une classe abstraite qui représente des mouvements.
 *                 Un mouvement est caractérisé par des vecteurs de coordonnées.
 *                 Les vecteurs partent d'un point central. Le paramètre range correspond
 *                 au nombre de coordonnées parcouru depuis le point central dans une direction.
 *  -----------------------------------------------------------------------------------
 **/

public abstract class Movement {

    // region Parmètre
    int range;
    // endregion

    // region Constructor
    /**
     * Nom          : Movement
     * Description  : Permet de contruire un Movement en spécifiant la range de déplacement autorisée.
     * @param range : Range de déplacement autorisée.
     * @return      : L'objet Movement construit par le constructeur
     **/
    public Movement(int range) {
        this.range = range;
    }
    // endregion

    // region Methods
    /**
     * Nom          : listMove
     * Description  : Gènère des listes de mouvements à partir du point coord
     * @param coord : Point d'origine à partir d'ou les vecteurs de mouvements sont
     *                générés.
     * @return      : Liste contenant des vecteurs de mouvements
     **/
    public abstract List<List<Coord>> listMove(Coord coord);

    /**
     * Nom          : listEatingMove
     * Description  : Gènère des listes de mouvements à partir du point coord. A la différence
     *                de "listMove", on gènère les mouvements ou l'on peut manger une pièce.
     * @param coord : Point d'origine à partir d'ou les vecteurs de mouvements sont
     *                générés.
     * @return      : Liste contenant des vecteurs de mouvements
     **/
    public abstract List<List<Coord>> listEatingMove(Coord coord);
    // endregion
}
