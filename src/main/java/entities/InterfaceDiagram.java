/**
 * [InterfaceDiagram.java]
 * Class representing an interface diagram in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package entities;

import java.util.LinkedList;

public class InterfaceDiagram extends Diagram {
    private final LinkedList<Method> methods;

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
        super(name, xPosition, yPosition, xSize, ySize);
        this.methods = methods;
        
    }

    /**
     * toJson
     * prints interfaceDiagarm to json
     * @return
     */

    public String toJson(){
        String data = "\"name\": " + getName() + ", ";
        data = data + "\"xPosition\": " + super.getXPosition() + ", ";
        data = data + "\"yPosition\": " + super.getYPosition() + ", ";
        data = data + "\"xSize\": " + super.getXSize() + ", ";
        data = data + "\"ySize\": " + super.getYSize() + ", ";
        data = data + "\"methods\": " + linkedListToJson(this.methods);
        return data;
    }

    private String linkedListToJson(LinkedList<Method> points) {
        String data = "[";
        for(int i = 0; i < points.size(); i++) {
            data = data + points + ", ";
        }
        data = data.substring(0, data.length()-2);
        data = data + "]";
        return data;
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
     * methodsToJson
     * converts all methods into JSON
     * @return a String in JSON representing the methods of the class diagram
     */
    public String methodsToJson() {
        StringBuilder methodsJson = new StringBuilder();
        methodsJson.append("\"fields\": [");
        for (int i = 0; i < this.methods.size(); i++) {
            if (i != this.methods.size() - 1) {
                methodsJson.append("\"" + this.methods.get(i) + "\",");
            } else {
                methodsJson.append("\"" + this.methods.get(i) + "\"],");
            }
        }

        return methodsJson.toString();
    }
}
