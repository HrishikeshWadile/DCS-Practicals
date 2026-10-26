Yep. Since you're on **Java 17**, let's do this properly with **Maven + GlassFish CORBA**, so you don't have to install Java 8. GlassFish provides the CORBA ORB and an `idlj` compiler artifact; the current Maven Central listings include these artifacts. ([Maven Central][1])

We'll also use **Student Information** instead of Calculator so it is clearly different from your RMI practical.

## 3rd Practical — CORBA Server-Client

### Step 1 — Create this folder

```text
D:\Study\College\B.Tech\DCS\Practicals\3
```

Inside it:

```text
3
├── pom.xml
├── src
│   └── main
│       ├── idl
│       │   └── Student.idl
│       └── java
│           ├── StudentServer.java
│           └── StudentClient.java
```

The generated CORBA classes will be created automatically by Maven.

---

# Run the Server

After compilation:

```powershell
mvn exec:java -Dexec.mainClass="StudentServer"
```

If the Exec plugin isn't configured, add this plugin to `pom.xml`:

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.5.0</version>
</plugin>
```

Then run:

```powershell
mvn exec:java -Dexec.mainClass="StudentServer"
```

You should get:

```text
CORBA Server started...
Student service is ready.
Waiting for clients...
```

And you'll see:

```text
student.ref
```

created in the project directory.

---

# 7. Run the Client

Open **another PowerShell terminal**:

```powershell
cd "D:\Study\College\B.Tech\DCS\Practicals\3"
```

Then:

```powershell
mvn exec:java -Dexec.mainClass="StudentClient"
```

Expected:

```text
Connected to CORBA Server.

Roll Number: 2
Student Name: Amit
Branch: Information Technology
```

---

## How this differs from your RMI practical

Your previous practical was:

```text
RMI
Client
  │
  │ Remote Method Call
  ▼
RMI Registry
  │
  ▼
Remote Object
```

This practical is:

```text
CORBA
Client
  │
  │ CORBA Request
  ▼
   ORB
  │
  ▼
Remote CORBA Object
```

The important CORBA components for your viva are:

| Component               | Purpose                                                |
| ----------------------- | ------------------------------------------------------ |
| **IDL**                 | Defines the remote interface                           |
| **ORB**                 | Handles communication between client and server        |
| **POA**                 | Creates/manages CORBA servants                         |
| **Stub/Helper classes** | Allow the client to communicate with the remote object |
| **Servant**             | Actual implementation of the remote interface          |

One correction from my previous response: I initially suggested `NameService` without actually setting up a naming service. **The version above avoids that unnecessary complication** by writing the CORBA object reference to `student.ref`, so you don't need a separate `tnameserv`/`orbd` process.

And importantly, we're not using Java's old built-in CORBA. The `org.omg.*` API and ORB are supplied by the GlassFish CORBA dependencies, which is why this approach can be used with your **Java 17** installation. The GlassFish project currently publishes both the CORBA API and ORB artifacts through Maven. ([Maven Central][1])

[1]: https://central.sonatype.com/artifact/org.glassfish.corba/glassfish-corba-omgapi/4.2.4?utm_source=chatgpt.com "Maven Central: org.glassfish.corba:glassfish-corba-omgapi:4.2.4"
[2]: https://mvnrepository.com/artifact/org.glassfish.corba/idlj/usages?utm_source=chatgpt.com "Maven Repository: org.glassfish.corba » idlj - Used By"
