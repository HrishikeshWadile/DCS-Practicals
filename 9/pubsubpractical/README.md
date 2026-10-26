# Pub/Sub Practical

## Requirements

- JDK 25
- Apache Maven 3.9 or later

Check the installed versions in PowerShell:

```powershell
$env:JAVA_HOME = "C:\Users\Dell\.jdks\jdk-25.0.4.1+1"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
java -version
mvn -version
```

Confirm that both commands report Java 25 before building. Maven uses the JDK
from `JAVA_HOME`/`PATH`; installing JDK 25 alone does not guarantee Maven will
use it. If JDK 25 is installed elsewhere, set `JAVA_HOME` to that JDK's folder.

## Build

Open PowerShell in this project directory:

```powershell
cd "D:\Study\College\B.Tech\DCS\Practicals\9\pubsubpractical"
$env:JAVA_HOME = "C:\Users\Dell\.jdks\jdk-25.0.4.1+1"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
mvn clean compile
```

## Assignment 9: Publish-Subscribe Messaging

### Code

- [MessageBroker.java](src/main/java/com/example/MessageBroker.java) starts a non-persistent ActiveMQ broker at `tcp://localhost:61616`.
- [Subscriber.java](src/main/java/com/example/Subscriber.java) subscribes to the `NewsTopic` topic and prints received text messages.
- [Publisher.java](src/main/java/com/example/Publisher.java) reads messages from the console and publishes them to `NewsTopic`; enter `exit` to stop.

The broker and both clients use the same topic. Each subscriber connected when a
message is published receives its own copy. This is a non-durable subscription,
so subscribers that are offline do not receive messages published while they
are disconnected.

### Run the program

In PowerShell, build the project after setting `JAVA_HOME` to JDK 25 as shown
above:

```powershell
cd "D:\Study\College\B.Tech\DCS\Practicals\9\pubsubpractical"
$env:JAVA_HOME = "C:\Users\Dell\.jdks\jdk-25.0.4.1+1"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
mvn clean compile
```

Open three PowerShell terminals in the project directory. Set `JAVA_HOME` and
`PATH` in each terminal, then run one command per terminal. Start the broker
first, then the subscriber, and then the publisher.

```powershell
$env:JAVA_HOME = "C:\Users\Dell\.jdks\jdk-25.0.4.1+1"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
mvn exec:java "-Dexec.mainClass=com.example.MessageBroker"
```

```powershell
$env:JAVA_HOME = "C:\Users\Dell\.jdks\jdk-25.0.4.1+1"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
mvn exec:java "-Dexec.mainClass=com.example.Subscriber"
```

```powershell
$env:JAVA_HOME = "C:\Users\Dell\.jdks\jdk-25.0.4.1+1"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
mvn exec:java "-Dexec.mainClass=com.example.Publisher"
```

### Sample output

#### Broker terminal

```text
================================
   MESSAGE BROKER STARTED
================================
Broker URL: tcp://localhost:61616
Waiting for publishers and subscribers...
```

#### Subscriber terminal

```text
================================
       MESSAGE SUBSCRIBER
================================
Subscribed to NewsTopic.
Waiting for messages...

Received: Hello Students
Received: Practical demonstration
```

#### Publisher terminal

```text
Enter message (type 'exit' to stop): Hello Students
Published: Hello Students

Enter message (type 'exit' to stop): Practical demonstration
Published: Practical demonstration

Enter message (type 'exit' to stop): exit
```
