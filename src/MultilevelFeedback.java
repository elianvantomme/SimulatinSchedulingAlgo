import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MultilevelFeedback extends Scheduler {
    public void process(ArrayList<Process> processList) {

        ArrayList<Queue<Process>> queueList = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            queueList.add(new LinkedList<>());
        }

        // add first process
        Process currentProcess = processList.get(0);
        queueList.get(0).add(currentProcess);
        int time = currentProcess.getArrivaltime();
        currentProcess.setStartTime(currentProcess.getArrivaltime());

        int processCounter = 1;
        int currentQueue = 0;
        int finishedProcesses = 0;
        int timeSlot = 16;

        while (finishedProcesses != processList.size()) {

            int remaningExecutionTime = 0;
            for(int k=0; k<queueList.size(); k++) {
                currentProcess = queueList.get(k).poll();
                if(currentProcess != null){
                    currentQueue = k;
                    break;
                }
                else if(k==queueList.size()-1){
                    time++; // all the queues are empty
                }
            }


            if(currentProcess != null){
                remaningExecutionTime = currentProcess.getServiceTime() - currentProcess.getExecutionTime();
                if(currentQueue==0){
                    currentProcess.setStartTime(time); // when in RQ0 the process hasn't started yet
                }
                if(remaningExecutionTime < timeSlot){
                    time += remaningExecutionTime;
                    currentProcess.setExecutionTime(currentProcess.getServiceTime());
                    remaningExecutionTime = 0;
                }
                else {
                    time += timeSlot;
                    currentProcess.setExecutionTime(currentProcess.getExecutionTime() + timeSlot);
                    remaningExecutionTime = currentProcess.getServiceTime() - currentProcess.getExecutionTime();
                }
            }



            //which processes have arrived in the meantime --> add to queue
            while (processCounter<processList.size()) {
                if(time >= processList.get(processCounter).getArrivalTime()) {
                    queueList.get(0).add(processList.get(processCounter));
                    processCounter++;
                }
                else break;
            }

            // now add the current process to the next queue if it hasn't finished
            if(currentProcess != null){
                if(remaningExecutionTime > 0){
                    if(currentQueue == queueList.size()-1) queueList.get(currentQueue).add(currentProcess);
                    else queueList.get(currentQueue+1).add(currentProcess);
                }
                else{
                    currentProcess.setEndTime(time);
                    finishedProcesses++;
                }
            }
        }

        //calculate waiting times
        calculateWaitingTime(processList);

        System.out.println("\n multilevel feedback");
        System.out.println("\t mean TAT: " + calculateMeanTAT(processList));
        System.out.println("\t mean normalised TAT: " + calculateMeanNormalisedTAT(processList));
        System.out.println("\t mean waiting time: " + calculateMeanWaitingTime(processList));
    }
}
