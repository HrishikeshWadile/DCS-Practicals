import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) {

        String serverAddress = "localhost";
        int port = 5000;

        Scanner scanner = new Scanner(System.in);

        try {

            // Connect to Name Server
            Socket socket = new Socket(serverAddress, port);

            System.out.println(
                    "Connected to Name Server.");

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true);

            // Get name from user
            System.out.print(
                    "Enter domain name: ");

            String name = scanner.nextLine();

            // Send name to server
            output.println(name);

            // Receive IP address
            String response = input.readLine();

            System.out.println(
                    "Response from Name Server: "
                            + response);

            socket.close();

        } catch (IOException e) {

            System.out.println(
                    "Client Error: " + e.getMessage());
        }

        scanner.close();
    }
}