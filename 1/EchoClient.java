import java.io.*;
import java.net.*;

public class EchoClient {

    public static void main(String[] args) {

        String serverAddress = "localhost";
        int port = 5000;

        try (
            Socket socket = new Socket(serverAddress, port);

            BufferedReader keyboard =
                new BufferedReader(
                    new InputStreamReader(System.in)
                );

            BufferedReader input =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );

            PrintWriter output =
                new PrintWriter(socket.getOutputStream(), true)
        ) {

            System.out.println("Connected to Echo Server.");
            System.out.println("Type a message (type 'exit' to quit):");

            String message;

            while (true) {

                System.out.print("Client: ");
                message = keyboard.readLine();

                // Send message to server
                output.println(message);

                // Receive echoed message
                String response = input.readLine();

                System.out.println("Server: " + response);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}