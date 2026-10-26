import StudentModule.Student;
import StudentModule.StudentHelper;

import org.omg.CORBA.ORB;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class StudentClient {

        public static void main(String[] args) {

                try {

                        /*
                         * Disable GlassFish CORBA monitoring.
                         * This avoids the GMBAL Java 17 compatibility
                         * exception during ORB initialization.
                         */
                        System.setProperty(
                                        "org.glassfish.gmbal.noop",
                                        "true");

                        // Create ORB with GlassFish CORBA monitoring disabled
                        java.util.Properties props = new java.util.Properties();
                        props.setProperty("org.glassfish.gmbal.disable", "true");

                        ORB orb = ORB.init(args, props);

                        // Read server object reference
                        byte[] refBytes = Files.readAllBytes(java.nio.file.Paths.get("student.ref"));
                        String objectReference = new String(refBytes, StandardCharsets.UTF_8);

                        // Convert string into CORBA object
                        org.omg.CORBA.Object obj = orb.string_to_object(objectReference);

                        // Convert to Student remote object
                        Student student = StudentHelper.narrow(obj);

                        int rollNo = 2;

                        System.out.println("=================================");
                        System.out.println("       CORBA Student Client");
                        System.out.println("=================================");

                        System.out.println("Connected to CORBA Server.");
                        System.out.println();

                        System.out.println("Roll Number : " + rollNo);

                        System.out.println(
                                        "Student Name: "
                                                        + student.getStudentName(rollNo));

                        System.out.println(
                                        "Branch      : "
                                                        + student.getBranch(rollNo));

                } catch (Exception e) {

                        System.out.println("Client Error:");
                        e.printStackTrace();
                }
        }
}