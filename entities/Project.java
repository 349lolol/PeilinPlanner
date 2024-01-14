package entities;

import java.util.LinkedList;
import java.util.HashMap;

public class Project {
    private final String projectName;
    private int objectIdCount;
    private final HashMap<Integer, Object> objects;

    /**
     * toString
     * constructs XML structure for a UML project
     */
    @Override
    public String toString(){
        String data = "<name= " + objectIdCount +">" + projectName + "</name>\n";
        data = data + "<objects>\n";
        for(int index = 0; index < objectIdCount; index++){
            if(objects.get(index) != null){
                //object has not been deleted by user, add to list
                data = data + "<object id:" + index + ">\n";
                data = data + objects.get(index).toString();
                data = data + "</object>\n";
            }
        }
        data = data + "</objects>\n";
        return data;
    }

    /**
     * fromString
     * deparses XML structure into java project structure
     * @param data data from string
     */
    public void fromString(String data){
        String[] lines = data.split("\n");
        //level 1 - project
        //level 2 - project details
        //level 2 - header of object
        //level 3 - index value information (object)
        //level 4 - lists of parameters of object
        
    }

    Project(String projectName){
        this.objectIdCount = 0;
        this.projectName = projectName;
        objects = new HashMap<Integer, Object>();
    }

    public void addObject(Object object){   
        objects.put(objectIdCount, object);
        objectIdCount++;
    }

    public Object getObject(int id){
        return objects.get(id);
    }

    public LinkedList<Object> getAllObjects(){
        LinkedList<Object> allObjects = new LinkedList<Object>();
        for(int i = 0; i < objectIdCount; i++){
            if(getObject(i) != null){
                allObjects.add(getObject(i));
            }
        }
        return allObjects;
    }

    public String getProjectName(){
        return this.projectName;
    }

    
}
