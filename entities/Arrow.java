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
    private final Diagram origin;
    private final Diagram destination;
    private final String arrowType;
    private ArrayList<Integer> xPoints;
    private ArrayList<Integer> yPoints;

    public String toString(){
        String data = "<OBJECTYPE> ARROW </OBJECTYPE>\n";
        data = data + "<Origin> " + this.origin.getName() + " </Origin>\n";  //only getname, not actual one for tostring
        data = data + "<Destination> " + this.destination.getName() + " </Destination>\n";
        data = data + "<ArrowType> " + this.arrowType + " </ArrowType>\n";
        String xList = "";
        String yList = "";
        for(int i = 0; i < xPoints.size(); i++){
            xList = xList + Integer.toString(xPoints.get(i)) + ", ";
            yList = yList + Integer.toString(yPoints.get(i)) + ", ";
        }
        xList.substring(0, xList.length() - 2);
        yList.substring(0, yList.length() - 2);
        data = data + "<XPoints> " + xList + " </XPoints>\n";
        data = data + "<YPoints> " + yList + " </YPoints>\n";
        return data;
    }
    Arrow(Diagram origin, Diagram destination, String arrowType){
        this.origin = origin;
        this.destination = destination;
        this.arrowType = arrowType;
        xPoints = new ArrayList<Integer>();
        yPoints = new ArrayList<Integer>();
    }

    public Diagram getOrigin(){
        return this.origin;
    }

    public Diagram getDestination(){
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