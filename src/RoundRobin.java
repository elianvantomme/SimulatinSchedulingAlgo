
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin extends Scheduler{
    public void process(ArrayList<Process> processList){

        Queue<Process> waitingList = new LinkedList<>();
        int time=0;
        int timeSlot = 1;
        Process current;
        int i=1;
        int remainingExecutionTime = 0;
        int finishedProcesses=0;

        //add first process
        waitingList.add(processList.get(0));

        while(finishedProcesses != processList.size()) {
            current = waitingList.poll();
            if(current != null) {
                if(current.getStartTime()==0) current.setStartTime(time);

                remainingExecutionTime = current.getServiceTime() - current.getExecutionTime();
                if(remainingExecutionTime < timeSlot) {
                    time += remainingExecutionTime;
                    current.setExecutionTime(current.getServiceTime());
                    remainingExecutionTime = 0;
                }
                else {
                    time += timeSlot;
                    current.setExecutionTime(current.getExecutionTime() + timeSlot);
                    remainingExecutionTime = current.getServiceTime() - current.getExecutionTime();
                }
            }
            else {
                time++;
            }


            //which processes have arrived in the meantime --> add to queue
            while (i<processList.size()) {
                if(time >= processList.get(i).getArrivalTime()) {
                    waitingList.add(processList.get(i));
                    i++;
                }
                else break;
            }

            //now add the current process to the queue if it hasn't finished
            if(current != null) {
                if(remainingExecutionTime > 0) {
                    waitingList.add(current);
                }
                else {
                    current.setEndTime(time); // no more execution time remaining --> process is done
                    finishedProcesses++;
                }
            }


        }

        //calculate waiting times
        calculateWaitingTime(processList);

        System.out.println("\n Round Robin");
        System.out.println("\t mean TAT: " + calculateMeanTAT(processList));
        System.out.println("\t mean normalised TAT: " + calculateMeanNormalisedTAT(processList));
        System.out.println("\t mean waiting time: " + calculateMeanWaitingTime(processList));



    }
}
