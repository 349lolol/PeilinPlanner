/**
 * [Method.java]
 * Class representing a method
 * @author Perry Xu & Patrick Wei
 * @version 1.2
 * 01/29/24
 */

package entities;

import java.io.Serializable;

public class Method implements Serializable {
    private String data;

    /**
     * toJava
     * prints the method out in a java style
     */
    public String toJava() {
        if(data.contains("abstract")) {
            return this.data + ";\n";
        } else {
            return this.data + "{\n\n}";
        }
    }

    /**
     * Method
     * @param data the method the object stores
     */
    public Method(String data) {
        this.data = data;
    }

    /**
     * Method
     * empty constructor
     */
    public Method() {
        this.data = "EMPTY";
    }

    /**
     * getData
     * retrieves the method stored in the object
     */
    public String getData() {
        return this.data;
    }

    /**
     * setData
     * updates the stored method of the class
     * @param xPosition the new method in the class
     */
    public void setData(String data) {
        this.data = data;
    }
}