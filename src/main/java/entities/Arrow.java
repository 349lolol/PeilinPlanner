/**
 * [Arrow.java]
 * Class representing any arrow in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package entities;

import java.util.ArrayList;

public class Arrow {
    private Diagram origin;
    private Diagram destination;
    private String arrowType;
    private ArrayList<Integer> xPoints;
    private ArrayList<Integer> yPoints;

    /**
     * arrow constructor
     * @param origin name of hte origin diagram
     * @param destination name of the destination diagram
     * @param arrowType arrow identifier
     */
    Arrow(Diagram origin, Diagram destination, String arrowType) {
        this.origin = origin;
        this.destination = destination;
        this.arrowType = arrowType;
        xPoints = new ArrayList<Integer>();
        yPoints = new ArrayList<Integer>();
    }

    /**
     * arrow constructor
     * @param origin name of hte origin diagram
     * @param destination name of the destination diagram
     * @param arrowType arrow identifier
     * @param xPoints list of x points
     * @param yPoints list of y points
     */
    Arrow(Diagram origin, Diagram destination, String arrowType, ArrayList<Integer> xPoints, ArrayList<Integer> yPoints) {
        this.origin = origin;
        this.destination = destination;
        this.arrowType = arrowType;
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }

    /**
     * arrow
     * empty constructor
     */
    Arrow() {
        this.origin = null;
        this.destination = null;
        this.arrowType = null;
        xPoints = new ArrayList<Integer>();
        yPoints = new ArrayList<Integer>();
    }

    public String toJson() {
        String data = "\"origin\": " + origin + ", ";
        data = data + "\"destination\": " + destination + ", ";
        data = data + "\"arrowType\": " + arrowType + ", ";
        data = data + "\"xPoints\": " + arrayListToJson(xPoints) + ", ";
        data = data + "\"yPoints\": " + arrayListToJson(yPoints);
        return data;
    }

    private String arrayListToJson(ArrayList<Integer> points) {
        String data = "[";
        for(int i = 0; i < points.size(); i++) {
            data = data + points + ", ";
        }
        data = data.substring(0, data.length()-2);
        data = data + "]";
        return data;
    }

    /**
     * getOrigin
     * gets the name of the origin
     * @return the name of the origin diagram
     */
    public Diagram getOrigin() {
        return this.origin;
    }

    /**
     * addXpoints
     * sets a new arraylist of xpoints as coordinates
     * @param xPoints list of coordinates
     */
    public void addXPoints(ArrayList<Integer> xPoints) {
        this.xPoints = xPoints;
    }

    /**
     * addYpoints
     * sets a new arraylist of ypoints as coordinates
     * @param yPoints list of coordinates
     */
    public void addYPoints(ArrayList<Integer> yPoints) {
        this.yPoints = yPoints;
    }

    /**
     * setOrigin
     * sets a new origin for the diagram
     * @param diagram the new origin name
     */
    public void setOrigin(Diagram diagram) {
        this.origin = diagram;
    }

    /**
     * getDestination
     * gets the name of the destination
     * @return destination name
     */
    public Diagram getDestination() {
        return this.destination;
    }

    /**
     * setDestination
     * sets a new destination for the diagram
     * @param diagram the new destination name
     */
    public void setDestination(Diagram diagram) {
        this.origin = diagram;
    }

    /**
     * getArrowType
     * gets the arrow type identifier
     * @return arrow type identifier
     */
    public String getArrowType() {
        return this.arrowType;
    }

    /**
     * setARrowType
     * sets a new arrow type
     * @param arrowType the new arrow identifier
     */
    public void setArrowType(String arrowType) {
        this.arrowType = arrowType;
    }

    /**
     * getXPoint
     * returns the list of x coordiantes
     * @return list of x coordinates
     */
    public ArrayList<Integer> getXPoints() {
        return this.xPoints;
    }

    /**
     * getYPoint 
     * returns the list of y coordinates
     * @return list of y coordinates
     */
    public ArrayList<Integer> getYPoints() {
        return this.yPoints;
    }

    /**
     * addPoint
     * adds a new point to the arrow
     * @param x x coordinate
     * @param y y coordinate
     */
    public void addPoint(int x, int y) {
        this.xPoints.add(x);
        this.yPoints.add(y);
    }

    /**
     * removePoints
     * removes n points from the arrow
     * @param n number of points being removed
     */
    public void removePoints(int n) {
        while(n>0) {
            n--;
            this.xPoints.remove(0);
            this.yPoints.remove(0);
        }
    }

    /**
     * removePoint
     * removes a point if it exists
     * @param x x coordiante of point
     * @param y y coordinate of point
     * @return if operation was successful or not 
     */
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