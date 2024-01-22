/**
 * [User.java]
 * Class representing a single user
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package user;

import java.util.LinkedList;
import java.util.HashSet;


public class User {
    private final String username;
    private final String password;
    private final HashSet<String> ownedProjects;
    private final LinkedList<String> sharedProjects;

    /**
     * User constructor
     * @param username unique username of user
     * @param password password of user
     */
    User(String username, String password) {
        this.username = username;
        this.password = password;
        ownedProjects = new HashSet<String>();
        sharedProjects = new LinkedList<String>();
    }

    /**
     * getUsername
     * @return username
     */
    public String getUserName() {
        return this.username;
    }

    /**
     * getPassword
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }


    /**
     * getSharedProjects
     * gets linkedlist of shared projects
     */
    public LinkedList<String> getSharedProjects() {
        return this.sharedProjects;
    }

    /**
     * getOwnedProjects
     * @return linkedlist of owned projects
     */
    public HashSet<String> getOwnedProjects() {
        return this.ownedProjects;
    }

    /**
     * 
     * @param project
     * @return
     */
    public boolean addSharedProject(String project) {
        return sharedProjects.add(project);
    }

    public boolean createProject(String project) {
        return ownedProjects.add(project);
    }
}
