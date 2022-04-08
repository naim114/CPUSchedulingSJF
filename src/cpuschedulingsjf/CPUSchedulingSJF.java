package cpuschedulingsjf;

import java.util.*;

public class CPUSchedulingSJF {
    static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Integer> process = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        ArrayList<Double> arrival = new ArrayList<>(Arrays.asList(0.0, 0.0, 3.0, 9.0));
        ArrayList<Double> burst = new ArrayList<>(Arrays.asList(48.0, 45.0, 35.0, 22.0));
        ArrayList<Double> time_process = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> end_time = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));

        ArrayList<Double> start = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> waiting = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));

        ArrayList<Double> cycle = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> length = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> rank = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));

        // ArrayList<Integer> process = new ArrayList<>();
        // ArrayList<Integer> arrival = new ArrayList<>();
        // ArrayList<Integer> burst = new ArrayList<>();
        // ArrayList<Integer> wait = new ArrayList<>();
        // ArrayList<Integer> turnaround = new ArrayList<>();

        /**
         * STEP 1 - Just create table
         */

        // char cont;
        // int countProcess = 1;
        //
        // // Initialize value
        // do {
        // System.out.print("\nEnter arrival time: ");
        //
        // int val = console.nextInt();
        // arrival.add(val);
        //
        // process.add(countProcess);
        // countProcess++;
        //
        // wait.add(0);
        // turnaround.add(0);
        //
        // System.out.print("\nPress Y to continue: ");
        // cont = console.next().charAt(0);
        // } while (Character.toUpperCase(cont) == 'Y');
        //
        // for (int count : arrival) {
        // System.out.print("\nEnter burst: ");
        // int val = console.nextInt();
        //
        // burst.add(val);
        // }

        // Output Step 1
        printOutput(1, process, arrival, burst, time_process, end_time);

        /**
         * STEP 2 - Sort arrival time to ascending order (burst)
         */

        // Selection sort
        for (int i = 0; i < process.size() - 1; i++) {
            int min_index = i;

            for (int j = i + 1; j < process.size(); j++) {
                if (burst.get(j) < burst.get(min_index)) {
                    min_index = j;
                }
            }

            // Sorting burst time
            double smallerNumber = burst.get(min_index);
            burst.set(min_index, burst.get(i));
            burst.set(i, smallerNumber);

            // Sorting burst time
            smallerNumber = arrival.get(min_index);
            arrival.set(min_index, arrival.get(i));
            arrival.set(i, smallerNumber);

            // Sorting process
            int smallerNumberI = process.get(min_index);
            process.set(min_index, process.get(i));
            process.set(i, smallerNumberI);
        }

        // Output Step 2
        printOutput(2, process, arrival, burst, time_process, end_time);

        /**
         * STEP 3 - Calculate waiting and turnaround
         */

        // Sort by arrival
        for (int i = 0; i < process.size() - 1; i++) {
            int min_index = i;

            for (int j = i + 1; j < process.size(); j++) {
                if (arrival.get(j) < arrival.get(min_index)) {
                    min_index = j;
                }
            }

            // Sorting arrival time
            double smallerNumber = arrival.get(min_index);
            arrival.set(min_index, arrival.get(i));
            arrival.set(i, smallerNumber);

            // Sorting burst time
            smallerNumber = burst.get(min_index);
            burst.set(min_index, burst.get(i));
            burst.set(i, smallerNumber);

            // Sorting process
            int smallerNumberI = process.get(min_index);
            process.set(min_index, process.get(i));
            process.set(i, smallerNumberI);
        }

        // Calculate time process
        for (int i = 0; i < process.size(); i++) {
            time_process.set(i, burst.get(i) / 10);
        }

        // Sort based on burst and end time process
        for (int i = 0; i < process.size() - 1; i++) {
            double min_burst = 0;

            if (i == 0) {
                end_time.set(0, burst.get(0) / 10);
            } else {
                // find minimum burst
                for (int j = i; j < process.size(); j++) {
                    if (arrival.get(j) <= end_time.get(i - 1)) {
                        if (j == i) {
                            min_burst = burst.get(j);
                        } else if (min_burst > burst.get(j)) {
                            min_burst = burst.get(j);
                        }
                    }
                }

                for (int j = i; j < process.size(); j++) {
                    if (min_burst == burst.get(j)) {
                        time_process.set(i, burst.get(j) / 10);

                        double temp_burst = burst.get(i);
                        int temp_process = process.get(i);
                        double temp_arrival = arrival.get(i);

                        burst.set(i, burst.get(j));
                        process.set(i, process.get(j));
                        arrival.set(i, arrival.get(j));

                        burst.set(j, temp_burst);
                        process.set(j, temp_process);
                        arrival.set(j, temp_arrival);
                    }
                }
            }
        }

        // Calculate end time
        for (int i = 0; i < process.size(); i++) {
            if (i == 0) {
                end_time.set(0, time_process.get(i));
            } else {
                end_time.set(i, time_process.get(i) + end_time.get(i - 1));
            }
        }

        // Output Step 3
        printOutput(3, process, arrival, burst, time_process, end_time);

        /**
         * STEP 4 - start and time_process
         */

        // Calculate start time (the end time of index before)
        for (int i = 0; i < process.size(); i++) {
            if (i == 0) {
                start.set(0, 0.0);
            } else {
                start.set(i, end_time.get(i - 1));
            }
        }

        // Calculate waiting(start - arr)
        for (int i = 0; i < process.size(); i++) {
            waiting.set(i, (double) Math.round((start.get(i) - arrival.get(i)) * 100) / 100);
        }

        // Output Step 4
        printOutput2(4, process, arrival, start, burst, time_process, end_time, waiting);

        /**
         * STEP 5 - aging
         */

        // aging var
        double w1 = 1.0;
        double w2 = 2.0;
        double min_arrival = 0;
        double min_rank = 0;

        // Sort by arrival
        // for (int i = 0; i < process.size() - 1; i++) {
        // int min_index = i;
        //
        // for (int j = i + 1; j < process.size(); j++) {
        // if (arrival.get(j) < arrival.get(min_index)) {
        // min_index = j;
        // }
        // }
        //
        // // Sorting arrival time
        // double smallerNumber = arrival.get(min_index);
        // arrival.set(min_index, arrival.get(i));
        // arrival.set(i, smallerNumber);
        //
        // // Sorting burst time
        // smallerNumber = burst.get(min_index);
        // burst.set(min_index, burst.get(i));
        // burst.set(i, smallerNumber);
        //
        // // Sorting time_process time
        // smallerNumber = time_process.get(min_index);
        // time_process.set(min_index, time_process.get(i));
        // time_process.set(i, smallerNumber);
        //
        // // Sorting end_time time
        // smallerNumber = end_time.get(min_index);
        // end_time.set(min_index, end_time.get(i));
        // end_time.set(i, smallerNumber);
        //
        // // Sorting start
        // smallerNumber = start.get(min_index);
        // start.set(min_index, start.get(i));
        // start.set(i, smallerNumber);
        //
        // // Sorting waiting
        // smallerNumber = waiting.get(min_index);
        // waiting.set(min_index, waiting.get(i));
        // waiting.set(i, smallerNumber);
        //
        // // Sorting process
        // int smallerNumberI = process.get(min_index);
        // process.set(min_index, process.get(i));
        // process.set(i, smallerNumberI);
        // }

        // initialize index 0
        for (int i = 0; i < process.size(); i++) {
            System.out.println("i: " + i);
            // Find min arrival
            for (int j = i; j < process.size(); j++) {
                if (j == 0) {
                    min_arrival = arrival.get(j);
                } else {
                    if (arrival.get(j) <= min_arrival) {
                        min_arrival = arrival.get(j);
                    }
                }
            }

            int min_rank_index = 0;

            // Set rank if arrival equal to min arrival
            for (int j = i; j < process.size(); j++) {
                if (arrival.get(j) == min_arrival) {
                    rank.set(j, w2 * time_process.get(j));

                    // Find min index to initialize min_rank val
                    if (j <= min_rank_index) {
                        min_rank_index = j;
                    }
                }
            }

            // Find min rank
            for (int j = i; j < process.size(); j++) {
                if (arrival.get(j) == min_arrival) {
                    if (j == min_rank_index) {
                        min_rank = rank.get(min_rank_index);
                    } else {
                        if (rank.get(j) <= min_rank) {
                            min_rank = rank.get(j);
                        }
                    }
                }
            }

            // Index that have min_rank will put on index 0
            for (int j = i; j < process.size(); j++) {
                if (min_rank == rank.get(j)) {
                    int temp_process = process.get(i);
                    double temp_arrival = arrival.get(i);
                    double temp_start = start.get(i);
                    double temp_burst = burst.get(i);
                    double temp_time_process = time_process.get(i);
                    double temp_end_time = end_time.get(i);
                    double temp_waiting = waiting.get(i);

                    process.set(i, process.get(j));
                    arrival.set(i, arrival.get(j));
                    start.set(i, start.get(j));
                    burst.set(i, burst.get(j));
                    time_process.set(i, time_process.get(j));
                    end_time.set(i, end_time.get(j));
                    waiting.set(i, waiting.get(j));

                    process.set(j, temp_process);
                    arrival.set(j, temp_arrival);
                    start.set(j, temp_start);
                    burst.set(j, temp_burst);
                    time_process.set(j, temp_time_process);
                    end_time.set(j, temp_end_time);
                    waiting.set(j, temp_waiting);
                }
            }

            // initialize index that not 0
            if (i != 0) {
                // set length
                for (int j = i; j < process.size(); j++) {
                    if (arrival.get(j) <= end_time.get(i - 1)) {
                        length.set(j, (double) Math.round((end_time.get(i - 1) - arrival.get(j)) * 100) / 100);
                    }
                }

                // compare
                for (int j = i; j < process.size(); j++) {
                    if (arrival.get(j) <= end_time.get(i - 1)) {
                        length.set(j, (double) Math.round((end_time.get(i - 1) - arrival.get(j)) * 100) / 100);

                        System.out.println(process.get(j) + " --> " + arrival.get(j) + " < " + end_time.get(i - 1)
                                + " --> " + length.get(j));
                    }
                }
            }
        }

        // for (int i = 0; i < process.size(); i++) {
        // if (i != 0) {
        // // find minimum rank
        // for (int j = i; j < process.size(); j++) {
        // // if arrival less than previous end time
        // if (arrival.get(j) <= end_time.get(i - 1)) {
        // System.out.println(process.get(j));
        //
        // length.set(j, end_time.get(j - 1) - arrival.get(j));
        // rank.set(j, w1*length.get(j)+w2*time_process.get(j));
        //
        // // store minimum rank
        // if (j == i) {
        // min_rank = rank.get(j);
        // } else if (min_rank > rank.get(j)) {
        // min_rank = rank.get(j);
        // }
        // }
        // }
        //
        // for (int j = i; j < process.size(); j++) {
        // if (min_rank == rank.get(j)) {
        // int temp_process = process.get(i);
        // double temp_arrival = arrival.get(i);
        // double temp_start = start.get(i);
        // double temp_burst = burst.get(i);
        // double temp_time_process = time_process.get(i);
        // double temp_end_time = end_time.get(i);
        // double temp_waiting = waiting.get(i);
        //
        // process.set(i, process.get(j));
        // arrival.set(i, arrival.get(j));
        // start.set(i, start.get(j));
        // burst.set(i, burst.get(j));
        // time_process.set(i, time_process.get(j));
        // end_time.set(i, end_time.get(j));
        // waiting.set(i, waiting.get(j));
        //
        // process.set(j, temp_process);
        // arrival.set(j, temp_arrival);
        // start.set(j, temp_start);
        // burst.set(j, temp_burst);
        // time_process.set(j, temp_time_process);
        // end_time.set(j, temp_end_time);
        // waiting.set(j, temp_waiting);
        // }
        // }
        // }
        // }

        // System.out.println("Min arrival: " + min_arrival);
        // System.out.println("Min Rank: " + min_rank);

        // Calc length & rank
        // for (int i = 0; i < process.size(); i++) {
        // double min_rank = 0;
        // // calc length
        // if (i == 0) {
        // length.set(0, 0.0);
        // rank.set(i, (w1 * length.get(i) + w2 * time_process.get(i)));
        // } else {
        // // find minimum rank
        // for (int j = i; j < process.size(); j++) {
        // // if arrival less than previous end time
        // if (arrival.get(j) <= end_time.get(i - 1)) {
        // // calculate length & rank
        // length.set(i, end_time.get(i - 1) - arrival.get(i));
        // rank.set(i, ((w1 * length.get(i)) + (w2 * time_process.get(i))));
        //
        // // store minimum rank
        // if (j == i) {
        // min_rank = rank.get(j);
        // } else if (min_rank > rank.get(j)) {
        // min_rank = rank.get(j);
        // }
        //
        // System.out.println("\nLength: " + length.get(i));
        // System.out.println("Rank: " + rank.get(i));
        // System.out.println("Min Rank: " + min_rank);
        // }
        // }
        //
        // for (int j = i; j < process.size(); j++) {
        // if (min_rank == rank.get(j)) {
        // // set length & rank
        //// length.set(i, end_time.get(i - 1) - arrival.get(i));
        //// length.set(i, (double) Math.round((end_time.get(i - 1) - arrival.get(i)) *
        // 100) / 100);
        //// rank.set(i, (w1 * length.get(i) + w2 * end_time.get(i)));
        //
        // int temp_process = process.get(i);
        // double temp_arrival = arrival.get(i);
        // double temp_start = start.get(i);
        // double temp_burst = burst.get(i);
        // double temp_time_process = time_process.get(i);
        // double temp_end_time = end_time.get(i);
        // double temp_waiting = waiting.get(i);
        //
        // process.set(i, process.get(j));
        // arrival.set(i, arrival.get(j));
        // start.set(i, start.get(j));
        // burst.set(i, burst.get(j));
        // time_process.set(i, time_process.get(j));
        // end_time.set(i, end_time.get(j));
        // waiting.set(i, waiting.get(j));
        //
        // process.set(j, temp_process);
        // arrival.set(j, temp_arrival);
        // start.set(j, temp_start);
        // burst.set(j, temp_burst);
        // time_process.set(j, temp_time_process);
        // end_time.set(j, temp_end_time);
        // waiting.set(j, temp_waiting);
        //
        // break;
        // }
        // }
        // }
        //
        // // calc rank
        //// rank.set(i, (w1 * length.get(i) + w2 * end_time.get(i)));
        // }

        // Output Step 5
        printOutput3(5, cycle, process, arrival, start, burst, time_process, length, rank, end_time, waiting);
    }

    public static void printOutput(
            int step,
            ArrayList<Integer> process,
            ArrayList<Double> arrival,
            ArrayList<Double> burst,
            ArrayList<Double> time_process,
            ArrayList<Double> end_time) {
        System.out.println("\nSTEP " + step);
        String leftAlignFormat = "| %-11s | %-15s | %-11s | %-16s | %-11s |%n";

        System.out
                .format("+-------------+-----------------+-------------+------------------+-------------+%n");
        System.out
                .format("|   Process   |  Arrival(Time)  |    Burst    |  Time (Process)  |  End(Time)  |%n");
        System.out
                .format("+-------------+-----------------+-------------+------------------+-------------+%n");

        for (int i = 0; i < process.size(); i++) {
            System.out.format(leftAlignFormat, "Process " + process.get(i), arrival.get(i), burst.get(i),
                    time_process.get(i),
                    end_time.get(i));
        }

        System.out
                .format("+-------------+-----------------+-------------+------------------+-------------+%n");
    }

    public static void printOutput2(
            int step,
            ArrayList<Integer> process,
            ArrayList<Double> arrival,
            ArrayList<Double> start,
            ArrayList<Double> burst,
            ArrayList<Double> time_process,
            ArrayList<Double> end_time,
            ArrayList<Double> waiting) {
        System.out.println("\nSTEP " + step);
        String leftAlignFormat = "| %-11s | %-15s | %-7s | %-11s | %-16s | %-11s | %-7s |%n";

        System.out
                .format("+-------------+-----------------+---------+-------------+------------------+-------------+---------+%n");
        System.out
                .format("|   Process   |  Arrival(Time)  |  Start  |    Burst    |  Time (Process)  |  End(Time)  | Waiting |%n");
        System.out
                .format("+-------------+-----------------+---------+-------------+------------------+-------------+---------+%n");

        for (int i = 0; i < process.size(); i++) {
            System.out.format(leftAlignFormat, "Process " + process.get(i), arrival.get(i), start.get(i), burst.get(i),
                    time_process.get(i),
                    end_time.get(i), waiting.get(i));
        }

        System.out
                .format("+-------------+-----------------+---------+-------------+------------------+-------------+---------+%n");
    }

    public static void printOutput3(
            int step,
            ArrayList<Double> cycle,
            ArrayList<Integer> process,
            ArrayList<Double> arrival,
            ArrayList<Double> start,
            ArrayList<Double> burst,
            ArrayList<Double> time_process,
            ArrayList<Double> length,
            ArrayList<Double> rank,
            ArrayList<Double> end_time,
            ArrayList<Double> waiting) {
        System.out.println("\nSTEP " + step);
        String leftAlignFormat = "| %-15s | %-11s | %-15s | %-7s | %-11s | %-16s | %-8s | %-8s | %-11s | %-7s |%n";

        System.out
                .format("+-----------------+-------------+-----------------+---------+-------------+------------------+----------+----------+-------------+---------+%n");
        System.out
                .format("|      Cycle      |   Process   |  Arrival(Time)  |  Start  |    Burst    |  Time (Process)  |  Length  |   Rank   |  End(Time)  | Waiting |%n");
        System.out
                .format("+-----------------+-------------+-----------------+---------+-------------+------------------+----------+----------+-------------+---------+%n");

        for (int i = 0; i < process.size(); i++) {
            System.out.format(leftAlignFormat, (i + 1) + ": Time " + cycle.get(i), "Process " + process.get(i),
                    arrival.get(i), start.get(i), burst.get(i), time_process.get(i), length.get(i), rank.get(i),
                    end_time.get(i), waiting.get(i));
        }

        System.out
                .format("+-----------------+-------------+-----------------+---------+-------------+------------------+----------+----------+-------------+---------+%n");
    }
}
