package engine.util;

import java.util.LinkedList;
import java.util.List;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Cette classe permet de générer les mouvements de la pièce cavalier dans
 *                 un jeu d'échecs.
 *  -----------------------------------------------------------------------------------
 **/

public class MoveKnight extends Movement {

    // region Parameter
    private final int NBR_VECTORS = 8;
    // endregion

    // region Contructor
    /**
     * Nom          : MoveKnight
     * Description  : Permet de contruire un MoveKnight en spécifiant la range de déplacement autorisée.
     * @param range : Range de déplacement autorisée.
     * @return      : L'objet MoveKnight construit par le constructeur
     **/
    public MoveKnight(int range) {
        super(range);
    }
    // endregion

    // region Methods
    @Override
    public List<List<Coord>> listMove(Coord coord) {
        List<List<Coord>> vectors = new LinkedList<>();

        int coefX = 1, coefY = 2;
        for (int i = 0; i < NBR_VECTORS; ++i) {
            List<Coord> v = new LinkedList<>();

            try {
                v.add(new Coord(coord.getX() + coefX, coord.getY() + coefY));
                vectors.add(v);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }


            if (i == 0) {
                coefX = 2;
                coefY = 1;
            }
            if (i == 1) {
                coefX = 2;
                coefY = -1;
            }
            if (i == 2) {
                coefX = 1;
                coefY = -2;
            }
            if (i == 3) {
                coefX = -1;
                coefY = -2;
            }
            if (i == 4) {
                coefX = -2;
                coefY = -1;
            }
            if (i == 5) {
                coefX = -2;
                coefY = 1;
            }
            if (i == 6) {
                coefX = -1;
                coefY = 2;
            }
        }
        return vectors;
    }

    @Override
    public List<List<Coord>> listEatingMove(Coord coord) {
        return listMove(coord);
    }
    // endregion
}
