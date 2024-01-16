//imports for network communication
import java.io.*;
import java.net.*;

class BasicServer{
    final int PORT = 5000;    
    
    ServerSocket serverSocket;
    Socket clientSocket;
    PrintWriter output;
    BufferedReader input;
    
    public static void main(String[] args) throws Exception{ 
        BasicServer server = new BasicServer();
        server.start();
        server.stop();                                        //after completing the communication close the streams and the sockets
    }
    
    public void start() throws Exception{ 
        //create a socket with the local IP address and wait for connection request        
        System.out.println("Waiting for a connection request from a client ...");
        serverSocket = new ServerSocket(PORT);                //create and bind a socket
        clientSocket = serverSocket.accept();                 //wait for connection request
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream());  
        System.out.println("Connection with client established!");
        
        String msg=input.readLine();                          //get a message from the client
        System.out.println("Message from client: '"+msg+"'"); 
        output.println("Hi. I am a basic server");            //send a response to the client
        output.flush();                                       //ensure the message was sent but not kept in the buffer
    }
    
    public void stop() throws Exception{
        input.close();
        output.close();
        serverSocket.close();
        clientSocket.close();
    }        
}