Sure. For a **concurrent echo client-server application in Java**, the standard approach is:

* **Server** → uses `ServerSocket`
* **Each client** → handled by a separate `Thread`
* **Echo** → whatever the client sends, the server sends back
* Multiple clients can connect simultaneously.

### How the concurrency works

The important part of the server is:

```java
Socket socket = serverSocket.accept();

ClientHandler clientHandler = new ClientHandler(socket);
clientHandler.start();
```

Every time a client connects, the server creates a **new thread**.

So if:

```text
Client 1 ────────┐
                 │
Client 2 ────────┼──> Echo Server
                 │       │
Client 3 ────────┘       ├── Thread 1 → Client 1
                         ├── Thread 2 → Client 2
                         └── Thread 3 → Client 3
```

all three clients can communicate with the server **concurrently**.

### Running it

Compile:

```bash
Sure. For a **concurrent echo client-server application in Java**, the standard approach is:

* **Server** → uses `ServerSocket`
* **Each client** → handled by a separate `Thread`
* **Echo** → whatever the client sends, the server sends back
* Multiple clients can connect simultaneously.

### 1. `EchoServer.java`

```java
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
```

### 2. `EchoClient.java`

```java
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
```

### How the concurrency works

The important part of the server is:

```java
Socket socket = serverSocket.accept();

ClientHandler clientHandler = new ClientHandler(socket);
clientHandler.start();
```

Every time a client connects, the server creates a **new thread**.

So if:

```text
Client 1 ────────┐
                 │
Client 2 ────────┼──> Echo Server
                 │       │
Client 3 ────────┘       ├── Thread 1 → Client 1
                         ├── Thread 2 → Client 2
                         └── Thread 3 → Client 3
```

all three clients can communicate with the server **concurrently**.

### Running it

Compile:

```bash
javac EchoServer.java EchoClient.java
```

Start the server first:

```bash
java EchoServer
```

Then open **multiple terminals** and run:

```bash
java EchoClient
```

You can start 2, 3, or more clients at the same time. Each will get its own `ClientHandler` thread.

Example:

```text
Client: Hello
Server: Echo: Hello

Client: How are you?
Server: Echo: How are you?

Client: exit
Server: Echo: exit
```

This is a straightforward **TCP-based concurrent echo client-server program**, suitable for a Java networking practical.

```

Start the server first:

```bash
java EchoServer
```

Then open **multiple terminals** and run:

```bash
java EchoClient
```

You can start 2, 3, or more clients at the same time. Each will get its own `ClientHandler` thread.

Example:

```text
Client: Hello
Server: Echo: Hello

Client: How are you?
Server: Echo: How are you?

Client: exit
Server: Echo: exit
```

This is a straightforward **TCP-based concurrent echo client-server program**, suitable for a Java networking practical.
