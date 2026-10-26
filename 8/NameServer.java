import java.io.*;
import java.net.*;
import java.util.*;

public class NameServer {

    public static void main(String[] args) {

        // Name-to-IP address mapping
        Map<String, String> nameTable = new HashMap<>();

        nameTable.put("google", "142.250.195.14");
        nameTable.put("youtube", "142.250.72.14");
        nameTable.put("facebook", "157.240.241.35");
        nameTable.put("amazon", "205.251.242.103");
        nameTable.put("microsoft", "20.112.52.29");

        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("===== NAME SERVER =====");
            System.out.println("Name Server started.");
            System.out.println("Waiting for client...");
            System.out.println("Port: " + port);

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println("\nClient connected.");

                BufferedReader input = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

                PrintWriter output = new PrintWriter(
                        socket.getOutputStream(), true);

                // Receive domain name from client
                String name = input.readLine();

                System.out.println(
                        "Client requested: " + name);

                // Convert input to lowercase
                name = name.toLowerCase();

                // Search name in table
                if (nameTable.containsKey(name)) {

                    String ip = nameTable.get(name);

                    output.println(ip);

                    System.out.println(
                            "IP address sent: " + ip);

                } else {

                    output.println("Name not found");

                    System.out.println(
                            "Name not found.");
                }

                socket.close();

                System.out.println(
                        "Client disconnected.");
            }

        } catch (IOException e) {

            System.out.println(
                    "Server Error: " + e.getMessage());
        }
    }
}