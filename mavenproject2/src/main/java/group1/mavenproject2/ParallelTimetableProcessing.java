/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author noaca
 */

public class ParallelTimetableProcessing {

    public static class ProcessListTask implements Runnable {
        private ArrayList<String> list;
        private TimetableModel m;
        public ProcessListTask(ArrayList<String> list, TimetableModel m) {
            this.list = list;
            this.m=m;
        }

        @Override
        public void run() {
            synchronized (TimetableController.class) {
                TimetableController.processInputs(list,m);
            }
        }
    }

    public static void processing(ArrayList<ArrayList<String>> a, TimetableModel m) {
        ExecutorService executor = Executors.newSingleThreadExecutor(); // Use single-threaded executor
        System.out.println(a);
        for (ArrayList<String> list : a) {
            executor.submit(new ProcessListTask(list,m));
            System.out.println("Submitted list:" + list);
            executor.shutdown(); // Ensure the task completes before submitting the next one
            while (!executor.isTerminated()) {
                // Wait for the task to complete
            }
            executor = Executors.newSingleThreadExecutor(); // Reinitialize executor for the next task
        }
    }
}


