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
     * userBase
     * creates a user base with pre existing user
     * @param userBase pre existing hashmap
     */
    public UserBase(HashMap<String, User> userBase) {
        this.userBase = new HashMap<String, User>();
    }

    /**
     * getUser
     * @param username username of user
     * @return user class
     */
    public User getUser(String username) {
        return this.userBase.get(username);
    }

    /**
     * addUser
     * adds a user to the system
     * @param username username of new user
     * @param password password of new user
     * @return if operation was succesful (will fail if username already used)
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
     * verify user
     * verifies if a user exists based on username, password
     * @param username username
     * @param password password
     * @return
     */
    public boolean verifyUser(String username, String password) {
        if (this.userBase.get(username) == null) {
            return false;
        }
        return (password.equals(this.userBase.get(username).getPassword()));
    }

    /**
     * addSharedProject
     * shares a project with the user
     * @param username target user
     * @param project project being shared
     * @return if operation was successful
     */
    public boolean addToSharedProject(String username, Project project) {
        return userBase.get(username).addSharedProject(project.getProjectName());
    }

    /**
     * addToOwnedProjects
     * adds a project to ownership list
     * @param username target user
     * @param project project being "owned"
     * @return if operation was successful
     */
    public boolean addToOwnedProjects(String username, Project project) {
        return userBase.get(username).createProject(project.getProjectName());
    }

    /**
     * getSharedProjects
     * gets list of shared projects
     * @param username target user
     * @return linkedlist of shared projects
     */
    public LinkedList<String> getSharedProjects(String username) {
        return userBase.get(username).getSharedProjects();
    }

    /**
     * getOwnedProjects
     * @param username username of target user
     * @return linkedlist of shared project
     */
    public LinkedList<String> getSOwnedProjects(String username) {
        return userBase.get(username).getSharedProjects();
    }
}
