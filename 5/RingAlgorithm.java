import java.util.*;

public class RingAlgorithm {

    static int n;
    static boolean[] active;
    static int coordinator;

    static void election(int initiator) {

        System.out.println(
                "\nProcess " + initiator +
                        " starts an election.");

        List<Integer> electionList = new ArrayList<>();

        int current = initiator;

        do {

            if (active[current]) {

                electionList.add(current);

                System.out.println(
                        "Election message reaches Process " +
                                current);
            }

            current++;

            if (current > n) {
                current = 1;
            }

        } while (current != initiator);

        coordinator = Collections.max(electionList);

        System.out.println(
                "\nElection message returns to Process " +
                        initiator);

        System.out.println(
                "Election participants: " + electionList);

        System.out.println(
                "\nProcess " + coordinator +
                        " becomes the new COORDINATOR.");

        for (int i = 1; i <= n; i++) {

            if (active[i] && i != coordinator) {

                System.out.println(
                        "Coordinator message sent from Process " +
                                coordinator + " to Process " + i);
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

        System.out.print(
                "Enter process that detects coordinator failure: ");

        int initiator = sc.nextInt();

        active[coordinator] = false;

        System.out.println(
                "\nProcess " + coordinator +
                        " has failed.");

        election(initiator);

        System.out.println(
                "\nFinal Coordinator: Process " +
                        coordinator);

        sc.close();
    }
}