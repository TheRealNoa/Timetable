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

    public static class ProcessListTask implements Runnable {
        private ArrayList<String> list;

        public ProcessListTask(ArrayList<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
             synchronized (TimetableController.class) {
        TimetableController.processInputs(list);
    }
            
        }
    }
    public static void processing(ArrayList<ArrayList<String>> a)
    {
    ExecutorService executor = Executors.newFixedThreadPool(5); 

        for (ArrayList<String> list : a) {
            executor.submit(new ParallelTimetableProcessing.ProcessListTask(list));
        }

        executor.shutdown();
    }
}

