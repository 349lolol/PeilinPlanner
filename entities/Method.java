/**
 * [Parameter.java]
 * Class representing a method
 * @author Perry Xu & Patrick Wei
 * @version 1.0
 * 01/07/24
 */

package entities;

import java.util.LinkedList;

public class Method {
    private String modifier;
    private String name;
    private String returnType;
    private boolean isAbstract;
    private LinkedList<Parameter> parameters;


    /**
     * toString
     * constructs XML structure for a method
     */
    @Override
    public String toString(){
        String data = "<modifier> " + this.modifier + " </modifier>\n";
        data = "<name> " + this.name + " </name>\n";
        data = "<returnType> " + this.returnType + " </returnType>\n";
        data = "<isAbstract> " + this.isAbstract + " </isAbstract>\n";
        data = "<parameters= " + parameters.size() + ">\n";
            for(Parameter parameter : parameters){
                data = parameter.toString();
            }
        data = data + "<parameters>\n";
        return data;
    }

    public String toJava(){
        String code = "";
        if(this.isAbstract()){
            code = this.modifier + " abstract " + this.returnType + " " + this.name + " (";
            for(int i = 0; i < parameters.size(); i++){
                code = code + parameters.get(i).getType() + " " + parameters.get(i).getName() + ", ";
            }
            code = code.substring(0, (code.length() - 2));
            code = code + ") {\n    \n}\n\n";
        }
        else {
            code = this.modifier + " " + this.returnType + " " + this.name + " (";
            for(int i = 0; i < parameters.size(); i++){
                code = code + parameters.get(i).getType() + " " + parameters.get(i).getName() + ", ";
            }
            code = code.substring(0, (code.length() - 2));
            code = code + ");\n\n";
        }
        return code;
    }
    /**
     * Method
     * Constructor for the method class
     * @param modifier The modifier of the method
     * @param name The name of the method
     * @param returnType The return type of the method
     * @param isAbstract The abstract status of the method
     * @param parameters The parameters of the method
     */
    public Method(String modifier, String name, String returnType, boolean isAbstract, LinkedList<Parameter> parameters) {
        this.modifier = modifier;
        this.name = name;
        this.returnType = returnType;
        this.isAbstract = isAbstract;
        this.parameters = parameters;
    }

    public Method() {
        this.modifier = "EMPTY";
        this.name = "EMPTY";
        this.returnType = "EMPTY";
        this.isAbstract = false;
        this.parameters = new LinkedList<Parameter>();
    }

    /**
     * getModifier
     * Returns the modifier of the method
     * @return the modifier of the method
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * setModifier
     * updates the modifier of the method
     * @param modifier the new modifier of the method
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * getName
     * Returns the name of the method
     * @return the name of the method
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * updates the name of the method
     * @param name the new name of the method
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getReturnType
     * Returns the return type of the method
     * @return the return type of the method
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * setReturnType
     * updates the return type of the method
     * @param returnType the new return type of the method
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * isAbstract
     * Returns the abstract status of the method
     * @return the abstract status of the method
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * setAbstract
     * updates the abstract status of the method
     * @param abstractStatus the new abstract status of the method
     */
    public void setAbstract(boolean abstractStatus) {
        isAbstract = abstractStatus;
    }

    /**
     * getParameters
     * Returns the parameters of the method
     * @return the parameters of the method
     */
    public LinkedList<Parameter> getParameters() {
        return parameters;
    }

    /**
     * addParameter
     * adds a parameter to the method
     * @param parameter the new parameter added to the method
     */
    public void addParameter(Parameter parameter) {
        this.parameters.add(parameter);
    }

    /**
     * removeParameter
     * removes a parameter from the method by element
     * @param parameter the parameter to be removed from the method
     */
    public void removeParameter(Parameter parameter) {
        this.parameters.remove(parameter);
    }

    /**
     * updateParameter
     * updates a parameter from the method by element
     * @param oldParameter the parameter to be updated from the method
     * @param newParameter the new parameter to the method
     */
    public void updateParameter(Parameter oldParameter, Parameter newParameter) {
        this.parameters.set(this.parameters.indexOf(oldParameter), newParameter);
    }
}
