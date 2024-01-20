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
    private HashMap<String, Boolean> isInUse;

    /**
     * ProjectBase
     * empty constructor
     */
    public ProjectBase() {
        projects = new HashMap<String, Project>();
        isInUse = new HashMap<String, Boolean>();
    }

    /**
     * addProject
     * @param project new project being added
     */
    public void addProject(Project project) {
        projects.put(project.getProjectName(), project);
        isInUse.put(project.getProjectName(), false);
    }
    
    /**
     * getProject
     * gets project by name of the project
     * @param name name of the project
     * @return the project structure
     */
    public Project getProject(String name) {
        if(isInUse.get(name) == false){
            isInUse.put(name, true);
            return projects.get(name);
        }
        return null;
    }

    /**
     * returnProject
     * allows someone else to access the project
     * @param name name of project
     */
    public void returnProject(String name) {
        isInUse.put(name, false);

    }

    //needs lockproject feature so multiple users dont conflict on the same one 
    //needs to be threaded
}
