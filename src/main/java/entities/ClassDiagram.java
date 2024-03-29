/**
 * [ClassDiagram.java]
 * Class representing a class diagram in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.2
 * 01/29/24
 */

package entities;

import java.util.LinkedList;

public class ClassDiagram extends Diagram {
    private boolean isAbstract;
    private final LinkedList<Field> fields;
    private final LinkedList<Method> methods;

    /**
     * ClassDiagram
     * Constructor for the ClassDiagram class
     * @param name the name of the class
     * @param id the id of the class
     * @param isAbstract the abstract status of the class
     * @param fields the fields of the class
     * @param methods the methods of the class
     * @param xPosition the x position of the top left of the class
     * @param yPosition the y position of the top left of the class
     * @param xSize the x size of the class
     * @param ySize the y size of the class
     */
    public ClassDiagram(String name, int id, boolean isAbstract, LinkedList<Field> fields, LinkedList<Method> methods, int xPosition,
    int yPosition, int xSize, int ySize) {
        super(name, id, xPosition, yPosition, xSize, ySize);
        this.isAbstract = isAbstract;
        this.fields = fields;
        this.methods = methods;
    }

    /**
     * converts json
     * @return a string rep of the json
     */
    public String toJson(){
        String data = "{\"name\": \"" + getName() + "\", ";
        data = data + "\"id\": " + super.getId() + ", ";
        data = data + "\"xPosition\": " + super.getXPosition() + ", ";
        data = data + "\"yPosition\": " + super.getYPosition() + ", ";
        data = data + "\"xSize\": " + super.getXSize() + ", ";
        data = data + "\"ySize\": " + super.getYSize() + ", ";
        data = data + "\"isAbstract\": " + this.isAbstract + ", ";
        data = data + "\"fields\": " + linkedListToJson(this.fields) + ", ";
        data = data + "\"methods\": " + linkedListToJson(this.methods) + "}";

        return data;
    }


    /**
     * LinkedListToJson
     * @param <T> type being converted
     * @param points list of items
     * @return string representation
     */
    private <T> String linkedListToJson(LinkedList<T> points) {
        if(points.size() > 0) {
            if(points.get(0) instanceof Method) {
                String data = "[";
                for(int i = 0; i < points.size(); i++) {
                    data = data + ((Method) points.get(i)).getData() + ", ";
                }
                data = data.substring(0, data.length()-2);
                data = data + "]";
                return data;
            }
            else if (points.get(0) instanceof Field) {
                String data = "[";
                for(int i = 0; i < points.size(); i++) {
                    data = data + ((Field) points.get(i)).getData() + ", ";
                }
                data = data.substring(0, data.length()-2);
                data = data + "]";
                return data;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * getName
     * returns the name of the class
     * @return the name of the class
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * setName
     * updates the name of the class
     * @param name the new name of the class
     */
    @Override
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * getId
     * returns the id of the class
     * @return the id of the class
     */
    @Override
    public int getId() {
        return super.getId();
    }

    /**
     * setId
     * updates the id of the class
     * @param name the new id of the class
     */
    @Override
    public void setId(int id) {
        super.setId(id);
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
     * fieldsToJson
     * converts all fields into JSON
     * @return a String in JSON representing the fields of the class diagram
     */
    public String fieldsToJson() {
        StringBuilder fieldsJson = new StringBuilder();
        fieldsJson.append("\"fields\": [");
        for (int i = 0; i < this.fields.size(); i++) {
            if (i != this.fields.size() - 1) {
                fieldsJson.append("\"" + this.fields.get(i) + "\",");
            } else {
                fieldsJson.append("\"" + this.fields.get(i) + "\"],");
            }
        }

        return fieldsJson.toString();
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
