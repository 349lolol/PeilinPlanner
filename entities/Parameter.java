/**
 * [Parameter.java]
 * Class representing a method parameter
 * @author Perry Xu
 * @version 1.0
 * 01/07/24
 */

package entities;

public class Parameter {
    private String type;
    private String name;


    @Override
    public String toString(){
        String data = "";
        data = "<type> " + this.type + "</type>\n";
        data = "<name> " + this.name + "</name>\n";
        return data;
    }
    /**
     * Parameter
     * Constructor for the parameter class
     * @param type the data type of the parameter
     * @param name the name of the parameter
     */
    public Parameter(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * getType
     * returns the parameter's type
     * @return the parameter's type
     */
    public String getType() {
        return type;
    }

    /**
     * setType
     * updates the parameter's type
     * @param type the parameter's new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getName
     * returns the parameter's name
     * @return the parameter's name
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * updates the parameter's name
     * @param name the parameter's new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
