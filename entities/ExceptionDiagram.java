/**
 * [ExceptionDiagram.java]
 * Class representing an exception diagram in UML
 * @author Perry Xu
 * @version 1.0
 * 01/07/24
 */

package entities;

public class ExceptionDiagram {
    private String name;

    /**
     * ExceptionDiagram
     * Constructor for the ExceptionDiagram class
     * @param name the name of the exception
     */
    public ExceptionDiagram(String name) {
        this.name = name;
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
}
