package user;

import java.util.LinkedList;
import java.util.HashSet;

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
    private final HashSet<String> ownedProjects;
    private final LinkedList<String> sharedProjects;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        ownedProjects = new HashSet<String>();
        sharedProjects = new LinkedList<String>();
    }

    public String getUserName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public LinkedList<String> getSharedProjects() {
        return this.sharedProjects;
    }

    public HashSet<String> getOwnedProjects() {
        return this.ownedProjects;
    }

    public boolean addSharedProject(String project) {
        return sharedProjects.add(project);
    }

    public boolean createProject(String project) {
        return ownedProjects.add(project);
    }
}
