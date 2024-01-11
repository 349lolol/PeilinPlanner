/**
 * [Parameter.java]
 * Class representing a class field
 * @author Perry Xu
 * @version 1.0
 * 01/07/24
 */

package entities;

public class Field {
    private String modifier;
    private String type;
    private String name;

    @Override
    public String toString(){
        String data = "<modifier> " + this.modifier + "</modifier>\n";
        data = "<type> " + this.type + "</type>\n";
        data = "<name> " + this.name + "</name>\n";
        return data;
    }

    /**
     * Field
     * Constructor for the field class
     * @param modifier the modifier for the field
     * @param type the data type of the field
     * @param name the name of the field
     */
    public Field(String modifier, String type, String name) {
        this.modifier = modifier;
        this.type = type;
        this.name = name;
    }

    /**
     * getModifier
     * returns the field's modifier
     * @return the field's modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * setModifier
     * updates the field's modifier
     * @param modifier the new modifier of the field
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * getType
     * returns the field's data type
     * @return the field's data type
     */
    public String getType() {
        return type;
    }

    /**
     * setType
     * updates the field's data type
     * @param type the new data type of the field
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getName
     * returns the field's name
     * @return the field's name
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * updates the field's name
     * @param name the new name of the field
     */
    public void setName(String name) {
        this.name = name;
    }
}
