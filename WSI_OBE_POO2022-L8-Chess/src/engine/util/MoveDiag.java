package engine.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MoveDiag extends Movement {

    // region Contructor
    public MoveDiag(int range) {
        super(range);
    }
    // endregion

    // region Parameter
    private final int NBR_VECTORS = 4;
    // endregion

    @Override
    public List<List<Coord>> listMove(Coord coord) {
        List<List<Coord>> vectors = new LinkedList<>();
        List<Coord> v = new LinkedList<>();
        int coefX = 1,coefY=1;
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

            if(i == 0 || i == 2)coefY = -1;
            if(i == 1){coefX = -1;coefY=1;}
        }
        return vectors;
    }

    @Override
    public List<List<Coord>> listEatingMove(Coord coord) {
        return listMove(coord);
    }
}
