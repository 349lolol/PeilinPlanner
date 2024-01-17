package entities;
import java.util.ArrayList;

/**
 * [Arrow.java]
 * Class representing any arrow in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

public class Arrow {
    private Diagram origin;
    private Diagram destination;
    private String arrowType;
    private ArrayList<Integer> xPoints;
    private ArrayList<Integer> yPoints;

    
    Arrow(Diagram origin, Diagram destination, String arrowType) {
        this.origin = origin;
        this.destination = destination;
        this.arrowType = arrowType;
        xPoints = new ArrayList<Integer>();
        yPoints = new ArrayList<Integer>();
    }

    Arrow() {
        this.origin = null;
        this.destination = null;
        this.arrowType = null;
        xPoints = new ArrayList<Integer>();
        yPoints = new ArrayList<Integer>();
    }

    public Diagram getOrigin() {
        return this.origin;
    }

    public void addXPoints(ArrayList<Integer> xPoints) {
        this.xPoints = xPoints;
    }

    public void addYPoints(ArrayList<Integer> yPoints) {
        this.yPoints = yPoints;
    }

    public void setOrigin(Diagram diagram) {
        this.origin = diagram;
    }

    public Diagram getDestination() {
        return this.destination;
    }

    public void setDestination(Diagram diagram) {
        this.origin = diagram;
    }

    public String getArrowType() {
        return this.arrowType;
    }

    public void setArrowType(String arrowType) {
        this.arrowType = arrowType;
    }

    public ArrayList<Integer> getXPoints() {
        return this.xPoints;
    }

    public ArrayList<Integer> getYPoints() {
        return this.yPoints;
    }

    public void addPoint(int x, int y) {
        this.xPoints.add(x);
        this.yPoints.add(y);
    }

    public void removePoints(int n) {
        while(n>0) {
            n--;
            this.xPoints.remove(0);
            this.yPoints.remove(0);
        }
    }

    public boolean removePoint(int x, int y) {
        for(int i = 0; i < xPoints.size(); i++) {
            if((xPoints.get(i) == x) && (yPoints.get(i) == y)) {
                xPoints.remove(i);
                yPoints.remove(i);
                return true;
            }
        }
        return false;
    }
}