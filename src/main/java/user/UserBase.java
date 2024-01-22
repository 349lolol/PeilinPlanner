/**
 * [UserBase.java]
 * Class representing all created users
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package user;

import java.util.HashMap;
import java.util.LinkedList;
import entities.Project;
import java.io.Serializable;

public class UserBase implements Serializable {
    private HashMap<String, User> userBase;

    public UserBase(HashMap<String, User> userBase) {
        this.userBase = new HashMap<String, User>();
    }

    public User getUser(String username) {
        return this.userBase.get(username);
    }

    public boolean addUser(String username, String password) {
        if(this.userBase.get(username) == null) {
            this.userBase.put(username, new User(username, password));
            return true;
        }
        else {
            return false;
        }
        
    }

    public boolean verifyUser(String username, String password) {
        if (this.userBase.get(username) == null) {
            return false;
        }
        return (password.equals(this.userBase.get(username).getPassword()));
    }

    public boolean addToSharedProject(String username, Project project) {
        return userBase.get(username).addSharedProject(project.getProjectName());
    }

    public boolean addToOwnedProjects(String username, Project project) {
        return userBase.get(username).createProject(project.getProjectName());
    }

    public LinkedList<String> getSharedProjects(String username) {
        return userBase.get(username).getSharedProjects();
    }

    public LinkedList<String> getSOwnedProjects(String username) {
        return userBase.get(username).getSharedProjects();
    }
}
