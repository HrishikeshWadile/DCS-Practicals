Yes. For a **Java RMI client/server process communication** practical, you can implement a simple remote `add()` method. The architecture is:

```text
        Client Process                         Server Process
       ┌──────────────┐                      ┌──────────────┐
       │  RMI Client  │ ──── RMI Call ─────> │ RMI Server   │
       │              │ <─── Result ─────── │              │
       └──────────────┘                      └──────────────┘
                                                   │
                                             Remote Object
```

Here is the complete implementation.

## 5. Compile

Keep all four files in the **same folder**:

```text
RMI/
├── Calculator.java
├── CalculatorImpl.java
├── RMIServer.java
└── RMIClient.java
```

Compile:

```bash
javac *.java
```

## 6. Run the Server

First run:

```bash
java RMIServer
```

You should see:

```text
RMI Server started...
Calculator service is ready.
```

## 7. Run the Client

Open another terminal in the same folder:

```bash
java RMIClient
```

Output:

```text
Connected to RMI Server.
a = 20
b = 10
Addition = 30
Subtraction = 10
Multiplication = 200
Division = 2
```

### What is actually happening?

The important part is:

```java
Calculator calculator =
    (Calculator) Naming.lookup(
        "rmi://localhost:1099/Calculator"
    );
```

The client doesn't create `CalculatorImpl` itself.

Instead, it obtains a **reference/proxy to the remote object** running inside the server process.

So when the client executes:

```java
calculator.add(20, 10);
```

the call travels through **RMI** to the server process:

```text
Client
   │
   │ calculator.add(20, 10)
   ▼
RMI Registry
   │
   ▼
Remote CalculatorImpl
   │
   │ return 30
   ▼
Client
```

This demonstrates **inter-process communication using Java Remote Method Invocation (RMI)**.
