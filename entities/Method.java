/**
 * [Parameter.java]
 * Class representing a method
 * @author Perry Xu & Patrick Wei
 * @version 1.0
 * 01/07/24
 */

package entities;

public class Method {
    private String data;



    public String toJava() {
        if(data.contains("abstract")) {
            return this.data + ";\n";
        } else {
            return this.data + "{\n\n}";
        }
    }

    public Method(String data) {
        this.data = data;
    }

    public Method() {
        this.data = "EMPTY";
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}