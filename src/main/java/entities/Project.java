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

public class Project {
    private String projectName;
    private int diagramIdCount;
    private final HashMap<Integer, Diagram> diagrams;
    private int arrowIdCount;
    private final HashMap<Integer, Arrow> arrows;

    /**
     * JsonToJava
     * creates a complete new project based on json data
     * @param data the json string being converted
     */
    public void JsonToJava(String data) {
        String[] lines = data.split("\n");
        this.projectName = lines[1].split(": ")[1];
        this.projectName = this.projectName.substring(0, this.projectName.length()-1);
        String diagramCountString = lines[2].split(": ")[1];
        this.diagramIdCount = Integer.parseInt(diagramCountString.substring(0, diagramCountString.length()-1));
        String arrowCountString = lines[2].split(": ")[1];
        this.arrowIdCount = Integer.parseInt(arrowCountString.substring(0, arrowCountString.length()-1));

        ArrayList<Integer> listStartIndices = new ArrayList<Integer>();
        ArrayList<Integer> listEndIndices = new ArrayList<Integer>();
        for(int i = 1; i < lines.length; i++) {
            if(lines[i].startsWith("    [")) {
                listStartIndices.add(i);
            }
            if(lines[i].startsWith("    ]")) {
                listEndIndices.add(i);
            }
        }

        //classDiagram
        int currentIndex = listStartIndices.get(0) + 1;
        while(currentIndex < listEndIndices.get(0) - 3) {
            if(lines[currentIndex].startsWith("        {")) {
                currentIndex = currentIndex + 1;
                String temp = lines[currentIndex].split(": ")[1];
                int classId = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                String className = temp.substring(0, temp.length() - 1);
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int xPosition = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int yPosition = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int xSize = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int ySize = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                boolean isAbstract = temp.equals("true,");
                
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split("[")[1];
                temp = temp.substring(0, temp.length() - 2);
                String[] fieldStrings = temp.split(", ");
                LinkedList<Field> fields = new LinkedList<Field>();
                for(int i = 0; i < fieldStrings.length; i++) {
                    fields.add(new Field(fieldStrings[i]));
                }

                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split("[")[1];
                temp = temp.substring(0, temp.length() - 2);
                String[] methodStrings = temp.split(", ");
                LinkedList<Method> methods = new LinkedList<Method>();
                for(int i = 0; i < methodStrings.length; i++) {
                    methods.add(new Method(methodStrings[i]));
                }
                diagrams.put(classId, new ClassDiagram(className, isAbstract, fields, methods, xPosition, yPosition, xSize, ySize));
            }
            else {
                currentIndex = currentIndex + 1;
            }
        }
        //interfaceDiagram
        currentIndex = listStartIndices.get(1) + 1;
        while(currentIndex < listEndIndices.get(1) - 3) {
            if(lines[currentIndex].startsWith("        {")) {
                currentIndex = currentIndex + 1;
                String temp = lines[currentIndex].split(": ")[1];
                int classId = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                String className = temp.substring(0, temp.length() - 1);
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int xPosition = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int yPosition = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int xSize = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int ySize = Integer.parseInt(temp.substring(0, temp.length() - 1));

                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split("[")[1];
                temp = temp.substring(0, temp.length() - 2);
                String[] methodStrings = temp.split(", ");
                LinkedList<Method> methods = new LinkedList<Method>();
                for(int i = 0; i < methodStrings.length; i++) {
                    methods.add(new Method(methodStrings[i]));
                }

                diagrams.put(classId, new InterfaceDiagram(className, methods, xPosition, yPosition, xSize, ySize));
            }
            else {
                currentIndex = currentIndex + 1;
            }
        }

        //exceptionDiagram
        currentIndex = listStartIndices.get(2) + 1;
        while(currentIndex < listEndIndices.get(2) - 3) {
            if(lines[currentIndex].startsWith("        {")) {
                currentIndex = currentIndex + 1;
                String temp = lines[currentIndex].split(": ")[1];
                int classId = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                String className = temp.substring(0, temp.length() - 1);
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int xPosition = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int yPosition = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int xSize = Integer.parseInt(temp.substring(0, temp.length() - 1));
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int ySize = Integer.parseInt(temp.substring(0, temp.length() - 1));

                diagrams.put(classId, new ExceptionDiagram(className, xPosition, yPosition, xSize, ySize));
            }
            else {
                currentIndex = currentIndex + 1;
            }
        }

        //all types of arrows
        currentIndex = listStartIndices.get(3) + 1;
        while(currentIndex < listEndIndices.get(3) - 3) {
            if(lines[currentIndex].startsWith("        {")) {
                currentIndex = currentIndex + 1;
                String temp = lines[currentIndex].split(": ")[1];
                String originName = temp.substring(0, temp.length() - 1);
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                String destinationName = temp.substring(0, temp.length() - 1);
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                String arrowType = temp.substring(0, temp.length() - 1);
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                int arrowId = Integer.parseInt(temp.substring(0, temp.length() - 1));

                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                temp = temp.substring(1, temp.length()-1);
                String[] xCoordinateStrings = temp.split(", ");
                
                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split(": ")[1];
                temp = temp.substring(1, temp.length()-1);
                String[] yCoordinateStrings = temp.split(", ");
                
                ArrayList<Integer> xCoordinates = new ArrayList<Integer>();
                ArrayList<Integer> yCoordinates = new ArrayList<Integer>();
                for(int i = 0; i < xCoordinateStrings.length; i++) {
                    xCoordinates.add(Integer.parseInt(xCoordinateStrings[i]));
                    yCoordinates.add(Integer.parseInt(yCoordinateStrings[i]));
                }
                arrows.put(arrowId, new Arrow(this.getDiagram(originName), this.getDiagram(destinationName), arrowType, xCoordinates, yCoordinates));                
            }
            else {
                currentIndex = currentIndex + 1;
            }
        }
    }

    /**
     * Project
     * empty constructor
     */
    public Project() {
        this.diagramIdCount = 0;
        this.projectName = "";
        diagrams = new HashMap<Integer, Diagram>();
        this.arrowIdCount = 0;
        arrows = new HashMap<Integer, Arrow>();
    }

    /**
     * getDiagram
     * gets the matching diagram from this project
     * @param diagramName name being searched
     * @return the diagram, or null if not found
     */
    public Diagram getDiagram(String diagramName) {
        for(int i = 0; i < diagramIdCount; i++) {
            if(diagrams.get(i).getName().equals(diagramName)) {
                return diagrams.get(i);
            }
        }
        return null;
    }

    /**
     * addDiagram
     * adds a diagram to the system
     * @param diagram diagram being added
     */
    public void addDiagram(Diagram diagram) {   
        diagrams.put(diagramIdCount, diagram);
        diagramIdCount++;
    }

    /**
     * getDiagram
     * gets a diagram based on the map's id system
     * @param id id being searched by
     * @return desired diagram, or null if not found
     */
    public Diagram getDiagram(int id) {
        return diagrams.get(id);
    }

    /**
     * getId
     * gets an id based on the diagram
     * @param diagram diagram whose id is being searched for
     * @return the diagram id, or -1 if not found
     */
    public int getId(Diagram diagram) {
        for(int i = 0; i < diagramIdCount; i++) {
            if(diagrams.get(i).equals(diagram)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * getALlDiagrams
     * gets all of the diagrams as a linkedList
     * @return all diagrams
     */
    public LinkedList<Diagram> getAllDiagrams() {
        LinkedList<Diagram> allDiagrams = new LinkedList<Diagram>();
        for(int i = 0; i < diagramIdCount; i++) {
            if(getDiagram(i) != null) {
                allDiagrams.add(getDiagram(i));
            }
        }
        return allDiagrams;
    }

    /**
     * addArrow
     * adds an arrow to the system
     * @param arrow arrow being added
     */
    public void addArrow(Arrow arrow) {
        arrows.put(arrowIdCount, arrow);
        arrowIdCount++;
    }

    /**
     * getArrow
     * gets an arrow based on the map id
     * @param id map id
     * @return the arrow
     */
    public Arrow getArrow(int id) {
        return arrows.get(id);
    }

    public LinkedList<Arrow> getAllArrows() {
        LinkedList<Arrow> allArrows = new LinkedList<Arrow>();
        for(int i = 0; i < arrowIdCount; i++) {
            if(getArrow(i) != null) {
                allArrows.add(getArrow(i));
            }
        }
        return allArrows;
    }

    /**
     * getProjectName
     * gets the name of this project
     * @return name of this project
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * setProjectName
     * sets a new name for this project
     * @param name the new name
     */
    public void setProjectName(String name) {
        this.projectName = name;
    }
    
}
