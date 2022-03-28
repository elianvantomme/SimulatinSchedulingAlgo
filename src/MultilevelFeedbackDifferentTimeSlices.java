import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class CustomQue {
    int i;
    int timeSlot;
    Queue<Process> queue;

    public CustomQue(int i){
        this.i = i;
        timeSlot = (int) Math.pow(2,i);
        queue = new LinkedList<>();
    }
}


public class MultilevelFeedbackDifferentTimeSlices extends Scheduler {
    public void process(ArrayList<Process> processList) {

        ArrayList<CustomQue> queueList = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            queueList.add(new CustomQue(j));
        }

        // add first process
        Process currentProcess = processList.get(0);
        queueList.get(0).queue.add(currentProcess);
        int time = currentProcess.getArrivaltime();
        currentProcess.setStartTime(currentProcess.getArrivaltime());

        int processCounter = 1;
        int finishedProcesses = 0;


        while (finishedProcesses != processList.size()) {

            CustomQue currentQueue = null;
            int remaningExecutionTime = 0;
            for(int k=0; k<queueList.size(); k++) {
                currentProcess = queueList.get(k).queue.poll();
                if(currentProcess != null){
                    currentQueue = queueList.get(k);
                    break;
                }
                else if(k==queueList.size()-1){
                    time++; // all the queues are empty
                }
            }


            if(currentProcess != null){
                remaningExecutionTime = currentProcess.getServiceTime() - currentProcess.getExecutionTime();
                if(currentQueue.i==0){
                    currentProcess.setStartTime(time); // when in RQ0 the process hasn't started yet
                }
                if(remaningExecutionTime < currentQueue.timeSlot){
                    time += remaningExecutionTime;
                    currentProcess.setExecutionTime(currentProcess.getServiceTime());
                    remaningExecutionTime = 0;
                }
                else {
                    time += currentQueue.timeSlot;
                    currentProcess.setExecutionTime(currentProcess.getExecutionTime() + currentQueue.timeSlot);
                    remaningExecutionTime = currentProcess.getServiceTime() - currentProcess.getExecutionTime();
                }
            }



            //which processes have arrived in the meantime --> add to queue
            while (processCounter<processList.size()) {
                if(time >= processList.get(processCounter).getArrivalTime()) {
                    queueList.get(0).queue.add(processList.get(processCounter));
                    processCounter++;
                }
                else break;
            }

            // now add the current process to the next queue if it hasn't finished
            if(currentProcess != null){
                if(remaningExecutionTime > 0){
                    if(currentQueue.i == queueList.size()-1) queueList.get(currentQueue.i).queue.add(currentProcess);
                    else queueList.get(currentQueue.i+1).queue.add(currentProcess);
                }
                else{
                    currentProcess.setEndTime(time);
                    finishedProcesses++;
                }
            }
        }

        //calculate waiting times
        calculateWaitingTime(processList);

        System.out.println("\n multilevel feedback q=2^i");
        System.out.println("\t mean TAT: " + calculateMeanTAT(processList));
        System.out.println("\t mean normalised TAT: " + calculateMeanNormalisedTAT(processList));
        System.out.println("\t mean waiting time: " + calculateMeanWaitingTime(processList));
    }
}
