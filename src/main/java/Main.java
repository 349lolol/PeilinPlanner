import server.MultiThreadedServer;
import server.Assets;

/**
 * [Main.java] - PeilinPlanner
 * PeilinPlanner is a UML software that allows users to access their UML diagrams remotely via web
 * Users can retain the full functionality of traditional UML diagrams from a main server
 * Users can also opt to share their projects to other people so multiple users can access a single UML diagram
 * However, users should not attempt to edit the same uml at the same time 
 * visit PeilinPlanner using this link: http://localhost:5069/frontend/loginpage/loginpage.html
 * @Author Perry Xu & Patrick Wei
 */

public class Main {
    public static void main(String[] args) throws Exception { 
        MultiThreadedServer server = new MultiThreadedServer();
        server.setAssets(new Assets());
        server.go();
        
    }
}
