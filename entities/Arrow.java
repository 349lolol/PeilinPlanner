package entities;
import java.util.ArrayList;

/**
 * [Arrow.java]
 * Class representing any arrow in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

public class Arrow{
    private final Object origin;
    private final Object destination;
    private final String arrowType;
    private ArrayList<Integer> xPoints;
    private ArrayList<Integer> yPoints;

    Arrow(Object origin, Object destination, String arrowType){
        this.origin = origin;
        this.destination = destination;
        this.arrowType = arrowType;
        xPoints = new ArrayList<Integer>();
        yPoints = new ArrayList<Integer>();
    }

    public Object getOrigin(){
        return this.origin;
    }

    public Object getDestination(){
        return this.destination;
    }

    public String getArrowType(){
        return this.arrowType;
    }

    public ArrayList<Integer> getXPoints(){
        return this.xPoints;
    }

    public ArrayList<Integer> getYPoints(){
        return this.yPoints;
    }

    public void addPoint(int x, int y){
        this.xPoints.add(x);
        this.yPoints.add(y);
    }

    public void removePoints(int n){
        while(n>0){
            n--;
            this.xPoints.remove(0);
            this.yPoints.remove(0);
        }
    }

    public boolean removePoint(int x, int y){
        for(int i = 0; i < xPoints.size(); i++){
            if((xPoints.get(i) == x) && (yPoints.get(i) == y)){
                xPoints.remove(i);
                yPoints.remove(i);
                return true;
            }
        }
        return false;
    }
}