package engine.util;

import java.util.List;

public abstract class Movement {

    // region Constructor
    public Movement(int range) {
        this.range = range;
    }
    // endregion

    // region Parmètre
     int range;
    // endregion


    // region Methods
    public abstract List<List<Coord>> listMove(Coord coord);

    public abstract List<List<Coord>> listEatingMove(Coord coord);
    // endregion
}
