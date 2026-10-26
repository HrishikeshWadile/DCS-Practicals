import java.io.*;
import java.net.*;

public class EchoServer {

    public static void main(String[] args) {

        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Echo Server started...");
            System.out.println("Waiting for clients...");

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println(
                    "Client connected: " + socket.getInetAddress()
                );

                // Create a separate thread for each client
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


// Handles communication with one client
class ClientHandler extends Thread {

    private Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
            BufferedReader input =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );

            PrintWriter output =
                new PrintWriter(socket.getOutputStream(), true)
        ) {

            String message;

            while ((message = input.readLine()) != null) {

                System.out.println(
                    "Received from client: " + message
                );

                // Echo the message back to the client
                output.println("Echo: " + message);

                // Stop if client sends "exit"
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        } catch (IOException e) {

            System.out.println("Client disconnected.");

        } finally {

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Client connection closed.");
        }
    }
}