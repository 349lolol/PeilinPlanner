/**
 * [ExceptionDiagram.java]
 * Class representing an exception diagram in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package entities;

public class ExceptionDiagram extends Diagram{

    /**
     * toString
     * constructs XML structure for exception diagram
     */
    @Override
    public String toString(){
        String data = "<OBJECTYPE-EXCEPTIONDIAGRAM>";
        data = data +  "<name> " + super.getName() + "</name>\n";
        data = data +  "<xPosition> " + super.getXPosition() + " </xPosition>\n";
        data = data +  "<yPosition> " + super.getYPosition() + " </yPosition>\n";
        data = data +  "<xSize> " + super.getYSize() + " </xSize>\n";
        data = data +  "<ySize> " + super.getYSize() + " </ySize>\n";
        return data;
    }

    /**
     * ExceptionDiagram
     * Constructor for the ExceptionDiagram class
     * @param name the name of the exception
     * @param xPosition the x position of the top left of the class
     * @param yPosition the y position of the top left of the class
     * @param xSize the x size of the class
     * @param ySize the y size of the class
     */
    public ExceptionDiagram(String name, int xPosition, int yPosition, int xSize, int ySize) {
        super(name, xPosition, yPosition, xSize, ySize);
    }

       /**
     * getName
     * returns the name of the exception
     * @return the name of the exception
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * setName
     * updates the name of the exception
     * @param name the new name of the exception
     */
    @Override
    public void setName(String name) {
        super.setName(name);
    }

        /**
     * getXPosition
     * returns the x position of the top left of the class
     * @return the x position of the top left of the class
     */
    @Override
    public int getXPosition() {
        return super.getXPosition();
    }

    /**
     * setXPosition
     * updates the x position of the top left of the class
     * @param xPosition the new x position of the top left of the class
     */
    @Override
    public void setXPosition(int xPosition) {
        super.setXPosition(xPosition);
    }

    /**
     * getYPosition
     * returns the y position of the top left of the class
     * @return the y position of the top left of the class
     */
    @Override
    public int getYPosition() {
        return super.getYPosition();
    }

    /**
     * setYPosition
     * updates the y position of the top left of the class
     * @param xPosition the new y position of the top left of the class
     */
    @Override
    public void setYPosition(int yPosition) {
        super.setYPosition(yPosition);
    }

    /**
     * getXSize
     * returns the x size  of the class
     * @return the x size of the class
     */
    @Override
    public int getXSize() {
        return super.getXSize();
    }

    /**
     * setXSize
     * updates the x size of the class
     * @param xPosition the new x size of the class
     */
    @Override
    public void setXSize(int xSize) {
        super.setXSize(xSize);
    }

    /**
     * getYSize
     * returns the y size  of the class
     * @return the y size of the class
     */
    @Override
    public int getYSize() {
        return super.getYSize();
    }

    /**
     * setYSize
     * updates the y size of the class
     * @param xPosition the new y size of the class
     */
    @Override
    public void setYSize(int ySize) {
        super.setYSize(ySize);
    }
}
