import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    public static void main(String[] args) {

        try {

            // Create the remote object
            Calculator calculator = new CalculatorImpl();

            // Start RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind the remote object with a name
            registry.rebind("Calculator", calculator);

            System.out.println("RMI Server started...");
            System.out.println("Calculator service is ready.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}