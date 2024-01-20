package entities;
import java.util.HashMap;

public class ProjectBase {
    private HashMap<String, Project> projects;

    public ProjectBase(){
        projects = new HashMap<String, Project>();
    }

    public void addProject(Project project){
        projects.put(project.getProjectName(), project);
    }
    
    public Project getProject(String name){
        return projects.get(name);
    }

    //needs lockproject feature so multiple users dont conflict on the same one 
}
