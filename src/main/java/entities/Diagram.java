/**
 * [Diagram.java]
 * Class representing a diagram in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.2
 * 01/29/24
 */

package entities;

import java.io.Serializable;

public abstract class Diagram implements Serializable {
    private String name;
    private int id;
    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;

    /**
     * Diagram
     * constructs a new diagram
     * @param name name of diagram
     * @param id id of the diagram
     * @param xPosition x position of diagram
     * @param yPosition y position of diagram
     * @param xSize x size of diagram
     * @param ySize y size of diagram
     */
    public Diagram(String name, int id, int xPosition, int yPosition, int xSize, int ySize) {
        this.name = name;
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    /**
     * getName
     * returns the name of the diagram
     * @return the name of the diagram
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * updates the name of the diagram
     * @param name the new name of the diagram
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getId
     * returns the id of the diagram
     * @return the id of the diagram
     */
    public int getId() {
        return this.id;
    }

    /**
     * setId
     * updates the id of the diagram
     * @param name the new id of the diagram
     */
    public void setId(int id) {
        this.id = id;
    }

        /**
     * getXPosition
     * returns the x position of the top left of the diagram
     * @return the x position of the top left of the diagram
     */
    public int getXPosition() {
        return this.xPosition;
    }

    /**
     * setXPosition
     * updates the x position of the top left of the diagram
     * @param xPosition the new x position of the top left of the diagram
     */
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * getYPosition
     * returns the y position of the top left of the diagram
     * @return the y position of the top left of the diagram
     */
    public int getYPosition() {
        return this.yPosition;
    }

    /**
     * setYPosition
     * updates the y position of the top left of the diagram
     * @param xPosition the new y position of the top left of the diagram
     */
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * getXSize
     * returns the x size of the class
     * @return the x size of the class
     */
    public int getXSize() {
        return this.xSize;
    }

    /**
     * setXSize
     * updates the x size of the diagram
     * @param xPosition the new x size of the diagram
     */
    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    /**
     * getYSize
     * returns the y size of the diagram
     * @return the y size of the diagram
     */
    public int getYSize() {
        return this.ySize;
    }

    /**
     * setYSize
     * updates the y size of the diagram
     * @param xPosition the new y size of the diagram
     */
    public void setYSize(int ySize) {
        this.ySize = ySize;
    }
}
