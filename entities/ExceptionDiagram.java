/**
 * [ExceptionDiagram.java]
 * Class representing an exception diagram in UML
 * @author Perry Xu
 * @version 1.1
 * 01/09/24
 */

package entities;

public class ExceptionDiagram {
    private String name;
    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;

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
        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    /**
     * getName
     * returns the name of the exception
     * @return the name of the exception
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * updates the name of the exception
     * @param name the new name of the exception
     */
    public void setName(String name) {
        this.name = name;
    }

        /**
     * getXPosition
     * returns the x position of the top left of the class
     * @return the x position of the top left of the class
     */
    public int getXPosition() {
        return this.xPosition;
    }

    /**
     * setXPosition
     * updates the x position of the top left of the class
     * @param xPosition the new x position of the top left of the class
     */
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * getYPosition
     * returns the y position of the top left of the class
     * @return the y position of the top left of the class
     */
    public int getYPosition() {
        return this.yPosition;
    }

    /**
     * setYPosition
     * updates the y position of the top left of the class
     * @param xPosition the new y position of the top left of the class
     */
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * getXSize
     * returns the x size  of the class
     * @return the x size of the class
     */
    public int getXSize() {
        return this.xSize;
    }

    /**
     * setXSize
     * updates the x size of the class
     * @param xPosition the new x size of the class
     */
    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    /**
     * getYSize
     * returns the y size  of the class
     * @return the y size of the class
     */
    public int getYSize() {
        return this.ySize;
    }

    /**
     * setYSize
     * updates the y size of the class
     * @param xPosition the new y size of the class
     */
    public void setYSize(int ySize) {
        this.ySize = ySize;
    }
}
