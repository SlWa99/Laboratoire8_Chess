package engine.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Cette classe permet de générer quatre vecteurs de points à partir d'un
 *                 point central. Les vecteurs sont disposés en croix autour du point central
 *                 (croix droite +).
 *  -----------------------------------------------------------------------------------
 **/

public class MoveLin extends Movement{

    // region Parameter
    private final int NBR_VECTORS = 4;
    // endregion

    // region Constructor
    /**
     * Nom          : MoveLin
     * Description  : Permet de contruire un MoveLin en spécifiant la range de déplacement autorisée.
     * @param range : Range de déplacement autorisée.
     * @return      : L'objet MoveLin construit par le constructeur
     **/
    public MoveLin(int range) {
        super(range);
    }
    // endregion

    // region Methods
    @Override
    public List<List<Coord>> listMove(Coord coord) {
        List<List<Coord>> vectors = new LinkedList<>();
        List<Coord> v = new LinkedList<>();
        int coefX = 1,coefY=0;
        for(int i = 0; i < NBR_VECTORS; ++i){
            v = new ArrayList<>();
            for(int j = 1; j < this.range + 1; ++j){
                try{
                    v.add(new Coord(coord.getX() + j*coefX,coord.getY() + j*coefY));
                }catch (RuntimeException e){
                    System.out.println(e.getMessage());
                    break;
                }
            }
            vectors.add(v);
            if(i == 0 ) {
                coefX = 0;
                coefY = 1;
            }
            if(i == 1 ) {
                coefX = -1;
                coefY = 0;
            }
            if(i == 2 ) {
                coefX = 0;
                coefY = -1;
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
