/**
 * [Project.java]
 * Class representing a UML PROJECT
 * @author Perry Xu & Patrick Wei
 * @version 1.0
 * 01/07/24
 */

package entities;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.ArrayList;

public class Project{
    private final String projectName;
    private int diagramIdCount;
    private final HashMap<Integer, Diagram> diagrams;
    private int arrowIdCount;
    private final HashMap<Integer, Arrow> arrows;

    /**
     * toString
     * constructs XML structure for a UML project
     */
    @Override
    public String toString(){
        String data = "<OBJECTYPE-PROJECT>";
        data = data + "<name= " + diagramIdCount +">" + projectName + "</name>\n";
        data = data + "<diagrams>\n";
        for(int index = 0; index < diagramIdCount; index++){
            if(diagrams.get(index) != null){
                //object has not been deleted by user, add to list
                data = data + "<diagram id:" + index + ">\n";
                if(diagrams.get(index) instanceof ClassDiagram){
                    data = data + ((ClassDiagram)diagrams.get(index)).toString();
                }
                else if(diagrams.get(index) instanceof ExceptionDiagram){
                    data = data + ((ExceptionDiagram)diagrams.get(index)).toString(); 
                }
                else if(diagrams.get(index) instanceof InterfaceDiagram){
                    data = data + ((InterfaceDiagram)diagrams.get(index)).toString(); 
                }
                else {
                    System.out.println("error: bad class in Project.java");
                }
                data = data + "</diagram>\n";
            }
        }
        data = data + "</diagrams>\n";

        data = data + "<arrows>\n";
        for(int index = 0; index < arrowIdCount; index++){
            if(arrows.get(index) != null){
                //object has not been deleted by user, add to list
                data = data + "<arrow id:" + index + ">\n";
                data = data + arrows.get(index).toString(); //change to instanceof checks
                data = data + "</arrow>\n";
            }
        }
        data = data + "</arrows>\n";
        return data;
    }


    /**
     * fromFile
     * deparses XML structure into java project structure
     * @param data data from string
     */
    public void fromFile(String data){
        String[] lines = data.split("\n");
        ArrayList<Integer> diagramIndices = new ArrayList<Integer>();
        ArrayList<Integer> arrowIndices = new ArrayList<Integer>();
        for(int i = 0; i < lines.length; i++){
            if((lines[i].equals("<OBJECTYPE> CLASSDIAGRAM </OBJECTYPE>")) || (lines[i].equals("<OBJECTYPE> EXCEPTIONDIAGRAM </OBJECTYPE>")) || (lines[i].equals("<OBJECTYPE> INTERFACEDIAGRAM </OBJECTYPE>"))){
                diagramIndices.add(i);
                //all of the single variable/single line values are written first
                //do string.split on " ", take the middle value
                //after that it is the variables/methods, which then requires another layer of searching for start/end of variable/method
                //based on the length of the list, create for loop that iterates through n lines and process each line with string.split " "
            }
            else if(lines[i].equals("<OBJECTYPE> ARROW </OBJECTYPE>")){
                arrowIndices.add(i);
                //all of the single variable/single line values are written first
                //do string.split on " ", take the middle value
                //after that its two lists of x/y coordinates
                //the lists for these have already been.tostringed using "a, b, c, d, e", just do string.split on ", " to get the values with parseint, add to arraylist
            }
        }

        //extract data from each point

        //now all classes starting points are stored
        //iteratively sort through each diagram indice
        //just use string.split(" ") to seperate the header/foot from the actual data
        //for x,y, coordiantes of an arrow do string.split(", ")
    }

    Project(String projectName){
        this.diagramIdCount = 0;
        this.projectName = projectName;
        diagrams = new HashMap<Integer, Diagram>();
        this.arrowIdCount = 0;
        arrows = new HashMap<Integer, Arrow>();
    }

    public void addDiagram(Diagram diagram){   
        diagrams.put(diagramIdCount, diagram);
        diagramIdCount++;
    }

    public Diagram getDiagram(int id){
        return diagrams.get(id);
    }

    public LinkedList<Diagram> getAllDiagrams(){
        LinkedList<Diagram> allDiagrams = new LinkedList<Diagram>();
        for(int i = 0; i < diagramIdCount; i++){
            if(getDiagram(i) != null){
                allDiagrams.add(getDiagram(i));
            }
        }
        return allDiagrams;
    }

    public void addArrow(Arrow arrow){
        arrows.put(arrowIdCount, arrow);
        arrowIdCount++;
    }

    public Arrow getArrow(int id){
        return arrows.get(id);
    }

    public LinkedList<Arrow> getAllArrows(){
        LinkedList<Arrow> allArrows = new LinkedList<Arrow>();
        for(int i = 0; i < arrowIdCount; i++){
            if(getArrow(i) != null){
                allArrows.add(getArrow(i));
            }
        }
        return allArrows;
    }

    public String getProjectName(){
        return this.projectName;
    }

    
}
