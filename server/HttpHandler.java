import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import entities.Arrow;
import entities.ClassDiagram;
import entities.Diagram;
import entities.ExceptionDiagram;
import entities.Field;
import entities.InterfaceDiagram;
import entities.Method;
import entities.Project;
import entities.ProjectBase;
import user.User;
import user.UserBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class HttpHandler {
    public HttpHandler() {

    }

    public String handleRequest(String request, UserBase userBase) {
        if(request.startsWith("LoginRequest:")) {
            //use java.split() to extract username and password out of request
            if(userBase.verifyUser("Username", "Password")) {
                //send them to home page of user, have to send appropriate UML projects
            }
            else {
                //send them back to login page
            }
            
        }
        else if(request.startsWith("NewAccountRequest:")) {
            //use java.split() to extract username and password out of request
            if(userBase.addUser("Username", "Password")) {
                //return back to homepage with confirmation
            }
            else {
                //send them back to new user page
            }
        }
        else if(request.startsWith("CreateUML:")) {
            //extract name of project from the request
            Project newProject = new Project();
            newProject.setProjectName("Project Name");
            //index the project, check if its allowed
            //return the newProject to user
        }
        else if(request.startsWith("GetUML:")) {
            //search for project in the system
        } 
        else if(request.startsWith("UpdateUML:")) {
            //directly save their return xml back to file
        } 
        else {
            System.out.println("stinky");
        }
        return null;
    }

    public String createResponse(String responseType, ArrayList<String> data){
        //pass in an arraylist of data needed for everything, use arraylist for flexibility in
        if(responseType.equals("LoginSuccess")) {

        }
        else if(responseType.equals("LoginFail")) {

        }
        else if(responseType.equals("NewAccountSuccess")) {

        }
        else if(responseType.equals("NewAccountFail")) {

        }
        else if(responseType.equals("NewUMLSuccess")) {

        }
        else if(responseType.equals("NewUMLFail")) {

        }
        else if(responseType.equals("GetUMLSuccess")) {

        }
        else if(responseType.equals("GetUMLFail")) {

        }
        else if(responseType.equals("SaveUML")) {

        }
        return null;
    }

}
