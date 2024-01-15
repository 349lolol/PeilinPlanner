/**
 * [ClassDiagram.java]
 * Class representing a class diagram in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package entities;

import java.util.LinkedList;

public class ClassDiagram extends Diagram {
    private boolean isAbstract;
    private final LinkedList<Field> fields;
    private final LinkedList<Method> methods;

    /**
     * toString
     * constructs XML structure for class diagram
     */
    @Override
    public String toString(){
        String data = "<OBJECTYPE> CLASSDIAGRAM </OBJECTYPE>\n";
        data = data +  "<name> " + super.getName() + " </name>\n";
        data = data +  "<xPosition> " + super.getXPosition() + " </xPosition>\n";
        data = data +  "<yPosition> " + super.getYPosition() + " </yPosition>\n";
        data = data +  "<xSize> " + super.getYSize() + " </xSize>\n";
        data = data +  "<ySize> " + super.getYSize() + " </ySize>\n";
        data = data + "<isAbstract> " + this.isAbstract() + " </isAbstract>\n";
        data = data + "<fields=" + fields.size() + ">\n";
            for(Field field : fields){
                data = data + field.toString();
            }
        data = data + "</fields>\n";
        data = data + "<methods= " + methods.size() + ">\n";
            for(Method method : methods){
                data = data + method.toString();
            }
        data = data + "</methods>\n";
        return data;
    }

    /**
     * ClassDiagram
     * Constructor for the ClassDiagram class
     * @param name the name of the class
     * @param isAbstract the abstract status of the class
     * @param fields the fields of the class
     * @param methods the methods of the class
     * @param xPosition the x position of the top left of the class
     * @param yPosition the y position of the top left of the class
     * @param xSize the x size of the class
     * @param ySize the y size of the class
     */
    public ClassDiagram(String name, boolean isAbstract, LinkedList<Field> fields, LinkedList<Method> methods, int xPosition,
    int yPosition, int xSize, int ySize) {
        super(name, xPosition, yPosition, xSize, ySize);
        this.isAbstract = isAbstract;
        this.fields = fields;
        this.methods = methods;
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
     * isAbstract
     * Returns the abstract status of the class
     * @return the abstract status of the class
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * setAbstract
     * updates the abstract status of the class
     * @param abstractStatus the new abstract status of the class
     */
    public void setAbstract(boolean abstractStatus) {
        isAbstract = abstractStatus;
    }

    /**
     * getFields
     * returns the fields of the class
     * @return the fields of the class
     */
    public LinkedList<Field> getFields() {
        return fields;
    }

    /**
     * addField
     * adds a field to the class diagram
     * @param field the new field to be added to the class diagram
     */
    public void addField(Field field) {
        this.fields.add(field);
    }

    /**
     * removeField
     * removes a field from the class diagram by element
     * @param field the field to be removed from the class diagram
     */
    public void removeField(Field field) {
        this.fields.remove(field);
    }

    /**
     * updateField
     * updates a field from the class diagram by element
     * @param oldField the field to be replaced from the class diagram
     * @param newField the new field
     */
    public void updateField(Field oldField, Field newField) {
        this.fields.set(this.fields.indexOf(oldField), newField);
    }

    /**
     * getMethods
     * returns the methods of the class
     * @return the methods of the class
     */
    public LinkedList<Method> getMethods() {
        return methods;
    }

    /**
     * addMethod
     * adds a method to the class diagram
     * @param method the new method to be added to the class diagram
     */
    public void addMethod(Method method) {
        this.methods.add(method);
    }

    /**
     * removeMethod
     * removes a method from the class diagram by element
     * @param method the method to be removed from the class diagram
     */
    public void removeMethod(Method method) {
        this.methods.remove(method);
    }

    /**
     * updateMethod
     * updates a method from the class diagram by element
     * @param oldMethod the method to be replaced from the class diagram
     * @param newMethod the new method
     */
    public void updateMethod(Method oldMethod, Method newMethod) {
        this.methods.set(this.methods.indexOf(oldMethod), newMethod);
    }
}
