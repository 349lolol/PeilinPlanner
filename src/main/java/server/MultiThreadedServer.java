package server;

//imports for network communication
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import user.User;
import user.UserBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

class MultiThreadedServer {
    final int PORT = 5069;       
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int clientCounter = 0;
    private UserBase userBase = new UserBase(new HashMap<String, User>());
    private static Assets assets;
    
    public static void main(String[] args) throws Exception { 
        // INITIALIZE userBase
        MultiThreadedServer server = new MultiThreadedServer();
        HttpHandler e = new HttpHandler();
        MultiThreadedServer.assets = new Assets();
        server.go();
        ObjectMapper objectMapper = new ObjectMapper();


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
        OutputStream output;
        BufferedReader input;
        
        public ConnectionHandler(Socket socket) { 
            this.socket = socket;
        }
//------------------------------------------------------------------------------        
        @Override
        public void run() {
            boolean breakLoop = false;
            int line = 0;
            int emptyLine = 0;
            Map<String, String> header = new HashMap<>();

            List<String> request = new ArrayList<>();
            String msg = "";
            int emptyLines = 0;
            
                try {
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = socket.getOutputStream();
                    while(emptyLines != 2) {
                        //receive a message from the client
                        msg = input.readLine();
                        System.out.println("message:" + msg);
                        line++;
                        request.add(msg);
                        if (msg.equals("") && emptyLines == 0) {
                            emptyLines++;
                            emptyLine = line;
                        }
                        if (msg.equals("") && emptyLines == 1) {
                            emptyLines++;
                        }

                    }

                    String type = request.get(0).split(" ")[0];
                    String url = request.get(0).split(" ")[1];

                    for (int i = 1; i < emptyLine - 1; i++) {
                        header.put(request.get(i).split(": ")[0], request.get(i).split(": ")[1]);
                    }

                    if ((type.equals("GET")) && (assets.getAssets().containsKey(url))) {
                        byte[] content = assets.getAssets().get(url);
                        String mimeType = url.substring(url.lastIndexOf('.') + 1);
                        if (mimeType.equals("js")) {
                            mimeType = "javascript";
                        }
                        output.write(
                            ("HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/" + mimeType + "; charset=iso-8859-1\r\n" +
                            "Vary: Accept-Encoding\r\n" +
                            "Accept-Ranges: none\r\n" +
                            "Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8)
                        );

                        output.write(content);

                        output.flush();   
                    }

                    if ((type.equals("POST")) && (url.equals("/frontend1"))) {
                        System.out.println(request);
                        byte[] content = "{\"valid\": true}".getBytes(StandardCharsets.UTF_8);
                        String username = request.get(emptyLine + 1);
                        String password = request.get(emptyLine + 2);
                        if (userBase.verifyUser(username, password)) {
                            output.write(
                                ("HTTP/1.1 200 OK\r\n" +
                                "Content-Type: application/json\r\n" +
                                "Vary: Accept-Encoding\r\n" +
                                "Accept-Ranges: none\r\n" +
                                "Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8)
                            );

                            output.write(content);
        
                            output.flush();   
                        }
                    }
                input.close();
                output.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    
}