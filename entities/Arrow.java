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
}