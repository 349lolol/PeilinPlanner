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

    /**
     * Userbase
     * @param userBase pre existing user base
     */
    public UserBase(HashMap<String, User> userBase) {
        this.userBase = new HashMap<String, User>();
    }

    /**
     * getUser
     * get used based on username search method
     * @param username
     * @return
     */
    public User getUser(String username) {
        return this.userBase.get(username);
    }

    /**
     * addUser 
     * adds a user to the user base
     * @param username unique username
     * @param password password
     * @return
     */
    public boolean addUser(String username, String password) {
        if(this.userBase.get(username) == null) {
            this.userBase.put(username, new User(username, password));
            return true;
        }
        else {
            return false;
        }
        
    }

    /**
     * verify User
     * @param username entered username
     * @param password entered password
     * @return if logins are valid or not 
     */

    public boolean verifyUser(String username, String password) {
        if (this.userBase.get(username) == null) {
            return false;
        }
        return (password.equals(this.userBase.get(username).getPassword()));
    }

    /**
     * addToSharedProject
     * @param username target user
     * @param project added project
     * @return
     */
    public boolean addToSharedProject(String username, Project project) {
        return userBase.get(username).addSharedProject(project.getProjectName());
    }

    /**
     * addToOwnedProject
     * @param username target user
     * @param project added project
     * @return
     */
    public boolean addToOwnedProjects(String username, Project project) {
        return userBase.get(username).createProject(project.getProjectName());
    }

    /**
     * getSharedProjects
     * @param username target user
     * @return list of projects shared to them
     */
    public LinkedList<String> getSharedProjects(String username) {
        return userBase.get(username).getSharedProjects();
    }

    /**
     * getOwnedProjects
     * @param username target user
     * @return list of projects owned by them
     */
    public LinkedList<String> getSOwnedProjects(String username) {
        return userBase.get(username).getSharedProjects();
    }
}
