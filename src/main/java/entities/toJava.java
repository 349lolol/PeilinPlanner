/**
 * [ToJava.java]
 * Class that converts a uml to a workable java project
 * For file IO, which doesn't work
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.HashMap;
import java.io.PrintWriter;

public class toJava {
    
    /**
     * PrintToJava
     * Converts an entire uml project to a workable folder with 1 file per class object
     *  */ 
    public static void printToJava(Project project) throws FileNotFoundException{
        //initialize the new folder
        String path = "./" + project.getProjectName();
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //process the list of objects in project to seperate into diagrams and arrows
        LinkedList<Diagram> classes = project.getAllDiagrams();
        LinkedList<Arrow> arrows = project.getAllArrows();
        HashMap<Object, LinkedList<Object>> links = new HashMap<Object, LinkedList<Object>>();

        for(int i = 0; i < arrows.size(); i++) {
            //get the class its pointing to, and then add it to links
            Arrow arrow = arrows.get(i);
            links.get(arrow.getDestination()).add(arrow);
        }

        //for every file, create a new file and then print the code in
        for(int i = 0; i < classes.size(); i++) {
            Diagram newObject = classes.get(i);
            String code = "";
            //add printwriter and filewriter here
            if(newObject instanceof ClassDiagram) { 
                
                //check for any imports using links
                if(((ClassDiagram)newObject).isAbstract() == true) {
                    code = code + "public abstract class " + ((ClassDiagram)newObject).getName();
                    
                }
                else {
                    code = code + "public class " + ((ClassDiagram)newObject).getName() + " ";
                }
                for(int j = 0; j < arrows.size(); j++) {
                    if(arrows.get(j).getArrowType().equals("INHERIT")) {
                        code = code + " inherits " + arrows.get(j).getOrigin() + " ";
                    }
                    else if(arrows.get(j).getArrowType().equals("INTERFACE")) {
                        code = code + " implements " + arrows.get(j).getOrigin() + " ";
                    }
                }
                code = code + " {\n";
                for(int j = 0; j < ((ClassDiagram)newObject).getFields().size(); j++) {
                    code = code + "    " + ((ClassDiagram)newObject).getFields().get(i).toJava();
                }
                code = code + "\n";
                for(int j = 0; j < ((ClassDiagram)newObject).getMethods().size(); j++) {
                    code = code + "    " + ((ClassDiagram)newObject).getMethods().get(i).toJava();

                }
                code = code + "}";
            }
            else if (newObject instanceof ExceptionDiagram) {
                code = code + "public class " + ((ExceptionDiagram)newObject).getName() + " extends Exception {\n";
                code = code + "    public " + ((ExceptionDiagram)newObject).getName() + "(String errorMessage) {\n";
                code = code + "        super(errorMessage);\n    }\n}\n";
            }
            else if (newObject instanceof InterfaceDiagram) {
                //check for any imports, inheritance, interfaces using links
                code = code + "public interface " + ((InterfaceDiagram)newObject).getName() + " {\n";
                for(int j = 0; j < ((InterfaceDiagram)newObject).getMethods().size(); j++) {
                    code = code + "    " + ((InterfaceDiagram)newObject).getMethods().get(i).toJava();
                    code = code + "}";
                }
            }
            else {
                System.out.println("bad class - error in toJava");
            }
            File javaFile = new File(path + "\\" + newObject.getName()+".java"); 
            PrintWriter printWriter = new PrintWriter(javaFile);
            printWriter.println(code);
            printWriter.close(); 
        }
    } 
}
