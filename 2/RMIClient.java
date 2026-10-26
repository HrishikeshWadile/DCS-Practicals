import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {

        public static void main(String[] args) {

                try {

                        // Look up the remote object
                        Calculator calculator = (Calculator) Naming.lookup(
                                        "rmi://localhost:1099/Calculator");

                        System.out.println("Connected to RMI Server.");

                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter the first number: ");
                        int a = scanner.nextInt();
                        System.out.print("Enter the second number: ");
                        int b = scanner.nextInt();

                        System.out.println("a = " + a);
                        System.out.println("b = " + b);

                        System.out.println("Addition = "
                                        + calculator.add(a, b));

                        System.out.println("Subtraction = "
                                        + calculator.subtract(a, b));

                        System.out.println("Multiplication = "
                                        + calculator.multiply(a, b));

                        System.out.println("Division = "
                                        + calculator.divide(a, b));

                        scanner.close();

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}