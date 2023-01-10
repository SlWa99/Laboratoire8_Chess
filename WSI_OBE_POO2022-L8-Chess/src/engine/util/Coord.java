package engine.util;

/**
 *  -----------------------------------------------------------------------------------
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Il s'agit d'une classe qui modélise un position sur l'échiquier
 *  -----------------------------------------------------------------------------------
 **/
public class Coord {
    private int x;
    private int y;
    private final int min = 0, max = 7;

    /**
     * Nom          : Coord
     * Description  : Constructeur de coordonnées
     * @param x     : Position x de la coord
     * @param y     : Position y de la coord
     * @throws RuntimeException
     */
    public Coord(int x,int y) throws RuntimeException{
        if(x < min || x > max || y < min || y > max){
            throw new RuntimeException("La coordonnée [" + x + ","+ y +"] n'est pas inclus dans l'echiquier");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Nom          : setCoord
     * Description  : Setter de coordonnées
     * @param x     : Nouvelle position x
     * @param y     : Nouvelle position y
     * @throws RuntimeException
     */
    public void setCoord(int x,int y) throws RuntimeException{
        if(x < min || x > max || y < min || y > max){
            throw new RuntimeException("Coordonnées [" + x + ","+ y +"] pas inclus dans l'echiquier");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Nom          : getX
     * Description  : getter de la positon x
     * @return      : valeur de x
     */
    public int getX() {
        return x;
    }
    /**
     * Nom          : getY
     * Description  : getter de la positon y
     * @return      : valeur de y
     */
    public int getY() {
        return y;
    }

    /**
     * Nom          : isEqual
     * Description  : Méthode qui retourne si une coordonnées est égale à la coord actuelle
     * @param other : coordonnées avec laquelle on se compare
     * @return      : si c'est égale
     */
    public boolean isEqual(Coord other){
        return x == other.x && y == other.y;
    }

    /**
     * Nom          : toString
     * Description  : Méthode qui retourne la coordonnées au format string
     * @return      : String de la coordonneés
     */
    public String toString(){
        return "("+x+","+y+")";
    }
}
