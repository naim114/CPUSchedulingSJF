package cpuschedulingsjf;

import java.util.*;

public class CPUSchedulingSJF {
    static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Integer> process = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        ArrayList<Integer> arrival = new ArrayList<>(Arrays.asList(0, 0, 3, 9));
        ArrayList<Integer> burst = new ArrayList<>(Arrays.asList(48, 45, 35, 22));
        ArrayList<Integer> wait = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        ArrayList<Integer> turnaround = new ArrayList<>(Arrays.asList(0, 0, 0, 0));

//        ArrayList<Integer> process = new ArrayList<>();
//        ArrayList<Integer> arrival = new ArrayList<>();
//        ArrayList<Integer> burst = new ArrayList<>();
//        ArrayList<Integer> wait = new ArrayList<>();
//        ArrayList<Integer> turnaround = new ArrayList<>();

        /**
         * STEP 1 - Just create table
         */

//        char cont;
//        int countProcess = 1;
//
//        // Initialize value
//        do {
//            System.out.print("\nEnter arrival time: ");
//
//            int val = console.nextInt();
//            arrival.add(val);
//
//            process.add(countProcess);
//            countProcess++;
//
//            wait.add(0);
//            turnaround.add(0);
//
//            System.out.print("\nPress Y to continue: ");
//            cont = console.next().charAt(0);
//        } while (Character.toUpperCase(cont) == 'Y');
//
//        for (int count : arrival) {
//            System.out.print("\nEnter burst: ");
//            int val = console.nextInt();
//
//            burst.add(val);
//        }

        // Output Step 1
        printOutput(1, process, arrival, burst, wait, turnaround);

        /**
         * STEP 2 - Sort burst time to ascending order (selection sort)
         */

        // Selection sort
        for (int i = 0; i < process.size() - 1; i++) {
            int min_index = i;

            for (int j = i + 1; j < burst.size(); j++) {
                if (burst.get(j) < burst.get(min_index)) {
                    min_index = j;
                }
            }

            // Sorting burst time
            int smallerNumber = burst.get(min_index);
            burst.set(min_index, burst.get(i));
            burst.set(i, smallerNumber);

            // Sorting burst time
            smallerNumber = arrival.get(min_index);
            arrival.set(min_index, arrival.get(i));
            arrival.set(i, smallerNumber);
        }

        // Output Step 2
        printOutput(2, process, arrival, burst, wait, turnaround);

        /**
         * STEP 3 - Calculate waiting and turnaround
         */

        // Calculate waiting time
        for (int i = 0; i < process.size(); i++) {
            if (i == 0) {
                wait.set(0, 0);
            } else {
                wait.set(i, burst.get(i - 1) + wait.get(i - 1));
            }
        }

        // Calculate turnaround
        for (int i = 0; i < process.size(); i++) {
            if (i == 0) {
                turnaround.set(0, wait.get(1));
            } else {
                turnaround.set(i, burst.get(i) + wait.get(i));
            }
        }

        // Output Step 3
        printOutput(3, process, arrival, burst, wait, turnaround);

        System.out.println("\nAverage Waiting (Process): " + calcAvg(wait));
        System.out.println("Average Turnaround(Time): " + calcAvg(turnaround));
    }

    public static void printOutput(
            int step,
            ArrayList<Integer> process,
            ArrayList<Integer> arrival,
            ArrayList<Integer> burst,
            ArrayList<Integer> wait,
            ArrayList<Integer> turnaround) {
        System.out.println("\nSTEP " + step);
        String leftAlignFormat = "| %-11s | %-15s | %-11s | %-19s | %-18s |%n";

        System.out
                .format("+-------------+-----------------+-------------+---------------------+--------------------+%n");
        System.out
                .format("|   Process   |  Arrival(Time)  |    Burst    |  Waiting (Process)  |  Turnaround(Time)  |%n");
        System.out
                .format("+-------------+-----------------+-------------+---------------------+--------------------+%n");

        for (int i = 0; i < arrival.size(); i++) {
            System.out.format(leftAlignFormat, "Process " + process.get(i), arrival.get(i), burst.get(i), wait.get(i),
                    turnaround.get(i));
        }

        System.out
                .format("+-------------+-----------------+-------------+---------------------+--------------------+%n");
    }

    /**
     * @param arr - ArrayList<Integer>
     * @return avg
     */
    public static int calcAvg(ArrayList<Integer> arr) {
        int total = 0;

        for (int val : arr) {
            total = total + val;
        }

        return total / arr.size();
    }
}
