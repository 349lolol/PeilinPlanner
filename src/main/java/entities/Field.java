/**
 * [Field.java]
 * Class representing a class field
 * @author Perry Xu & Patrick Wei
 * @version 1.2
 * 01/29/24
 */

package entities;

import java.io.Serializable;

public class Field implements Serializable {
    private String data;

    /**
     * toJava
     * returns the field in a usable java manner
     * @return field in java style
     */
    public String toJava() {
        return (data + ";\n");
    }

    /**
     * Field
     * constructs field
     * @param data contains modifier, type, 
     */
    public Field(String data) {
        this.data = data;
    }

    /**
     * Field
     * Empty field constructor
     */
    public Field() {
        this.data = "EMPTY";
    }

    /**
     * getData
     * retrieves the parameter stored in the class
     */
    public String getData() {
        return this.data;
    }

    /**
     * setData
     * updates the data of the class
     * @param data the new field being stored
     */
    public void setData(String data) {
        this.data = data;
    }
}
