package user;
import java.util.HashMap;
import java.util.LinkedList;
import entities.Arrow;
import entities.ClassDiagram;
import entities.Diagram;
import entities.ExceptionDiagram;
import entities.Field;
import entities.InterfaceDiagram;
import entities.Method;
import entities.Project;
import java.io.Serializable;
import entities.ProjectBase;

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
        return (password.equals(this.userBase.get(username).getPassword()));
    }
}
