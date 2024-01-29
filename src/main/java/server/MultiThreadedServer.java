/**
 * [MultiThreadedServer.java]
 * Server supporting multiple users
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

 package server;

 //imports for network communication
 import java.io.*;
 import java.net.*;
 import java.nio.charset.StandardCharsets;
 import java.util.Map;
 
 import com.fasterxml.jackson.core.type.TypeReference;
 import com.fasterxml.jackson.databind.ObjectMapper;
 
 import user.User;
 import user.UserBase;
 
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Set;
 import java.util.stream.Collectors;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.List;
 import entities.*;
 
 public class MultiThreadedServer {
     final int PORT = 5069;       
     
     private ServerSocket serverSocket;
     private Socket clientSocket;
     private int clientCounter = 0;
     private UserBase userBase;
     private ProjectBase projectBase = new ProjectBase();
     private Assets assets;

     public void setAssets(Assets assets) {
        this.assets = assets;
     }

     public synchronized void saveUsers() throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("userStorage.txt"))) {
            output.writeObject(this.userBase);
        }
     }

     public void readUsers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("userStorage.txt"))) {
            this.userBase = (UserBase) input.readObject();
        } catch (EOFException e) {
            this.userBase = new UserBase(new HashMap<String, User>());
        }
     }
     
     public synchronized void saveProjects() throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("projectStorage.txt"))) {
            output.writeObject(this.projectBase);
        }
     }

     public void readProjects() throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("projectStorage.txt"))) {
            this.projectBase = (ProjectBase) input.readObject();
        } catch (EOFException e) {
            this.projectBase = new ProjectBase();
        }
     }

     public void go() throws Exception { 
        this.readUsers();
        this.readProjects();

        System.out.println(this.userBase);
        System.out.println(this.projectBase);
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
     
     /**
      * ConnectionHandler class
      */
 //------------------------------------------------------------------------------
    class ConnectionHandler extends Thread { 
        Socket socket;
        OutputStream output;
        BufferedReader input;
         
        public ConnectionHandler(Socket socket) { 
            this.socket = socket;
        }

        private void sendResponse(byte[] content) throws IOException {
            this.sendResponse(content, "application/json");
        }

        private void sendResponse(byte[] content, String mimeType) throws IOException {
            output.write(
                ("HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + mimeType + "\r\n" +
                "Vary: Accept-Encoding\r\n" +
                "Accept-Ranges: none\r\n" +
                "Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.UTF_8)
            );

            output.write(content);

            output.flush();
        }

        private void sendRedirect(String location) throws IOException {
            output.write(
                ("HTTP/1.1 302 Found\r\n" +
                "Location: " + location + "\r\n").getBytes(StandardCharsets.UTF_8)
            );

            output.flush();
        }
 //------------------------------------------------------------------------------        
        @Override
        public void run() {
            int line = 0;
            int emptyLine = 0;
            Map<String, String> header = new HashMap<>();

            List<String> request = new ArrayList<>();

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
                if ((type.equals("GET")) && ((url.equals("/")) || (url.equals("")))) {
                    this.sendRedirect("/frontend/loginpage/loginpage.html");
                }


                if ((type.equals("GET")) && (assets.getAssets().containsKey(url))) {
                    byte[] content = assets.getAssets().get(url);
                    String mimeType = url.substring(url.lastIndexOf('.') + 1);
                    if (mimeType.equals("js")) {
                        mimeType = "javascript";
                    }

                    sendResponse(content, "text/" + mimeType);
                }

                // LOGIN VERIFICATION
                else if ((type.equals("POST")) && (url.equals("/frontend/verify"))) {
                    byte[] content;
                    String usernameObject = request.get(line).split(",")[0];
                    String usernameValue = usernameObject.split(":")[1];
                    String username = usernameValue.substring(1, usernameValue.length() - 1);

                    String passwordObject = request.get(line).split(",")[1];
                    String passwordValue = passwordObject.split(":")[1];
                    String password = passwordValue.substring(1, passwordValue.length() - 2);

                    if (userBase.verifyUser(username, password))  {
                        content = "{\"valid\": true}".getBytes(StandardCharsets.UTF_8);
                        sendResponse(content);  
                    } else {
                        content = "{\"valid\": false}".getBytes(StandardCharsets.UTF_8);
                        sendResponse(content);
                    }
                }

                // NEW USER
                else if ((type.equals("POST")) && (url.equals("/frontend/newUser"))) {
                    byte[] content;
                    String usernameObject = request.get(line).split(",")[0];
                    String usernameValue = usernameObject.split(":")[1];
                    String username = usernameValue.substring(1, usernameValue.length() - 1);

                    String passwordObject = request.get(line).split(",")[1];
                    String passwordValue = passwordObject.split(":")[1];
                    String password = passwordValue.substring(1, passwordValue.length() - 2);

                    if (userBase.addUser(username, password))  {
                        content = "{\"valid\": true}".getBytes(StandardCharsets.UTF_8);
                        sendResponse(content);
                        saveUsers();
                    } else {
                        content = "{\"valid\": false}".getBytes(StandardCharsets.UTF_8);
                        sendResponse(content);
                    }
                }

                // LOADING PROJECTS IN
                else if ((type.equals("POST")) && (url.equals("/frontend/loadProjects"))) {
                    byte[] content;
                    StringBuilder stringContent = new StringBuilder();
                    stringContent.append("{\"projectName\": [");

                    String usernameObject = request.get(line).split(",")[0];
                    String usernameValue = usernameObject.split(":")[1];
                    String username = usernameValue.substring(1, usernameValue.length() - 2);

                    User user = userBase.getUser(username);
                    Iterator<String> projectIterator = user.getOwnedProjects().iterator();

                    int i = 0;

                while (projectIterator.hasNext()) {
                    String projectName = projectIterator.next();

                    String projectContent = "\"" + projectName + "\"";
                    
                    stringContent.append(projectContent);

                    if (i != user.getOwnedProjects().size() - 1) {
                        stringContent.append(",");
                    } else {
                        stringContent.append("]}");
                    }
                    i++;
                }

                    content = stringContent.toString().getBytes(StandardCharsets.UTF_8);

                    sendResponse(content);
                }

                // CREATING A PROJECT
                else if ((type.equals("POST")) && (url.equals("/frontend/createProject"))) {
                    byte[] content;
                    String usernameString = request.get(line).split(",")[0];
                    String usernameValue = usernameString.split(":")[1];
                    String username = usernameString.split(":")[1].substring(1, usernameValue.length() - 1);

                    String projectString = request.get(line).split(",")[1];
                    String projectNameValue = projectString.split(":")[1];
                    String projectName = projectNameValue.substring(1, projectNameValue.length() - 2);

                    // if user's owned projects contain the project with the name
                    if (projectName.equals("")) {
                        content = "{\"valid\": false}".getBytes();

                    } else if (!userBase.getUser(username).getOwnedProjects().contains(projectName)) {
                        content = "{\"valid\": true}".getBytes();
                        projectBase.addProject(projectName);
                        userBase.addToOwnedProjects(username, projectBase.getProject(projectName));

                        saveUsers();
                        saveProjects();
                    } else {
                        content = "{\"valid\": false}".getBytes();
                    }

                    sendResponse(content);
                }

                // SHARING A PROJECT
                else if ((type.equals("POST")) && (url.equals("/frontend/shareProject"))) {
                    byte[] content;
                    String projectString = request.get(line).split(",")[0];
                    String projectValue = projectString.split(":")[1];
                    String projectName = projectValue.substring(1, projectValue.length() - 1);

                    String shareeString = request.get(line).split(",")[1];
                    String shareeValue = shareeString.split(":")[1];
                    String shareeName = shareeValue.substring(1, shareeValue.length() - 2);

                    // if user's owned projects contain the project with the name
                    User sharee = userBase.getUser(shareeName);

                    if (sharee != null) {
                        sharee.getSharedProjects().add(projectName);
                        content = "{\"valid\": true}".getBytes();
                    } else {
                        content = "{\"valid\": false}".getBytes();
                    }

                    sendResponse(content);
                }

                // COLLABORATION
                else if ((type.equals("POST")) && (url.equals("/frontend/collaborate"))) {
                    byte[] content;
                    String usernameString = request.get(line).split(",")[0];
                    String usernameValue = usernameString.split(":")[1];
                    String username = usernameString.split(":")[1].substring(1, usernameValue.length() - 1);

                    String projectString = request.get(line).split(",")[1];
                    String projectNameValue = projectString.split(":")[1];
                    String projectName = projectNameValue.substring(1, projectNameValue.length() - 2);

                    if (userBase.getUser(username).getSharedProjects().contains(projectName)) {
                        content = "{\"valid\": true}".getBytes();
                    } else {
                        content = "{\"valid\": false}".getBytes();
                    }

                    sendResponse(content);
                }

                // SAVING UML
                else if ((type.equals("POST")) && (url.equals("/frontend/saveUML"))) {
                    byte[] content;
                    String projectString = request.get(line).split(",")[0];
                    String projectNameValue = projectString.split(":")[1];
                    String projectName = projectNameValue.substring(1, projectNameValue.length() - 1);

                    Project project = projectBase.getProject(projectName);

                    project.jsonToJava(request.get(line));

                    for (Diagram diagram : project.getAllDiagrams()) {
                        System.out.println("hi" + diagram.getXPosition());
                        System.out.println(diagram.getYPosition());
                    }

                    content = "{\"valid\": true}".getBytes();

                    sendResponse(content);
                } // 416 205

                // LOADING UML
                else if ((type.equals("POST")) && (url.equals("/frontend/loadUML"))) {
                    byte[] content;
                    String usernameString = request.get(line).split(",")[0];
                    String usernameValue = usernameString.split(":")[1];
                    String username = usernameString.split(":")[1].substring(1, usernameValue.length() - 2);

                    String projectString = request.get(line).split(",")[1];
                    String projectNameValue = projectString.split(":")[1];
                    String projectName = projectNameValue.substring(1, projectNameValue.length() - 2);

                    content = projectBase.getProject(projectName).javaToJson().getBytes();

                    sendResponse(content);
                }

                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}