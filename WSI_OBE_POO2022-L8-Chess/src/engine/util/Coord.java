package engine.util;

public class Coord {
    private int x;
    private int y;
    private final int min = 0, max = 7;

    public Coord(int x,int y) throws RuntimeException{
        if(x < min || x > max || y < min || y > max){
            throw new RuntimeException("La coordonnée [" + x + ","+ y +"] n'est pas inclus dans l'echiquier");
        }
        this.x = x;
        this.y = y;
    }

    public void setCoord(int x,int y) throws RuntimeException{
        if(x < min || x > max || y < min || y > max){
            throw new RuntimeException("Coordonnées [" + x + ","+ y +"] pas inclus dans l'echiquier");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public String toString(){
        return "("+x+","+y+")";
    }
}
