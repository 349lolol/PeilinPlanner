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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;

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

    public Project(String name) {
        this.diagramIdCount = 0;
        this.projectName = name;
        diagrams = new HashMap<Integer, Diagram>();
        this.arrowIdCount = 0;
        arrows = new HashMap<Integer, Arrow>();
    }

    public String[] splitUML(String data) {
        System.out.println("\n\n\n\n");
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
                System.out.println(lines[linesIndex]);
                
            }
        }
        //
        //bug test
        
        System.out.println("\n\n\n");
        for(int i = 0; i < lines.length; i++) {
            System.out.println(lines[i]);
        }
        return lines;
    }

    public boolean isInteger(String numString) {
        try {
            Integer.parseInt(numString);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * JsonToJava
     * creates a complete new project based on json data
     * @param data the json string being converted
     */
    public void JsonToJava(String data) {
        System.out.println(data);
        
        // data = data.replace("\\", "");
        // String[] lines = data.split("##\",");
        // for (int i = 0; i < lines.length; i++) {
        //     lines[i] = lines[i] + "\"";

        //     String value = lines[i].split(":", 2)[1];
        //     if ((isInteger(value.substring(1, value.length() - 1))) || (value.substring(1, value.length() - 1).startsWith("["))) {
        //         lines[i] = lines[i].split(":")[0] + ":" + value.substring(1, value.length() - 1);
        //     }
        // }

        // // fixes the last line
        // lines[0] = lines[0].substring(1);
        // lines[lines.length - 1] = lines[lines.length - 1].replace("##\"}", "");

        // System.out.println("\n\n\n");
        // for (String line : lines) {
        //     System.out.println(line);
        // }

        // this.projectName = lines[0].split("\":\"")[1];
        // this.projectName = this.projectName.substring(0, this.projectName.length()-1);
        // String diagramCountString = lines[1].split("\":")[1];
        // this.diagramIdCount = Integer.parseInt(diagramCountString);
        // String arrowCountString = lines[2].split("\":")[1];
        // this.arrowIdCount = Integer.parseInt(arrowCountString);

        // //line 2 is classes
        // //line 3 is interfaces
        // //line 4 is exceptions
        // //line 5 is arrows

        // //classes
        // {
        //     int layersDeep = 0;
        //     int objectCount = 0;
        //     for(int i = 0; i < lines[3].length(); i++) {
        //         if(lines[3].charAt(i) == '{') {
        //             layersDeep = layersDeep + 1;
        //         }
        //         else if(lines[3].charAt(i) == '}') {
        //             layersDeep = layersDeep - 1;
        //         }
        //         else if((layersDeep == 0) && (lines[3].charAt(i) == ',')) {
        //             objectCount++;
        //         }
        //     }

        //     //split the line into classes
        //     String[] classesList = new String[objectCount];
        //     int lastIndex = 2;
        //     int currentClassIndex = 0;

        //     for(int i = 0; i < lines[3].length(); i++) {
        //         if(lines[3].charAt(i) == '{') {
        //             layersDeep = layersDeep + 1;
        //         }
        //         else if(lines[3].charAt(i) == '}') {
        //             layersDeep = layersDeep - 1;
        //             if(layersDeep == 0) {
        //                 classesList[currentClassIndex] = lines[3].substring(lastIndex, i);
        //                 currentClassIndex = currentClassIndex + 1;
        //                 lastIndex = i + 1;
        //             }
        //         }
        //     }

        //     //split each class into each individual line in loop, process, and add
            
            
        //     for(int i = 0; i < classesList.length; i++) {
        //         ArrayList<String> classParameters = new ArrayList<String>();
        //         layersDeep = 0;
        //         lastIndex = 1;
        //         for(int j = 0; j < classesList[i].length(); j++) {
        //             if(classesList[i].charAt(j) == '[') {
        //                 layersDeep = layersDeep + 1;
        //             }
        //             else if(classesList[i].charAt(j) == ']') {
        //                 layersDeep = layersDeep - 1;
        //             }
        //             else if((layersDeep == 0) && (classesList[i].charAt(j) == ',')) {
        //                 classParameters.add(classesList[i].substring(lastIndex, j));
        //                 lastIndex = j + 2;
        //             }
        //         }
        //         int classId = Integer.parseInt(classParameters.get(0).split("\":")[1]);
        //         String className = classParameters.get(1).split("\":")[1];
        //         int xPosition = Integer.parseInt(classParameters.get(2).split("\":")[1]);
        //         int yPosition = Integer.parseInt(classParameters.get(3).split("\":")[1]);
        //         int xSize = Integer.parseInt(classParameters.get(4).split("\":")[1]);
        //         int ySize = Integer.parseInt(classParameters.get(5).split("\":")[1]);
        //         boolean isAbstract = classParameters.get(6).split("\":")[1].equals("true");
                
        //         String fieldLine = classParameters.get(7).split("\":")[1]; //could cause issues with cutting off early
        //         fieldLine = fieldLine.substring(1, fieldLine.length() - 1); //isolated down to "xxx","yyy","zzz"
        //         String[] fieldArray = fieldLine.split("\",\"");
        //         fieldArray[0].substring(1);
        //         fieldArray[fieldArray.length - 1].substring(0, fieldArray[fieldArray.length - 1].length() - 1);
        //         LinkedList<Field> fields = new LinkedList<Field>();
        //         for(int j = 0; j < fieldArray.length; j++) {
        //             fields.add(new Field(fieldArray[j]));
        //         }
                
        //         String methodLine = classParameters.get(8).split("\":")[1]; //could cause issues with cutting off early
        //         methodLine = methodLine.substring(1, methodLine.length() - 1); //isolated down to "xxx","yyy","zzz"
        //         String[] methodArray = methodLine.split("\",\"");
        //         methodArray[0].substring(1);
        //         methodArray[methodArray.length - 1].substring(0, methodArray[methodArray.length - 1].length() - 1);
        //         LinkedList<Method> methods = new LinkedList<Method>();
        //         for(int j = 0; j < methodArray.length; j++) {
        //             methods.add(new Method(methodArray[j]));
        //         }
        //         diagrams.put(classId, new ClassDiagram(className, isAbstract, fields, methods, xPosition, yPosition, xSize, ySize));
        //     }
        // }


        // //interfaces
        // {
        //     int layersDeep = 0;
        //     int objectCount = 0;
        //     for(int i = 0; i < lines[4].length(); i++) {
        //         if(lines[4].charAt(i) == '{') {
        //             layersDeep = layersDeep + 1;
        //         }
        //         else if(lines[4].charAt(i) == '}') {
        //             layersDeep = layersDeep - 1;
        //         }
        //         else if((layersDeep == 0) && (lines[4].charAt(i) == ',')) {
        //             objectCount++;
        //         }
        //     }

        //     //split the line into classes
        //     String[] interfacesList = new String[objectCount];
        //     int lastIndex = 2;
        //     int currentClassIndex = 0;

        //     for(int i = 0; i < lines[4].length(); i++) {
        //         if(lines[4].charAt(i) == '{') {
        //             layersDeep = layersDeep + 1;
        //         }
        //         else if(lines[4].charAt(i) == '}') {
        //             layersDeep = layersDeep - 1;
        //             if(layersDeep == 0) {
        //                 interfacesList[currentClassIndex] = lines[4].substring(lastIndex, i);
        //                 currentClassIndex = currentClassIndex + 1;
        //                 lastIndex = i + 1;
        //             }
        //         }
        //     }

        //     for(int i = 0; i < interfacesList.length; i++) {
        //         ArrayList<String> classParameters = new ArrayList<String>();
        //         layersDeep = 0;
        //         lastIndex = 1;
        //         for(int j = 0; j < interfacesList[i].length(); j++) {
        //             if(interfacesList[i].charAt(j) == '[') {
        //                 layersDeep = layersDeep + 1;
        //             }
        //             else if(interfacesList[i].charAt(j) == ']') {
        //                 layersDeep = layersDeep - 1;
        //             }
        //             else if((layersDeep == 0) && (interfacesList[i].charAt(j) == ',')) {
        //                 classParameters.add(interfacesList[i].substring(lastIndex, j));
        //                 lastIndex = j + 2;
        //             }
        //         }
        //         int classId = Integer.parseInt(classParameters.get(0).split("\":")[1]);
        //         String className = classParameters.get(1).split("\":")[1];
        //         int xPosition = Integer.parseInt(classParameters.get(2).split("\":")[1]);
        //         int yPosition = Integer.parseInt(classParameters.get(3).split("\":")[1]);
        //         int xSize = Integer.parseInt(classParameters.get(4).split("\":")[1]);
        //         int ySize = Integer.parseInt(classParameters.get(5).split("\":")[1]);
                
        //         String methodLine = classParameters.get(6).split("\":")[1]; //could cause issues with cutting off early
        //         methodLine = methodLine.substring(1, methodLine.length() - 1); //isolated down to "xxx","yyy","zzz"
        //         String[] methodArray = methodLine.split("\",\"");
        //         methodArray[0].substring(1);
        //         methodArray[methodArray.length - 1].substring(0, methodArray[methodArray.length - 1].length() - 1);
        //         LinkedList<Method> methods = new LinkedList<Method>();
        //         for(int j = 0; j < methodArray.length; j++) {
        //             methods.add(new Method(methodArray[j]));
        //         }
        //         diagrams.put(classId, new InterfaceDiagram(className, methods, xPosition, yPosition, xSize, ySize));
        //     }
        // }

        // //exceptions
        // {
        //     int layersDeep = 0;
        //     int objectCount = 0;
        //     for(int i = 0; i < lines[5].length(); i++) {
        //         if(lines[5].charAt(i) == '{') {
        //             layersDeep = layersDeep + 1;
        //         }
        //         else if(lines[5].charAt(i) == '}') {
        //             layersDeep = layersDeep - 1;
        //         }
        //         else if((layersDeep == 0) && (lines[5].charAt(i) == ',')) {
        //             objectCount++;
        //         }
        //     }

        //     //split the line into classes
        //     String[] exceptionsList = new String[objectCount];
        //     int lastIndex = 2;
        //     int currentClassIndex = 0;

        //     for(int i = 0; i < lines[5].length(); i++) {
        //         if(lines[5].charAt(i) == '{') {
        //             layersDeep = layersDeep + 1;
        //         }
        //         else if(lines[5].charAt(i) == '}') {
        //             layersDeep = layersDeep - 1;
        //             if(layersDeep == 0) {
        //                 exceptionsList[currentClassIndex] = lines[5].substring(lastIndex, i);
        //                 currentClassIndex = currentClassIndex + 1;
        //                 lastIndex = i + 1;
        //             }
        //         }
        //     }

        //     for(int i = 0; i < exceptionsList.length; i++) {
        //         ArrayList<String> classParameters = new ArrayList<String>();
        //         layersDeep = 0;
        //         lastIndex = 1;
        //         for(int j = 0; j < exceptionsList[i].length(); j++) {
        //             if(exceptionsList[i].charAt(j) == '[') {
        //                 layersDeep = layersDeep + 1;
        //             }
        //             else if(exceptionsList[i].charAt(j) == ']') {
        //                 layersDeep = layersDeep - 1;
        //             }
        //             else if((layersDeep == 0) && (exceptionsList[i].charAt(j) == ',')) {
        //                 classParameters.add(exceptionsList[i].substring(lastIndex, j));
        //                 lastIndex = j + 2;
        //             }
        //         }
        //         int classId = Integer.parseInt(classParameters.get(0).split("\":")[1]);
        //         String className = classParameters.get(1).split("\":")[1];
        //         int xPosition = Integer.parseInt(classParameters.get(2).split("\":")[1]);
        //         int yPosition = Integer.parseInt(classParameters.get(3).split("\":")[1]);
        //         int xSize = Integer.parseInt(classParameters.get(4).split("\":")[1]);
        //         int ySize = Integer.parseInt(classParameters.get(5).split("\":")[1]);
    
        //         diagrams.put(classId, new ExceptionDiagram(className, xPosition, yPosition, xSize, ySize));
        //     }
        // }
        
        // //arrows
        // {
        //     int layersDeep = 0;
        //     int objectCount = 0;
        //     for(int i = 0; i < lines[6].length(); i++) {
        //         if(lines[6].charAt(i) == '{') {
        //             layersDeep = layersDeep + 1;
        //         }
        //         else if(lines[6].charAt(i) == '}') {
        //             layersDeep = layersDeep - 1;
        //         }
        //         else if((layersDeep == 0) && (lines[6].charAt(i) == ',')) {
        //             objectCount++;
        //         }
        //     }

        //     //split the line into classes
        //     String[] arrowsList = new String[objectCount];
        //     int lastIndex = 2;
        //     int currentClassIndex = 0;

        //     for(int i = 0; i < lines[6].length(); i++) {
        //         if(lines[6].charAt(i) == '{') {
        //             layersDeep = layersDeep + 1;
        //         }
        //         else if(lines[6].charAt(i) == '}') {
        //             layersDeep = layersDeep - 1;
        //             if(layersDeep == 0) {
        //                 arrowsList[currentClassIndex] = lines[6].substring(lastIndex, i);
        //                 currentClassIndex = currentClassIndex + 1;
        //                 lastIndex = i + 1;
        //             }
        //         }
        //     }

        //     for(int i = 0; i < arrowsList.length; i++) {
        //         ArrayList<String> classParameters = new ArrayList<String>();
        //         layersDeep = 0;
        //         lastIndex = 1;
        //         for(int j = 0; j < arrowsList[i].length(); j++) {
        //             if(arrowsList[i].charAt(j) == '[') {
        //                 layersDeep = layersDeep + 1;
        //             }
        //             else if(arrowsList[i].charAt(j) == ']') {
        //                 layersDeep = layersDeep - 1;
        //             }
        //             else if((layersDeep == 0) && (arrowsList[i].charAt(j) == ',')) {
        //                 classParameters.add(arrowsList[i].substring(lastIndex, j));
        //                 lastIndex = j + 2;
        //             }
        //         }
        //         String origin = classParameters.get(0).split("\":")[1];
        //         String destination = classParameters.get(1).split("\":")[1];
        //         String arrowType =  classParameters.get(2).split("\":")[1];

        //         String xPointsString = classParameters.get(3).split("\":")[1];
        //         xPointsString.substring(1, xPointsString.length() - 1);
        //         String[] xPointsArray = xPointsString.split(",");
        //         ArrayList<Integer> xPoints = new ArrayList<Integer>();
        //         for(int j = 0; j < xPointsArray.length; j++) {
        //             xPoints.add(Integer.parseInt(xPointsArray[j]));
        //         }

        //         String yPointsString = classParameters.get(3).split("\":")[1];
        //         yPointsString.substring(1, yPointsString.length() - 1);
        //         String[] yPointsArray = yPointsString.split(",");
        //         ArrayList<Integer> yPoints = new ArrayList<Integer>();
        //         for(int j = 0; j < yPointsArray.length; j++) {
        //             yPoints.add(Integer.parseInt(yPointsArray[j]));
        //         }
        //         arrows.put(i, new Arrow(this.getDiagram(origin), this.getDiagram(destination), arrowType, xPoints, yPoints));
        //     }
        // }
    }

    public String javaToJson() {
        String data = "{" + "\"ProjectName\": \""  + this.projectName + "\", ";
        data = data + "\"diagramIdCount\": "  + this.diagramIdCount + ", ";
        data = data + "\"arrowIdCount\": "  + this.arrowIdCount + ", ";
        data = data + "\"classDiagrams\": [";
        for(int i = 0; i < diagramIdCount; i++) {
            if(diagrams.get(i) instanceof ClassDiagram) {
                data = data + ((ClassDiagram)diagrams.get(i)).toJson() + ", ";
            }
        }
        data = data.substring(0, data.length());
        data = data + "], ";
        data = data + "\"interfaceDiagrams\": [";
        for(int i = 0; i < diagramIdCount; i++) {
            if(diagrams.get(i) instanceof InterfaceDiagram) {
                data = data + ((InterfaceDiagram)diagrams.get(i)).toJson() + ", ";
            }
        }
        data = data.substring(0, data.length());
        data = data + "], ";
        data = data + "\"exceptionDiagrams\": [";
        for(int i = 0; i < diagramIdCount; i++) {
            if(diagrams.get(i) instanceof ExceptionDiagram) {
                data = data + ((ExceptionDiagram)diagrams.get(i)).toJson() + ", ";
            }
        }
        data = data.substring(0, data.length());
        data = data + "], ";
        data = data + "\"arrows\": [";
        for(int i = 0; i < arrowIdCount; i++) {
            data = data + arrows.get(i).toJson() + ", ";
        }
        data = data.substring(0, data.length());
        data = data + "]}";
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

    public int getDiagramIdCount() {
        return this.diagramIdCount;
    }

    public int getArrowIdCount() {
        return this.arrowIdCount;
    }
    
    public void setDiagramIdCount(int count) {
        this.diagramIdCount = count;
    }

    public void setArrowIdCount(int count) {
        this.arrowIdCount = count;
    }

    public void updateDiagram(int id, Diagram diagram) {
        diagrams.put(id, diagram);
    }

    public void updateArrow(int id, Arrow arrow) {
        arrows.put(id, arrow);
    }
}
