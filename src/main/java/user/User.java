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
     * getPass
     * @return
     */
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
