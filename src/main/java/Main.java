import server.MultiThreadedServer;
import server.Assets;

public class Main {
    public static void main(String[] args) throws Exception { 
        MultiThreadedServer server = new MultiThreadedServer();
        server.setAssets(new Assets());
        server.go();
        
    }
}
