/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

/**
 *
 * @author noaca
 */
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelTimetableProcessing {
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> dataList = new ArrayList<>(); // Your list of lists

        ExecutorService executor = Executors.newFixedThreadPool(5); // Create a thread pool with 5 threads

        for (ArrayList<String> list : dataList) {
            executor.submit(new ProcessListTask(list));
        }

        executor.shutdown(); // Shutdown the executor after all tasks are submitted
    }

    static class ProcessListTask implements Runnable {
        private ArrayList<String> list;

        public ProcessListTask(ArrayList<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            // Process the list here
            System.out.println("Processing list: " + list);
            // Example: Print each element of the list
            for (String item : list) {
                System.out.println(item);
            }
        }
    }
}

