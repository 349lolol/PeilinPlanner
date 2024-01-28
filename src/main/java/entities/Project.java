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

public class Project {
    private String projectName;
    private int diagramIdCount;
    private final HashMap<Integer, Diagram> diagrams;
    private int arrowIdCount;
    private final HashMap<Integer, Arrow> arrows;

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
     * Project
     * constructor for the project class given a name
     * @param name the project name
     */
    public Project(String name) {
        this.diagramIdCount = 0;
        this.projectName = name;
        diagrams = new HashMap<Integer, Diagram>();
        this.arrowIdCount = 0;
        arrows = new HashMap<Integer, Arrow>();
    }

    public String[] splitUML(String data) {
        int layersDeep = 0;
        int linesCount = 2;
        for(int i = 3; i < data.length(); i++){
            if(data.charAt(i) == '[') {
                layersDeep = layersDeep + 1;
            }
            else if(data.charAt(i) == ']') {
                layersDeep = layersDeep - 1;
            }
            else if((data.charAt(i) == ',') && (layersDeep < 2)) {
                linesCount = linesCount + 1;
            }
        }

        String[] lines = new String[linesCount];
        int lastRecordedEnter = 1;
        int linesIndex = 0;
        for(int i = 3; i < data.length(); i++) {
            if(data.charAt(i) == '[') {
                layersDeep = layersDeep + 1;
            }
            else if(data.charAt(i) == ']') {
                layersDeep = layersDeep - 1;
            }
            else if((data.charAt(i) == ',') && (layersDeep < 2)) {
                lines[linesIndex] = data.substring(lastRecordedEnter, i + 1);
                lastRecordedEnter = i + 1;
                
            }
        }
        return lines;
    }

    
    public String jsonToJava() {

    }

    /**
    * javaToJson
    * converts the project class into JSON
    * @return the project class as a JSON string
    */
    public String javaToJson() {
        System.out.println("DIGRAMS : " + this.diagrams);
        String data = "{" + "\"ProjectName\":\""  + this.projectName + "\",";
        data = data + "\"diagramIdCount\":"  + this.diagramIdCount + ",";
        data = data + "\"arrowIdCount\":"  + this.arrowIdCount + ",";
        data = data + "\"classDiagrams\":[ ";
        for(int i = 0; i < diagramIdCount; i++) {
            if(this.diagrams.get(i) instanceof ClassDiagram) {
                data = data + ((ClassDiagram)this.diagrams.get(i)).toJson();
                data = data + ",";
            }
        }
        data = data.substring(0, data.length() - 1);
        data = data + "],";
        data = data + "\"interfaceDiagrams\":[ ";
        for(int i = 0; i < diagramIdCount; i++) {
            if(this.diagrams.get(i) instanceof InterfaceDiagram) {
                data = data + ((InterfaceDiagram)this.diagrams.get(i)).toJson();
                data = data + ",";
            }
        }
        data = data.substring(0, data.length() - 1);
        data = data + "],";
        data = data + "\"exceptionDiagrams\":[ ";
        for(int i = 0; i < diagramIdCount; i++) {
            if(this.diagrams.get(i) instanceof ExceptionDiagram) {
                data = data + "{" + ((ExceptionDiagram)this.diagrams.get(i)).toJson();
                data = data + ",";
            }
        }
        data = data.substring(0, data.length() - 1);
        data = data + "],";
        data = data + "\"arrows\":[ ";
        for(int i = 0; i < arrowIdCount; i++) {
            data = data + this.arrows.get(i).toJson();
            data = data + ",";
        }
        data = data.substring(0, data.length() - 1);
        data = data + "]}";

        System.out.println(data);
        return data;
        
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
        System.out.println("ADDING " + diagram);
        diagrams.put(diagram.getId(), diagram);
        System.out.println(this.diagrams);
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
        for(int i = 0; i < diagramIdCount + 1; i++) {
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
    public void addArrow(int id, Arrow arrow) {
        arrows.put(id, arrow);
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
        for(int i = 0; i < arrowIdCount + 1; i++) {
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

    /**
     * getDiagramIdCount
     * gets the highest diagram id
     * @return the highest diagram id
     */
    public int getDiagramIdCount() {
        return this.diagramIdCount;
    }

    /**
     * getArrowIdCount
     * gets the highest arrow id
     * @return the highest arrow id
     */
    public int getArrowIdCount() {
        return this.arrowIdCount;
    }
    
    /**
     * setDiagramIdCount
     * updates the diagram id count
     * @param count the new diagram id count
     */
    public void setDiagramIdCount(int count) {
        this.diagramIdCount = count;
    }

    /**
     * setArrowIdCount
     * updates the arrow id count
     * @param count the new arrow id count
     */
    public void setArrowIdCount(int count) {
        this.arrowIdCount = count;
    }

    /**
     * updateDiagram
     * updates a diagram with a new diagram
     * @param id the id of the diagram you want to update
     * @param diagram the diagram you want to replace with
     */
    public void updateDiagram(int id, Diagram diagram) {
        diagrams.put(id, diagram);
    }

    /**
     * updateArrow
     * updates an arrow with a new arrow
     * @param id the id of the arrow you want to update
     * @param arrow the arrow you want to replace with
     */
    public void updateArrow(int id, Arrow arrow) {
        arrows.put(id, arrow);
    }
}
