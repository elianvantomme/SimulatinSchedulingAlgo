import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin extends Scheduler{
    public void process(ArrayList<Process> processList){

        Queue<Process> waitingList = new LinkedList<>();
        int time=0;
        int timeSlot = 4;
        Process current;
        int i=1;
        int remainingExecutionTime = 0;

        //add first process (arrivaltime=0 to the queue)
        waitingList.add(processList.get(0));

        while(waitingList.size() > 0) {
            current = waitingList.remove();
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

            //which processes have arrived in the meantime --> add to queue
            while (i<processList.size()) {
                if(time >= processList.get(i).getArrivalTime()) {
                    waitingList.add(processList.get(i));
                    i++;
                }
                else break;
            }

            //now add the current process to the queue if it hasn't finished
            if(remainingExecutionTime > 0) {
                waitingList.add(current);
            }
            else {
                current.setEndTime(time); // no more execution time remaining --> process is done
            }

        }

        //calculate waiting times
        for(Process p : processList) {
            int waitTime = p.getEndTime() - p.getArrivalTime() - p.getServiceTime();
            p.setWaitingTime(waitTime);
            p.setTurnaroundTime(calculateTAT(p));
            p.setNormalisedTurnaroundTime(calculateNormalisedTAT(p));;
            System.out.println(p);
        }


        System.out.println("\n Round Robin");
        System.out.println("\t mean TAT: " + calculateMeanTAT(processList));
        System.out.println("\t mean normalised TAT: " + calculateMeanNormalisedTAT(processList));
        System.out.println("\t mean waiting time: " + calculateMeanWaitingTime(processList));

    }
}
