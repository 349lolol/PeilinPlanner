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
     private static UserBase userBase = new UserBase(new HashMap<String, User>());
     private static ProjectBase projectBase = new ProjectBase();
     private static Assets assets;
     
     /**
      * amin method
      * @param args
      * @throws Exception
      */
     public static void main(String[] args) throws Exception { 
 
         try
         {   
             // Reading the object from a file
             FileInputStream file = new FileInputStream("./userStorage.txt");
             ObjectInputStream in = new ObjectInputStream(file);
              
             // Method for deserialization of object
             userBase = (UserBase)in.readObject();
             in.close();
             file.close();
         }
         catch(IOException e)
         {
             System.out.println("Error deserializing");
         }
         try
         {   
             // Reading the object from a file
             File folder = new File("./server/umlStorage/");
             File[] listOfFiles = folder.listFiles();
 
             for (File file : listOfFiles) {
                 if (file.isFile()) {
                     BufferedReader UMLReader = new BufferedReader(new FileReader(file));
                     projectBase.addProject(file.getName());
                     projectBase.getProject(file.getName()).JsonToJava(UMLReader.readLine());
                     UMLReader.close();
                 }
             }
         }
         catch(IOException exception)
         {
             System.out.println("Error reading in UML diagrams");
         }
 
         try
         {   
             //Saving of object in a file
             FileOutputStream file = new FileOutputStream("./userStorage.txt");
             ObjectOutputStream out = new ObjectOutputStream(file);
              
             // Method for serialization of object
             out.writeObject(userBase);
              
             out.close();
             file.close();
         }
         catch(IOException ex)
         {
             System.out.println("Error serializing");
         }
 
         try
         {   
             Set<String> keys = projectBase.getAllKeys();
             // Reading the object from a file
             for(String key : keys) {
                 File UMLSave = new File("./server/umlStorage/" + projectBase.getProject(key));
                 PrintWriter UMLWriter = new PrintWriter(UMLSave);
                 UMLWriter.print(projectBase.getProject(key).toString());
                 UMLWriter.close();
             }
         }
         catch(IOException exception)
         {
             System.out.println("Error printing UML diagrams");
         }
     }

     public static void setAssets(Assets assets) {
        MultiThreadedServer.assets = assets;
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
                         // byte[] content = "{\"projectNames\": [ \"PROJECTNAME\", ]}".getBytes(StandardCharsets.UTF_8);
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
                             userBase.getUser(username).getOwnedProjects().add(projectName);
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
 
                     // SAVING UML
                     else if ((type.equals("POST")) && (url.equals("/frontend/saveUML"))) {
                         ObjectMapper objectMapper = new ObjectMapper();
                         
                         byte[] content;
                         String projectString = request.get(line).split(",")[0];
                         String projectNameValue = projectString.split(":")[1];
                         String projectName = projectNameValue.substring(1, projectNameValue.length() - 1);
 
                         Project project = projectBase.getProject(projectName);
                         
                         TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String,String>>() {};
                         Map<String, String> jsonMap = objectMapper.readValue(request.get(line), typeReference);
 
                         System.out.println(request.get(line));
 
                         project.setProjectName(projectName);
                         project.setDiagramIdCount(Integer.parseInt(jsonMap.get("diagramCount")));
                         project.setArrowIdCount(Integer.parseInt(jsonMap.get("arrowCount")));
 
                         // GETTING CLASS INFORMATION
 
                         // splitting into individual diagram objects
                         if (!request.get(line).contains("\"diagrams\":\"[]\"")) {
                             String[] diagramMapArray = jsonMap.get("diagrams").substring(1, jsonMap.get("diagrams").length() - 1).split("\\}\\,\\{");
                             for (int i = 0; i < diagramMapArray.length; i++) {
                                 if (i > 0) {
                                     diagramMapArray[i] = "{" + diagramMapArray[i];
                                    }
                                    
                                    if (i < diagramMapArray.length - 1) {
                                        diagramMapArray[i] = diagramMapArray[i] + "}";
                                    }
                                }
                                
                            System.out.println(jsonMap);
                            System.out.println(Arrays.toString(diagramMapArray));
 
                             for (int i = 0; i < diagramMapArray.length; i++) {
                                 Map<String, String> diagramMap = objectMapper.readValue(diagramMapArray[i], typeReference);
                                 System.out.println(diagramMap);
                                 LinkedList<Field> fields = new LinkedList<>();
                                 LinkedList<Method> methods = new LinkedList<>();
                                 if (diagramMap.get("fields") != null) {
                                     String fieldsString = diagramMap.get("fields");
                                     String[] fieldsArray = fieldsString.substring(1, fieldsString.length() - 1).split(",");
                                     fields = Arrays.stream(fieldsArray)
                                         .map(str -> new Field(str))
                                         .collect(Collectors.toCollection(LinkedList::new));
                                 }
 
                                 if (diagramMap.get("methods") != null) {
                                     String methodsString = diagramMap.get("methods");
                                     String[] methodsArray = methodsString.substring(1, methodsString.length() - 1).split(",");
                                     methods = Arrays.stream(methodsArray)
                                         .map(str -> new Method(str))
                                         .collect(Collectors.toCollection(LinkedList::new));
                                 }
 
                                 int id = Integer.parseInt(diagramMap.get("classId"));
                                 // class
                                 if ((diagramMap.get("fields") != null) && (diagramMap.get("methods") != null)
                                 && (diagramMap.get("isAbstract").equals("false"))) {
                                     if (project.getDiagram(id) != null) {
                                         project.updateDiagram(id, new ClassDiagram(
                                             diagramMap.get("className"),
                                             id,
                                             false,
                                             fields,
                                             methods,
                                             (int) Double.parseDouble(diagramMap.get("xPosition")),
                                             (int) Double.parseDouble(diagramMap.get("yPosition")),
                                             (int) Double.parseDouble(diagramMap.get("xSize")),
                                             (int) Double.parseDouble(diagramMap.get("ySize"))
                                         ));
                                     } else {
                                         project.addDiagram(new ClassDiagram(
                                             diagramMap.get("className"),
                                             id,
                                             false,
                                             fields,
                                             methods,
                                             (int) Double.parseDouble(diagramMap.get("xPosition")),
                                             (int) Double.parseDouble(diagramMap.get("yPosition")),
                                             (int) Double.parseDouble(diagramMap.get("xSize")),
                                             (int) Double.parseDouble(diagramMap.get("ySize"))
                                         ));
                                     }
 
 
                                 // abstract class
                                 } else if ((diagramMap.get("fields") != null) && (diagramMap.get("methods") != null)
                                 && (diagramMap.get("isAbstract").equals("true"))) {
                                     if (project.getDiagram(id) != null) {
                                         project.updateDiagram(id, new ClassDiagram(
                                             diagramMap.get("className"),
                                             id,
                                             true,
                                             fields,
                                             methods,
                                             (int) Double.parseDouble(diagramMap.get("xPosition")),
                                             (int) Double.parseDouble(diagramMap.get("yPosition")),
                                             (int) Double.parseDouble(diagramMap.get("xSize")),
                                             (int) Double.parseDouble(diagramMap.get("ySize"))
                                         ));
                                     } else {
                                         project.addDiagram(new ClassDiagram(
                                             diagramMap.get("className"),
                                             id,
                                             true,
                                             fields,
                                             methods,
                                             (int) Double.parseDouble(diagramMap.get("xPosition")),
                                             (int) Double.parseDouble(diagramMap.get("yPosition")),
                                             (int) Double.parseDouble(diagramMap.get("xSize")),
                                             (int) Double.parseDouble(diagramMap.get("ySize"))
                                         ));
                                     }
                                 // interface
                                 } else if ((diagramMap.get("fields") == null) && (diagramMap.get("methods") != null)) {
                                     if (project.getDiagram(id) != null) {
                                         project.updateDiagram(id, new InterfaceDiagram(
                                             diagramMap.get("className"),
                                             id,
                                             methods,
                                             (int) Double.parseDouble(diagramMap.get("xPosition")),
                                             (int) Double.parseDouble(diagramMap.get("yPosition")),
                                             (int) Double.parseDouble(diagramMap.get("xSize")),
                                             (int) Double.parseDouble(diagramMap.get("ySize"))
                                         ));
                                     } else {
                                         project.addDiagram(new InterfaceDiagram(
                                             diagramMap.get("className"),
                                             id,
                                             methods,
                                             (int) Double.parseDouble(diagramMap.get("xPosition")),
                                             (int) Double.parseDouble(diagramMap.get("yPosition")),
                                             (int) Double.parseDouble(diagramMap.get("xSize")),
                                             (int) Double.parseDouble(diagramMap.get("ySize"))
                                         ));
                                     }
                                 // exception
                                 } else if ((diagramMap.get("fields") == null) && (diagramMap.get("methods") == null)) {
                                     if (project.getDiagram(id) != null) {
                                         project.updateDiagram(id, new ExceptionDiagram(
                                             diagramMap.get("className"),
                                             id,
                                             (int) Double.parseDouble(diagramMap.get("xPosition")),
                                             (int) Double.parseDouble(diagramMap.get("yPosition")),
                                             (int) Double.parseDouble(diagramMap.get("xSize")),
                                             (int) Double.parseDouble(diagramMap.get("ySize"))
                                         ));
                                     } else {
                                         project.addDiagram(new ExceptionDiagram(
                                             diagramMap.get("className"),
                                             id,
                                             (int) Double.parseDouble(diagramMap.get("xPosition")),
                                             (int) Double.parseDouble(diagramMap.get("yPosition")),
                                             (int) Double.parseDouble(diagramMap.get("xSize")),
                                             (int) Double.parseDouble(diagramMap.get("ySize"))
                                         ));
                                     }
                                 }
                             }
                         }
 
                         for (Diagram diagram : project.getAllDiagrams()) {
                             System.out.println(diagram.getName());
                             System.out.println(diagram.getXPosition());
                             System.out.println(diagram.getYPosition());
                             System.out.println(diagram.getXSize());
                             System.out.println(diagram.getYSize());
                         }

                         System.out.println(project.getAllDiagrams());
                         
                         // GETTING ARROW INFORMATION
 
                         // splitting into individual arrow objects
                         if (!request.get(line).contains("\"arrows\":\"[]\"")) {
                             
                             String[] arrowMapArray = jsonMap.get("arrows").substring(1, jsonMap.get("arrows").length() - 1).split("\\}\\,\\{");
                             for (int i = 0; i < arrowMapArray.length; i++) {
                                 if (i > 0) {
                                     arrowMapArray[i] = "{" + arrowMapArray[i];
                                 }
                                 
                                 if (i < arrowMapArray.length - 1) {
                                     arrowMapArray[i] = arrowMapArray[i] + "}";
                                 }
                             }
 
                             for (String arrowObject : arrowMapArray) {
                                 Map<String, String> arrowMap = objectMapper.readValue(arrowObject, typeReference);
 
                                 String xPointsString = arrowMap.get("xPoints");
                                 String[] xPointsArray = xPointsString.substring(1, xPointsString.length() - 1).split(",");
                                 ArrayList<Integer> xPoints = Arrays.stream(xPointsArray)
                                     .mapToDouble(Double::parseDouble)
                                     .mapToInt(d -> (int) d)
                                     .boxed()
                                     .collect(Collectors.toCollection(ArrayList::new));
 
                                 String yPointsString = arrowMap.get("yPoints");
                                 String[] yPointsArray = yPointsString.substring(1, yPointsString.length() - 1).split(",");
                                 ArrayList<Integer> yPoints = Arrays.stream(yPointsArray)
                                     .mapToDouble(Double::parseDouble)
                                     .mapToInt(d -> (int) d)
                                     .boxed()
                                     .collect(Collectors.toCollection(ArrayList::new));
 
 
                                 Diagram origin;
                                 Diagram destination;
                                 if (arrowMap.get("origin") != null) {
                                     origin = project.getDiagram(Integer.parseInt(arrowMap.get("origin")));
                                 } else {
                                     origin = null;
                                 }
 
                                 if (arrowMap.get("destination") != null) {
                                     destination = project.getDiagram(Integer.parseInt(arrowMap.get("destination")));
                                 } else {
                                     destination = null;
                                 }
 
                                 if (project.getArrow(Integer.parseInt(arrowMap.get("arrowId"))) != null) {
                                     project.updateArrow(Integer.parseInt(arrowMap.get("arrowId")), new Arrow(
                                         origin,
                                         destination,
                                         "\"" + arrowMap.get("arrowType") + "\"",
                                         xPoints,
                                         yPoints
                                     ));
                                 } else {
                                     project.addArrow(Integer.parseInt(arrowMap.get("arrowId")), new Arrow(
                                         origin,
                                         destination,
                                         "\"" + arrowMap.get("arrowType") + "\"",
                                         xPoints,
                                         yPoints
                                     ));
                                 }
                             }
                         }
 
 
                         content = "{\"valid\": true}".getBytes();
 
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
 
                     // LOADING UML
                     else if ((type.equals("POST")) && (url.equals("/frontend/loadUML"))) {
                         byte[] content;
                         String usernameString = request.get(line).split(",")[0];
                         String usernameValue = usernameString.split(":")[1];
                         String username = usernameString.split(":")[1].substring(1, usernameValue.length() - 2);
 
                         String projectString = request.get(line).split(",")[1];
                         String projectNameValue = projectString.split(":")[1];
                         String projectName = projectNameValue.substring(1, projectNameValue.length() - 2);
 
                         System.out.println(projectBase);
                         System.out.println(projectName);
                         
                         System.out.println(projectBase.getProject(projectName));
                         System.out.println(projectBase.getProject(projectName).javaToJson());
                         content = projectBase.getProject(projectName).javaToJson().getBytes();
 
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
                 input.close();
                 output.close();
             }catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }    
 }