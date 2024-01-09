/**
 * [InterfaceDiagram.java]
 * Class representing an interface diagram in UML
 * @author Perry Xu
 * @version 1.1
 * 01/09/24
 */

package entities;

import java.util.LinkedList;

public class InterfaceDiagram {
    private String name;
    private final LinkedList<Method> methods;
    private int xPosition;
    private int yPosition;
    private int xSize;
    private int ySize;

    /**
     * InterfaceDiagram
     * Constructor for the InterfaceDiagram class
     * @param name the name of the interface
     * @param methods the methods of the interface
     * @param xPosition the x position of the top left of the class
     * @param yPosition the y position of the top left of the class
     * @param xSize the x size of the class
     * @param ySize the y size of the class
     */
    public InterfaceDiagram(String name, LinkedList<Method> methods, int xPosition, int yPosition, int xSize, int ySize) {
        this.name = name;
        this.methods = methods;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    /**
     * getName
     * returns the name of the interface
     * @return the name of the interface
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * updates the name of the interface
     * @param name the new name of the interface
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getMethods
     * returns the methods of the interface
     * @return the methods of the interface
     */
    public LinkedList<Method> getMethods() {
        return methods;
    }

    /**
     * addMethod
     * adds a method to the interface diagram
     * @param method the new method to be added to the interface diagram
     */
    public void addMethod(Method method) {
        this.methods.add(method);
    }

    /**
     * removeMethod
     * removes a method from the interface diagram by element
     * @param method the method to be removed from the interface diagram
     */
    public void removeMethod(Method method) {
        this.methods.remove(method);
    }

    /**
     * updateMethod
     * updates a method from the interface diagram by element
     * @param oldMethod the method to be replaced from the interface diagram
     * @param newMethod the new method
     */
    public void updateMethod(Method oldMethod, Method newMethod) {
        this.methods.set(this.methods.indexOf(oldMethod), newMethod);
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
