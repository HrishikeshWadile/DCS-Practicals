import java.util.*;

public class BerkeleyAlgorithm {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of computers: ");
        int n = sc.nextInt();

        int[] clocks = new int[n];

        System.out.println("\nEnter clock time of each computer:");

        for (int i = 0; i < n; i++) {
            System.out.print("Computer " + i + ": ");
            clocks[i] = sc.nextInt();
        }

        // Computer 0 is the master/coordinator
        int masterClock = clocks[0];

        System.out.println("\nMaster Computer: Computer 0");
        System.out.println("Master Clock: " + masterClock);

        // Calculate differences from master
        int[] offsets = new int[n];

        for (int i = 0; i < n; i++) {
            offsets[i] = clocks[i] - masterClock;
        }

        System.out.println("\nClock Differences:");

        for (int i = 0; i < n; i++) {
            System.out.println(
                    "Computer " + i +
                            " : " + offsets[i]);
        }

        // Calculate average offset
        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += offsets[i];
        }

        int averageOffset = sum / n;

        System.out.println("\nAverage Offset: " + averageOffset);

        // Calculate adjustments
        System.out.println("\nClock Adjustments:");

        for (int i = 0; i < n; i++) {

            int adjustment = averageOffset - offsets[i];

            System.out.println(
                    "Computer " + i +
                            " adjustment: " + adjustment);
        }

        // Apply adjustments
        System.out.println("\nSynchronized Clocks:");

        for (int i = 0; i < n; i++) {

            int adjustment = averageOffset - offsets[i];

            clocks[i] = clocks[i] + adjustment;

            System.out.println(
                    "Computer " + i +
                            " : " + clocks[i]);
        }

        sc.close();
    }
}