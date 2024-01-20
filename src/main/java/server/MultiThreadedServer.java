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
import entities.*;

class MultiThreadedServer {
    final int PORT = 5069;       
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int clientCounter = 0;
    private static UserBase userBase = new UserBase(new HashMap<String, User>());
    private static ProjectBase projectBase = new ProjectBase();
    private static Assets assets;
    
    public static void main(String[] args) throws Exception { 
        // INITIALIZE userBase
        userBase.addUser("MidtrickWei", "IMADECAKID");
        userBase.addUser("Parmesan", "Cheese");
        
        userBase.getUser("MidtrickWei").addProject(null)
        MultiThreadedServer server = new MultiThreadedServer();
        HttpHandler e = new HttpHandler();
        MultiThreadedServer.assets = new Assets();
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
            
                try {
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = socket.getOutputStream();
                    String currentLine = input.readLine();

                    while ((currentLine != null) && (currentLine.length() != 0)) {
                        request.add(currentLine);
                        line++;

                        currentLine = input.readLine();
                    }

                    StringBuilder requestBody = new StringBuilder();

                    try {
                        while (this.input.ready()) {
                            requestBody.append((char) this.input.read());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
        
                    // Append request body to the request list
                    request.add(requestBody.toString());

                    String type = request.get(0).split(" ")[0];
                    String url = request.get(0).split(" ")[1];

                    for (int i = 1; i < emptyLine - 1; i++) {
                        header.put(request.get(i).split(": ")[0], request.get(i).split(": ")[1]);
                    }

                    // ------------------------- REQUEST HANDLING ------------------------------------

                    // WEBPAGE LOADING
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

                    // LOGIN VERIFICATION
                    else if ((type.equals("POST")) && (url.equals("/frontend/verify"))) {
                        System.out.println(request);
                        byte[] content;
                        String usernameObject = request.get(line).split(",")[0];
                        String usernameValue = usernameObject.split(":")[1];
                        String username = usernameValue.substring(1, usernameValue.length() - 1);

                        String passwordObject = request.get(line).split(",")[1];
                        String passwordValue = passwordObject.split(":")[1];
                        String password = passwordValue.substring(1, passwordValue.length() - 2);
                        
                        output.write(
                            ("HTTP/1.1 200 OK\r\n" +
                            "Content-Type: application/json\r\n" +
                            "Vary: Accept-Encoding\r\n" +
                            "Accept-Ranges: none\r\n").getBytes(StandardCharsets.UTF_8)
                        );

                        if (userBase.verifyUser(username, password))  {
                            content = "{\"valid\": true}".getBytes(StandardCharsets.UTF_8);
                            output.write(("Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                            output.write(content);
        
                            output.flush();   
                        } else {
                            content = "{\"valid\": false}".getBytes(StandardCharsets.UTF_8);
                            output.write(("Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                            output.write(content);

                            output.flush();
                        }
                    }

                    // NEW USER
                    else if ((type.equals("POST")) && (url.equals("/frontend/newUser"))) {
                        System.out.println(request);
                        byte[] content;
                        String usernameObject = request.get(line).split(",")[0];
                        String usernameValue = usernameObject.split(":")[1];
                        String username = usernameValue.substring(1, usernameValue.length() - 1);

                        String passwordObject = request.get(line).split(",")[1];
                        String passwordValue = passwordObject.split(":")[1];
                        String password = passwordValue.substring(1, passwordValue.length() - 2);
                        
                        output.write(
                            ("HTTP/1.1 200 OK\r\n" +
                            "Content-Type: application/json\r\n" +
                            "Vary: Accept-Encoding\r\n" +
                            "Accept-Ranges: none\r\n").getBytes(StandardCharsets.UTF_8)
                        );

                        if (userBase.addUser(username, password))  {
                            content = "{\"valid\": true}".getBytes(StandardCharsets.UTF_8);
                            output.write(("Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                            output.write(content);
        
                            output.flush();   
                        } else {
                            content = "{\"valid\": false}".getBytes(StandardCharsets.UTF_8);
                            output.write(("Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                            output.write(content);

                            output.flush();
                        }
                    }

                    // LOADING PROJECTS IN
                    else if ((type.equals("POST")) && (url.equals("/frontend/loadProjects"))) {
                        System.out.println(request);
                        // byte[] content = "{\"projectNames\": [ \"PROJECTNAME\", ]}".getBytes(StandardCharsets.UTF_8);
                        byte[] content;
                        StringBuilder stringContent = new StringBuilder();
                        stringContent.append("{\"projectName\": [");

                        String usernameObject = request.get(line).split(",")[0];
                        String usernameValue = usernameObject.split(":")[1];
                        String username = usernameValue.substring(1, usernameValue.length() - 2);

                        User user = userBase.getUser(username);

                        for (int i = 0; i < user.getSharedProjects().size(); i++) {
                            String projectName = user.getSharedProjects().get(i).getProjectName();

                            String projectContent = "\"" + projectName + "\"";
                            
                            stringContent.append(projectContent);

                            if (i != user.getSharedProjects().size() - 1) {
                                stringContent.append(",");
                            } else {
                                stringContent.append("]}");
                            }
                        }

                        content = stringContent.toString().getBytes(StandardCharsets.UTF_8);

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

                    // CREATING A PROJECT
                    else if ((type.equals("POST")) && (url.equals("/frontend/createProject"))) {
                        System.out.println(request);
                        byte[] content;
                        String usernameString = request.get(line).split(",")[0];
                        String usernameValue = usernameString.split(":")[1].substring(1, usernameString.length() - 1);

                        String projectString = request.get(line).split(",")[1];
                        String projectName = projectString.split(":")[1].substring(1, projectString.length() - 2);

                        // if user's shared projects contain the project with the name
                        if (userBase.getUser(usernameValue).getSharedProjects().contains(projectBase.getProject(projectName))) {
                            content = "{\"valid\": true}".getBytes();
                        } else {
                            content = "{\"valid\": false}".getBytes();
                        }

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

                    // COLLABORATION
                    else if ((type.equals("POST")) && (url.equals("/frontend/collaborate"))) {
                        System.out.println(request);
                        String requestString = request.get(line).split(":")[1];
                        String requestValue = requestString.substring(1, requestString.length() - 2);
                        if ()

                        byte[] content = projectBase.addProject(requestValue).getBytes();

                        output.write(
                            ("HTTP/1.1 200 OK\r\n" +
                            "Content-Type: application/json\r\n" +
                            "Vary: Accept-Encoding\r\n" +
                            "Accept-Ranges: none\r\n" +
                            "Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8)
                        );

                        output.write(content);
                        System.out.println(projectBase.addProject(requestValue));

                        output.flush();
                    }
                input.close();
                output.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    
}