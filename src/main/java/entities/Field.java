/**
 * [Parameter.java]
 * Class representing a class field
 * @author Perry Xu & Patrick Wei
 * @version 1.0
 * 01/07/24
 */

package entities;

public class Field {
    private String data;

    @Override
    public String toString() {
        return "<Line> " + this.data + "</Line>\n"; 
    }

    public String toJava() {
        return (data + ";\n");
    }

    public Field(String data) {
        this.data = data;
    }

    public Field() {
        this.data = "EMPTY";
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
