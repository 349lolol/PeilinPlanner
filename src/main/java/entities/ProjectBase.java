/**
 * [ProjectBase.java]
 * Class representing all created projects
 * @author Perry Xu & Patrick Wei
 * @version 1.2
 * 01/29/24
 */

package entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class ProjectBase implements Serializable {
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
     * getProjects
     * @return all projects from projectBase
     */
    public HashMap<String, Project> getProjects() {
        return projects;
    }
    /**
     * addProject
     * @param project new project being added
     */
    public String addProject(Project project) {
        projects.putIfAbsent(project.getProjectName(), project);
        isInUse.put(project.getProjectName(), false);
        return "{\"ProjectName\":" + "\"" + project.getProjectName() +  "\"" + ", " +
            "\"diagramIdCount\": 0, " +
            "\"arrowIdCount\": 0, " +
            "\"classDiagrams\": [], " +
            "\"interfaceDiagrams\": [], " +
            "\"exceptionDiagrams\": [], " +
            "\"arrows\": []}";
    }
    
    /**
     * addProject
     * adds project to the system
     * @param projectName name of the project
     * @return
     */
    public String addProject(String projectName) {
        projects.putIfAbsent(projectName, new Project(projectName));
        isInUse.put(projectName, false);
        return "{\"ProjectName\":" + "\"" + projectName +  "\"" + ", " +
            "\"diagramIdCount\": 0, " +
            "\"arrowIdCount\": 0, " +
            "\"classDiagrams\": [], " +
            "\"interfaceDiagrams\": [], " +
            "\"exceptionDiagrams\": [], " +
            "\"arrows\": []}";
    }

    /**
     * getProject
     * gets project by name of the project
     * @param name name of the project
     * @return the project structure
     */
    public Project getProject(String name) {
        if(isInUse.containsKey(name)){
            isInUse.put(name, true);
            return projects.get(name);
        }
        return null;
    }

    /**
     * setProject
     * sets a project to a new project
     * @param name the name of the old project
     * @param project the new project
     */
    public void setProject(String name, Project project) {
        projects.put(name, project);
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

    @Override
    public String toString() {
        return this.projects.toString() + " | " + this.isInUse.toString();
    }

    /**
     * getAllKeys
     * gets all project keys
     * @return all project keys
     */
    public Set<String> getAllKeys() {
        return projects.keySet();
    }
}
