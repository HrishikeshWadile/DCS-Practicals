public class TerminationDetection {

    // Represents a process in the distributed system
    static class Process {

        int id;
        boolean active;

        Process(int id) {
            this.id = id;
            this.active = false;
        }

        void startWork() {
            active = true;
            System.out.println("Process P" + id + " started working.");
        }

        void sendMessage(Process receiver) {

            // A message is now in transit
            messagesInTransit++;

            System.out.println(
                    "P" + id + " sent a message to P" + receiver.id);
        }

        void receiveMessage() {

            // Message has reached its destination
            messagesInTransit--;

            // Receiving a message makes the process active
            active = true;

            System.out.println(
                    "P" + id + " received a message.");
        }

        void finishWork() {
            active = false;

            System.out.println(
                    "Process P" + id + " finished working.");
        }
    }

    // Number of messages currently in transit
    static int messagesInTransit = 0;

    // Termination detection
    static boolean isTerminated(Process[] processes) {

        // Check whether any process is still active
        for (Process process : processes) {

            if (process.active) {
                return false;
            }
        }

        // Check whether any message is still in transit
        if (messagesInTransit > 0) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        // Create processes
        Process p1 = new Process(1);
        Process p2 = new Process(2);
        Process p3 = new Process(3);

        Process[] processes = { p1, p2, p3 };

        System.out.println("===== DISTRIBUTED SYSTEM =====");

        // P1 starts working
        p1.startWork();

        // P1 sends a message to P2
        p1.sendMessage(p2);

        // P2 receives the message
        p2.receiveMessage();

        // P2 finishes its work
        p2.finishWork();

        // P1 finishes its work
        p1.finishWork();

        // P3 starts and finishes its work
        p3.startWork();
        p3.finishWork();

        System.out.println("\nMessages in transit: "
                + messagesInTransit);

        System.out.println("\n===== TERMINATION CHECK =====");

        if (isTerminated(processes)) {
            System.out.println("System has TERMINATED.");
        } else {
            System.out.println("System has NOT TERMINATED.");
        }
    }
}