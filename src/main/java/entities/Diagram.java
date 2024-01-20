/**
 * [Diagram.java]
 * Class representing a diagram in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package entities;

public class Diagram {
    private String name;
    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;

    public Diagram(String name, int xPosition, int yPosition, int xSize, int ySize) {
        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    /**
     * getName
     * returns the name of the exception
     * 
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
