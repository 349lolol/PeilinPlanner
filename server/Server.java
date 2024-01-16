/**
 * [HttpServer.java]
 * Class representing a HttpServer
 * @author Patrick Wei
 * @version 1.0
 * 01/09/24
 */


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import entities.Arrow;
import entities.ClassDiagram;
import entities.Diagram;
import entities.ExceptionDiagram;
import entities.Field;
import entities.InterfaceDiagram;
import entities.Method;
import entities.Parameter;
import entities.Project;

public class Server {

    //wait for papi to come back with the solution
    public static void main(String[] args) throws IOException {
        OpenProject openProject = new OpenProject();
        Project project = openProject();
        // int port = 8080;
        // Server server = Server.create(new InetSocketAddress(port), 0);
        
        // // Context for the "/hello" path, handling both GET and POST requests
        // server.createContext("/hello", new HelloHandler());
        
        // // Start the server
        // server.setExecutor(null);
        // server.start();
        
        // System.out.println("Server is running on port " + port);

        
    }

    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Handle GET requests
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                handleGetRequest(exchange);
            }
            
            // Handle POST requests
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                handlePostRequest(exchange);
            }
        }

        private void handleGetRequest(HttpExchange exchange) throws IOException {
            // Send a simple response for GET requests
            String response = "Hello, this is a GET request!";
            sendResponse(exchange, response);
        }

        private void handlePostRequest(HttpExchange exchange) throws IOException {
            // Read the POST request body
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            
            // Send a response with the received data for POST requests
            String response = "Hello, this is a POST request! Received data: " + requestBody;
            sendResponse(exchange, response);
        }

        private void sendResponse(HttpExchange exchange, String response) throws IOException {
            // Send the HTTP headers
            exchange.sendResponseHeaders(200, response.length());

            // Get the response body stream and write the response
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}