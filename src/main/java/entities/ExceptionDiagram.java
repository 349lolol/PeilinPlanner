/**
 * [ExceptionDiagram.java]
 * Class representing an exception diagram in UML
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package entities;

public class ExceptionDiagram extends Diagram {

    /**
     * ExceptionDiagram
     * Constructor for the ExceptionDiagram class
     * @param name the name of the exception
     * @param id the id of the exception
     * @param xPosition the x position of the top left of the exception
     * @param yPosition the y position of the top left of the exception
     * @param xSize the x size of the exception
     * @param ySize the y size of the exception
     */
    public ExceptionDiagram(String name, int id, int xPosition, int yPosition, int xSize, int ySize) {
        super(name, id, xPosition, yPosition, xSize, ySize);
    }

    /**
     * toJson
     * outputs exceptionDiagram as a json
     * @return
     */
    public String toJson(){
        String data = "{\"name\": \"" + getName() + "\", ";
        data = data + "\"id\": " + super.getId() + ", ";
        data = data + "\"xPosition\": " + super.getXPosition() + ", ";
        data = data + "\"yPosition\": " + super.getYPosition() + ", ";
        data = data + "\"xSize\": " + super.getXSize() + ", ";
        data = data + "\"ySize\": " + super.getYSize() + "}";
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
     * getId
     * returns the id of the exception
     * @return the id of the exception
     */
    @Override
    public int getId() {
        return super.getId();
    }

    /**
     * setId
     * updates the id of the exception
     * @param name the new id of the exception
     */
    @Override
    public void setId(int id) {
        super.setId(id);
    }

    /**
     * getXPosition
     * returns the x position of the top left of the exception
     * @return the x position of the top left of the exception
     */
    @Override
    public int getXPosition() {
        return super.getXPosition();
    }

    /**
     * setXPosition
     * updates the x position of the top left of the exception
     * @param xPosition the new x position of the top left of the exception
     */
    @Override
    public void setXPosition(int xPosition) {
        super.setXPosition(xPosition);
    }

    /**
     * getYPosition
     * returns the y position of the top left of the exception
     * @return the y position of the top left of the exception
     */
    @Override
    public int getYPosition() {
        return super.getYPosition();
    }

    /**
     * setYPosition
     * updates the y position of the top left of the exception
     * @param xPosition the new y position of the top left of the exception
     */
    @Override
    public void setYPosition(int yPosition) {
        super.setYPosition(yPosition);
    }

    /**
     * getXSize
     * returns the x size  of the exception
     * @return the x size of the exception
     */
    @Override
    public int getXSize() {
        return super.getXSize();
    }

    /**
     * setXSize
     * updates the x size of the exception
     * @param xPosition the new x size of the exception
     */
    @Override
    public void setXSize(int xSize) {
        super.setXSize(xSize);
    }

    /**
     * getYSize
     * returns the y size  of the exception
     * @return the y size of the exception
     */
    @Override
    public int getYSize() {
        return super.getYSize();
    }

    /**
     * setYSize
     * updates the y size of the exception
     * @param xPosition the new y size of the exception
     */
    @Override
    public void setYSize(int ySize) {
        super.setYSize(ySize);
    }
}
