/**
 * [ProjectBase.java]
 * Class representing all created projects
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package entities;

import java.util.HashMap;

public class ProjectBase {
    private HashMap<String, Project> projects;

    /**
     * ProjectBase
     * empty constructor
     */
    public ProjectBase(){
        projects = new HashMap<String, Project>();
    }

    /**
     * addProject
     * @param project new project being added
     */
    public void addProject(Project project){
        projects.put(project.getProjectName(), project);
    }
    
    /**
     * getProject
     * gets project by name of the project
     * @param name name of the project
     * @return the project structure
     */
    public Project getProject(String name){
        return projects.get(name);
    }

    //needs lockproject feature so multiple users dont conflict on the same one 
    //needs to be threaded
}
