package user;

import java.util.LinkedList;

import entities.Arrow;
import entities.ClassDiagram;
import entities.Diagram;
import entities.ExceptionDiagram;
import entities.Field;
import entities.InterfaceDiagram;
import entities.Method;
import entities.Project;
import entities.ProjectBase;


public class User {
    private final String username;
    private final String password;
    private final LinkedList<Project> sharedProjects;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        sharedProjects = new LinkedList<Project>();
    }

    public String getUserName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public LinkedList<Project> getSharedProjects() {
        return this.sharedProjects;
    }

    public boolean addProject(Project project) {
        return sharedProjects.add(project);
    }
}
