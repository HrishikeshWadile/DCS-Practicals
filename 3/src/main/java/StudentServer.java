import StudentModule.Student;
import StudentModule.StudentHelper;
import StudentModule.StudentOperations;

import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;

public class StudentServer {

    // Implementation of the remote Student interface
    static class StudentImpl extends Servant
            implements StudentOperations,
            org.omg.CORBA.portable.InvokeHandler {

        @Override
        public String getStudentName(int rollNo) {

            switch (rollNo) {

                case 1:
                    return "Rahul";

                case 2:
                    return "Amit";

                case 3:
                    return "Priya";

                default:
                    return "Student Not Found";
            }
        }

        @Override
        public String getBranch(int rollNo) {

            switch (rollNo) {

                case 1:
                case 2:
                case 3:
                    return "Information Technology";

                default:
                    return "Branch Not Found";
            }
        }

        @Override
        public OutputStream _invoke(
                String method,
                InputStream input,
                org.omg.CORBA.portable.ResponseHandler handler) {

            switch (method) {

                case "getStudentName": {

                    int rollNo = input.read_long();

                    String result = getStudentName(rollNo);

                    OutputStream out = handler.createReply();

                    out.write_string(result);

                    return out;
                }

                case "getBranch": {

                    int rollNo = input.read_long();

                    String result = getBranch(rollNo);

                    OutputStream out = handler.createReply();

                    out.write_string(result);

                    return out;
                }

                default:

                    throw new org.omg.CORBA.BAD_OPERATION(
                            0,
                            org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
            }
        }

        @Override
        public String[] _all_interfaces(
                POA poa,
                byte[] objectId) {

            return new String[] {
                    "IDL:StudentModule/Student:1.0"
            };
        }
    }

    public static void main(String[] args) {

        try {

            java.util.Properties props = new java.util.Properties();
            props.setProperty("org.glassfish.gmbal.disable", "true");

            // Create ORB
            ORB orb = ORB.init(args, props);

            // Get Root POA
            POA rootPOA = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA"));

            // Activate POA
            rootPOA.the_POAManager().activate();

            // Create implementation
            StudentImpl studentImpl = new StudentImpl();

            // Convert servant into CORBA object
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(studentImpl);

            Student studentRef = StudentHelper.narrow(ref);

            // Store object reference
            String objectReference = orb.object_to_string(studentRef);

            java.nio.file.Files.write(
                    java.nio.file.Paths.get("student.ref"),
                    objectReference.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            System.out.println("CORBA Server started...");
            System.out.println("Student service is ready.");
            System.out.println("Waiting for clients...");

            // Keep server running
            orb.run();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}