import java.util.*;

public class BullyAlgorithm {

    static int n;
    static boolean[] active;
    static int coordinator;

    static void election(int initiator) {

        System.out.println("\nProcess " + initiator + " starts an election.");

        boolean higherProcessFound = false;

        for (int i = initiator + 1; i <= n; i++) {

            if (active[i]) {

                higherProcessFound = true;

                System.out.println(
                        "Process " + initiator +
                                " sends ELECTION message to Process " + i);

                System.out.println(
                        "Process " + i +
                                " sends OK message to Process " + initiator);

                election(i);
                return;
            }
        }

        if (!higherProcessFound) {

            coordinator = initiator;

            System.out.println(
                    "\nProcess " + initiator +
                            " becomes the new COORDINATOR.");

            for (int i = 1; i <= n; i++) {

                if (active[i] && i != coordinator) {

                    System.out.println(
                            "Coordinator message sent from Process " +
                                    coordinator + " to Process " + i);
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();

        active = new boolean[n + 1];

        for (int i = 1; i <= n; i++) {
            active[i] = true;
        }

        System.out.print("Enter current coordinator: ");
        coordinator = sc.nextInt();

        System.out.print("Enter process that detects coordinator failure: ");
        int initiator = sc.nextInt();

        active[coordinator] = false;

        System.out.println(
                "\nProcess " + coordinator +
                        " has failed.");

        election(initiator);

        System.out.println(
                "\nFinal Coordinator: Process " + coordinator);

        sc.close();
    }
}
