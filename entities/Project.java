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
    private String projectName;
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
        data = data + "<name:" + diagramIdCount +":"+arrowIdCount+ ":> " + projectName + " </name>\n";
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
                data = data + arrows.get(index).toString(this); //change to instanceof checks
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
        int counter = 0; 
        setProjectName(lines[1].split(" ")[1]);
        this.diagramIdCount = Integer.parseInt(lines[1].split(" ")[0].split(":")[1]);
        this.arrowIdCount = Integer.parseInt(lines[1].split(" ")[0].split(":")[2]);
        for(int i = 0; i < lines.length; i++){
            if((lines[i].equals("<OBJECTYPE> CLASSDIAGRAM </OBJECTYPE>")) || (lines[i].equals("<OBJECTYPE> EXCEPTIONDIAGRAM </OBJECTYPE>")) || (lines[i].equals("<OBJECTYPE> INTERFACEDIAGRAM </OBJECTYPE>"))){
                diagramIndices.add(i);
                
            }
            else if(lines[i].equals("<OBJECTYPE> ARROW </OBJECTYPE>")){
                arrowIndices.add(i);
            }
        }
        //all of the single variable/single line values are written first
        //do string.split on " ", take the middle value
        //after that it is the variables/methods, which then requires another layer of searching for start/end of variable/method
        //based on the length of the list, create for loop that iterates through n lines and process each line with string.split " "
        for (int i = 0; i < diagramIndices.size(); i++) {
            String idStore = lines[diagramIndices.get(i) - 1].split(":")[1];
            int id = Integer.parseInt(idStore.substring(0, idStore.length() - 1));
            
            if (lines[diagramIndices.get(i)].equals("<OBJECTYPE> CLASSDIAGRAM </OBJECTYPE>")) {
                String name = (lines[diagramIndices.get(i) + 1].split(" ")[1]);
                int xPosition = Integer.parseInt(lines[diagramIndices.get(i) + 2].split(" ")[1]);
                int yPosition = Integer.parseInt(lines[diagramIndices.get(i) + 3].split(" ")[1]);
                int xSize = Integer.parseInt(lines[diagramIndices.get(i) + 4].split(" ")[1]);
                int ySize = Integer.parseInt(lines[diagramIndices.get(i) + 5].split(" ")[1]);
                boolean isAbstract = Boolean.valueOf(lines[diagramIndices.get(i) + 6].split(" ")[1]);

                counter = 7;
                LinkedList<Field> fields = new LinkedList<Field>();

                for (int j = 0; j < Integer.parseInt(lines[diagramIndices.get(i) + counter].split(" ")[1]); j++) {
                    fields.add(new Field());
                    counter = counter + 1;
                    fields.get(fields.size()-1).setData(lines[diagramIndices.get(i) + counter]);
                }
                //increment counter once to represent end point
                LinkedList<Method> methods = new LinkedList<Method>();
                counter = counter + 1;
                for (int j = 0; j < Integer.parseInt(lines[diagramIndices.get(i) + counter].split(" ")[1]); j++) {
                    methods.add(new Method());
                    counter = counter + 1;
                    methods.get(methods.size()-1).setData(lines[diagramIndices.get(i) + counter]);
                    counter = 0;
                }
                diagrams.put(id, new ClassDiagram(name, isAbstract, fields, methods, xPosition, yPosition, xSize, ySize));
            } else if (lines[diagramIndices.get(i)].equals("<OBJECTYPE> EXCEPTIONDIAGRAM </OBJECTYPE>")) {
                String name = (lines[diagramIndices.get(i) + 1].split(" ")[1]);
                int xPosition = Integer.parseInt(lines[diagramIndices.get(i) + 2].split(" ")[1]);
                int yPosition = Integer.parseInt(lines[diagramIndices.get(i) + 3].split(" ")[1]);
                int xSize = Integer.parseInt(lines[diagramIndices.get(i) + 4].split(" ")[1]);
                int ySize = Integer.parseInt(lines[diagramIndices.get(i) + 5].split(" ")[1]);
                diagrams.put(id, new ExceptionDiagram(name, xPosition, yPosition, xSize, ySize));
                counter = 0;
                

            } else if (lines[diagramIndices.get(i)].equals("<OBJECTYPE> INTERFACEDIAGRAM </OBJECTYPE>")) {
                String name = (lines[diagramIndices.get(i) + 1].split(" ")[1]);
                int xPosition = Integer.parseInt(lines[diagramIndices.get(i) + 2].split(" ")[1]);
                int yPosition = Integer.parseInt(lines[diagramIndices.get(i) + 3].split(" ")[1]);
                int xSize = Integer.parseInt(lines[diagramIndices.get(i) + 4].split(" ")[1]);
                int ySize = Integer.parseInt(lines[diagramIndices.get(i) + 5].split(" ")[1]);

                counter = 6;
                LinkedList<Method> methods = new LinkedList<Method>();
                for (int j = 0; j < Integer.parseInt(lines[diagramIndices.get(i) + counter].split(" ")[1]); j++) {
                    methods.add(new Method());
                    counter = counter + 1;
                    methods.get(methods.size()-1).setData(lines[diagramIndices.get(i) + counter]);
                    diagrams.put(id, new InterfaceDiagram(name, methods, xPosition, yPosition, xSize, ySize));
                    counter = 0;
                }
            }
        }

        //extract data from each point

        //now all classes starting points are stored
        //iteratively sort through each diagram indice
        //just use string.split(" ") to seperate the header/foot from the actual data
        //for x,y, coordiantes of an arrow do string.split(",")
        for(int i = 0; i < arrowIndices.size(); i++){
            String idStore = lines[arrowIndices.get(i) - 1].split(":")[1];
            int id = Integer.parseInt(idStore.substring(0, idStore.length() - 1));
            counter = 0;
            Arrow arrow = new Arrow();
            counter++;
            arrow.setOrigin(this.getDiagram(Integer.parseInt(lines[arrowIndices.get(i) + counter].split(" ")[1])));
            counter++;
            arrow.setDestination(this.getDiagram(Integer.parseInt(lines[arrowIndices.get(i) + counter].split(" ")[1])));
            counter++;
            arrow.setArrowType(lines[arrowIndices.get(i) + counter].split(" ")[1]);
            counter++;
            String[] xCoords = lines[arrowIndices.get(i) + counter].split(" ")[1].split(",");
            counter++;
            String[] yCoords = lines[arrowIndices.get(i) + counter].split(" ")[1].split(",");
            //process xCoords and yCoords, add to arraylist
            ArrayList<Integer> xPoints = new ArrayList<Integer>();
            ArrayList<Integer> yPoints = new ArrayList<Integer>();
            for(int j = 0; i < xCoords.length; j++){
                xPoints.add(Integer.parseInt(xCoords[j]));
                yPoints.add(Integer.parseInt(yCoords[j]));
            }
            arrows.put(id, arrow);
        }
    }

    public Project(){
        this.diagramIdCount = 0;
        this.projectName = "";
        diagrams = new HashMap<Integer, Diagram>();
        this.arrowIdCount = 0;
        arrows = new HashMap<Integer, Arrow>();
    }

    public Diagram getDiagram(String diagramName){
        for(int i = 0; i < diagramIdCount; i++){
            if(diagrams.get(i).getName().equals(diagramName)){
                return diagrams.get(i);
            }
        }
        return null;
    }

    public void addDiagram(Diagram diagram){   
        diagrams.put(diagramIdCount, diagram);
        diagramIdCount++;
    }

    public Diagram getDiagram(int id){
        return diagrams.get(id);
    }

    public int getId(Diagram diagram){
        for(int i = 0; i < diagramIdCount; i++){
            if(diagrams.get(i).equals(diagram)){
                return i;
            }
        }
        return -1;
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

    public void setProjectName(String name){
        this.projectName = name;
    }
    
}
