package entities;
import java.io.File;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.PrintWriter;

public class toJava {
    public static void toJava(Project project){
        //initialize the new folder
        String path = "C:\\Users\\patri\\Documents\\PeilinPlanner\\PeilinPlanner\\"+project.getProjectName();
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //process the list of objects in project to seperate into diagrams and arrows
        LinkedList<Object> allObjects = project.getAllObjects();
        LinkedList<Diagram> classes = new LinkedList<Diagram>();
        // LinkedList<Field> a = new LinkedList<Field>();
        // LinkedList<Method> b = new LinkedList<Method>();
        // boolean flag = true;
        // ClassDiagram c = new ClassDiagram("name", flag ,a, b, 1, 2, 1, 2);
        // classes.add(c);
        LinkedList<Arrow> arrows = new LinkedList<Arrow>();
        HashMap<Object, LinkedList<Object>> links = new HashMap<Object, LinkedList<Object>>();
        for(Object UMLObject : allObjects){
            if((UMLObject instanceof Diagram)){
                classes.add(((Diagram)UMLObject));
                links.put(((Diagram)UMLObject), new LinkedList<Object>());
            }
            else {
                //add to arrows temporarily
                if(UMLObject instanceof Arrow){
                    arrows.add(((Arrow) UMLObject));
                }
            }
        }

        for(Arrow newArrow : arrows){
            //get the class its pointing to, and then add it to links
        }

        //for every file, create a new file and then print the code in
        for(Diagram newObject : classes){
            if(newObject instanceof ClassDiagram){
                File javaFile = new File(path + "\\" + newObject.getName()+".java");
                String code = "";
                //check for any imports using links
                if(((ClassDiagram)newObject).isAbstract() == true){
                    code = code + "public abstract class " + ((ClassDiagram)newObject).getName();
                    //do all of the inheritance and interfaces here

                    code = code + " {\n";
                    for(((ClassDiagram)newObject).getFields()){
                        //do all of the variables here
                    }

                    for(((ClassDiagram)newObject).getMethods()){
                        //do all of the variables here
                    }
                    code = code + "}";
                }
                else {
                    code = code + "public class " + ((ClassDiagram)object).getName() + " {\n";

                    code = code + "}";
                }
                
                
                code = code + "}";
            }
            else if (newObject instanceof ExceptionDiagram){
                String code = "";
                //check for any imports, inheritance, interfaces using links
            }
            else if (newObject instanceof InterfaceDiagram){
                String code = "";
                //check for any imports, inheritance, interfaces using links
            }
            else {
                System.out.println("bad class - error in toJava");
            }
        }
    } 
}
