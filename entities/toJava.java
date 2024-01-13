package entities;
import java.io.File;
import java.util.LinkedList;
import java.util.HashMap;
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
        LinkedList<Object> classes = new LinkedList<Object>();
        LinkedList<Object> arrows = new LinkedList<Object>();
        HashMap<Object, LinkedList<Object>> links = new  HashMap<Object, LinkedList<Object>>();
        for(Object object : allObjects){
            if((object instanceof ClassDiagram) || (object instanceof ExceptionDiagram) || (object instanceof InterfaceDiagram)){
                classes.add(object);
                links.put(object, new LinkedList<Object> ());
            }
            else {
                //add to arrows temporarily
            }
        }

        for(Object object : arrows){
            //get the class its pointing to, and then add it to links
        }

        //for every file, create a new file and then print the code in
        for(Object newClass : classes){
            
            if(newClass instanceof ClassDiagram){
                File javaFile = new File(path + "\\" + ((ClassDiagram)newClass).getName()+".java");
                String code = "";
                //check for any imports using links
                if(((ClassDiagram)object).isAbstract() == true){
                    code = code + "public abstract class " + ((ClassDiagram)newClass).getName();
                    //do all of the inheritance and interfaces here
                    code = code + " {\n";
                    for(((ClassDiagram)newClass).getFields()){
                        //do all of the variables here
                    }

                    for(((ClassDiagram)newClass).getMethods()){
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
            else if (object instanceof ExceptionDiagram){
                String code = "";
                //check for any imports, inheritance, interfaces using links
            }
            else if (object instanceof InterfaceDiagram){
                String code = "";
                //check for any imports, inheritance, interfaces using links
            }
            else {
                System.out.println("bad class - error in toJava");
            }
        }
    } 
}
