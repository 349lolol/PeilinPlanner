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
import java.util.Arrays;

public class Project{
    private String projectName;
    private int diagramIdCount;
    private final HashMap<Integer, Diagram> diagrams;
    private int arrowIdCount;
    private final HashMap<Integer, Arrow> arrows;

    public void JsonToJava(String data){
        String[] lines = data.split("\n");
        this.projectName = lines[1].split(": ")[1];
        this.projectName = this.projectName.substring(0, this.projectName.length()-1);
        String diagramCountString = lines[2].split(": ")[1];
        this.diagramIdCount = Integer.parseInt(diagramCountString.substring(0, diagramCountString.length()-1));
        String arrowCountString = lines[2].split(": ")[1];
        this.arrowIdCount = Integer.parseInt(arrowCountString.substring(0, arrowCountString.length()-1));

        ArrayList<Integer> listStartIndices = new ArrayList<Integer>();
        ArrayList<Integer> listEndIndices = new ArrayList<Integer>();
        for(int i = 1; i < lines.length; i++){
            if(lines[i].startsWith("    [")){
                listStartIndices.add(i);
            }
            if(lines[i].startsWith("    ]")){
                listEndIndices.add(i);
            }
        }

        int currentIndex = listStartIndices.get(0) + 1;
        while(currentIndex < listEndIndices.get(0) - 3) {
            if(lines[currentIndex].startsWith("        {")){
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
                ArrayList<Field> fields = new ArrayList<Field>();
                for(int i = 0; i < fieldStrings.length; i++){
                    fields.add(new Field(fieldStrings[i]));
                }

                currentIndex = currentIndex + 1;
                temp = lines[currentIndex].split("[")[1];
                temp = temp.substring(0, temp.length() - 2);
                String[] methodStrings = temp.split(", ");
                ArrayList<Method> methods = new ArrayList<Method>();
                for(int i = 0; i < methodStrings.length; i++){
                    methods.add(new Method(methodStrings[i]));
                }
            }
            else {
                currentIndex = currentIndex + 1;
            }
        }

    }

    public Project() {
        this.diagramIdCount = 0;
        this.projectName = "";
        diagrams = new HashMap<Integer, Diagram>();
        this.arrowIdCount = 0;
        arrows = new HashMap<Integer, Arrow>();
    }

    public Diagram getDiagram(String diagramName) {
        for(int i = 0; i < diagramIdCount; i++) {
            if(diagrams.get(i).getName().equals(diagramName)) {
                return diagrams.get(i);
            }
        }
        return null;
    }

    public void addDiagram(Diagram diagram) {   
        diagrams.put(diagramIdCount, diagram);
        diagramIdCount++;
    }

    public Diagram getDiagram(int id) {
        return diagrams.get(id);
    }

    public int getId(Diagram diagram) {
        for(int i = 0; i < diagramIdCount; i++) {
            if(diagrams.get(i).equals(diagram)) {
                return i;
            }
        }
        return -1;
    }

    public LinkedList<Diagram> getAllDiagrams() {
        LinkedList<Diagram> allDiagrams = new LinkedList<Diagram>();
        for(int i = 0; i < diagramIdCount; i++) {
            if(getDiagram(i) != null) {
                allDiagrams.add(getDiagram(i));
            }
        }
        return allDiagrams;
    }

    public void addArrow(Arrow arrow) {
        arrows.put(arrowIdCount, arrow);
        arrowIdCount++;
    }

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

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String name) {
        this.projectName = name;
    }
    
}
