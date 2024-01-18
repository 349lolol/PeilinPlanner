//imports for network communication
import java.io.*;
import java.net.*;

class MultiThreadedServer {
    final int PORT = 5000;       
    
    ServerSocket serverSocket;
    Socket clientSocket;
    PrintWriter output;
    BufferedReader input;
    int clientCounter = 0;
    
    public static void main(String[] args) throws Exception { 
        MultiThreadedServer server = new MultiThreadedServer();
        HttpHandler e = new HttpHandler();
        server.go();
    }
    
    public void go() throws Exception { 
        //create a socket with the local IP address and wait for connection request       
        System.out.println("Waiting for a connection request from a client ...");
        serverSocket = new ServerSocket(PORT);                //create and bind a socket
        while(true) {
            clientSocket = serverSocket.accept();             //wait for connection request
            clientCounter = clientCounter +1;
            System.out.println("Client "+clientCounter+" connected");
            Thread connectionThread = new Thread(new ConnectionHandler(clientSocket));
            connectionThread.start();                         //start a new thread to handle the connection
        }
    }
    
//------------------------------------------------------------------------------
    class ConnectionHandler extends Thread { 
        Socket socket;
        PrintWriter output;
        BufferedReader input;
        
        public ConnectionHandler(Socket socket) { 
            this.socket = socket;
        }
//------------------------------------------------------------------------------        
        @Override
        public void run() {
            boolean breakLoop = false;
            
                try {
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = new PrintWriter(socket.getOutputStream());
                    while(!breakLoop) {
                        //receive a message from the client
                        String msg = input.readLine();
                        //msg is the request from the js side
                        System.out.println("Message from the client: " + msg);


                        //send a response to the client
                        output.println("Client "+clientCounter+", you are connected!");
                        //change the text in the output to what youre sending to the server in data
                        //use the httphandler functions to deal with it 
                        output.flush();         
                        //after completing the communication close the streams but do not close the socket!
                    }
                try {
                    Thread.sleep(10000);
                }catch(Exception f){
                    f.printStackTrace();
                }
                input.close();
                output.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    
}