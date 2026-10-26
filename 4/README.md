If this is for your **Distributed Computing practical**, Berkeley clock synchronization is quite straightforward to implement.

The idea is:

1. One node acts as the **coordinator/master**.
2. Master asks all nodes for their current clock times.
3. Master calculates the **average clock time**.
4. It calculates the **adjustment required** for each node.
5. Each node adjusts its clock according to that offset.

For a practical/demo, we can simulate clocks using integer values rather than modifying the actual system clock.

### Java implementation

```java
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
```

### Example

Input:

```text
Enter number of computers: 4

Enter clock time of each computer:
Computer 0: 100
Computer 1: 110
Computer 2: 90
Computer 3: 95
```

The master is Computer 0:

```text
Master Clock = 100
```

Offsets:

```text
Computer 0 =   0
Computer 1 = +10
Computer 2 = -10
Computer 3 =  -5
```

Average offset:

```text
(0 + 10 - 10 - 5) / 4
= -5 / 4
= -1
```

So the clocks are adjusted by:

```text
Computer 0: -1
Computer 1: -11
Computer 2: +9
Computer 3: +4
```

Result:

```text
Computer 0 = 99
Computer 1 = 99
Computer 2 = 99
Computer 3 = 99
```

So all computers become synchronized to:

```text
99
```

### One important detail

This is the **simulated Berkeley algorithm**, which is normally what is expected in a basic DCS practical. The program doesn't actually change the operating system clock; it demonstrates the synchronization calculation.

The core formula is:

```text
Offset = Clock[i] - MasterClock

AverageOffset = Σ Offset / NumberOfComputers

Adjustment[i] = AverageOffset - Offset[i]

NewClock[i] = Clock[i] + Adjustment[i]
```

Unlike Cristian's algorithm, Berkeley synchronization **doesn't synchronize everyone to an external time server**. The coordinator finds an average of the participating clocks and tells everyone how much to adjust.
