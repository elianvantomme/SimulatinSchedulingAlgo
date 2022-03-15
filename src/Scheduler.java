import java.util.ArrayList;

public class Scheduler {


    static int calculateTAT(Process p) {
        return p.getServiceTime() + p.getWaitingTime();
    }

    static double calculateNormalisedTAT(Process p) {
        return (double) p.getTurnaroundTime() / p.getServiceTime();
    }

    static double calculateMeanTAT(ArrayList<Process> processList) {
        double total = 0.0;
        for(Process p : processList) {
            total += p.getTurnaroundTime();
        }
        return total / (double) processList.size();
    }

    static double calculateMeanNormalisedTAT(ArrayList<Process> processList) {
        double total = 0.0;
        for(Process p : processList) {
            total += p.getNormalisedTurnaroundTime();
        }
        return total / (double) processList.size();
    }

    static double calculateMeanWaitingTime(ArrayList<Process> processList) {
        double total = 0;
        for(Process p : processList) {
            total += p.getWaitingTime();
        }
        return total / (double) processList.size();
    }

    static void calculateWaitingTime(ArrayList<Process> processList){
        for(Process p : processList) {
            int waitTime = p.getEndTime() - p.getArrivalTime() - p.getServiceTime();
            p.setWaitingTime(waitTime);
            p.setTurnaroundTime(calculateTAT(p));
            p.setNormalisedTurnaroundTime(calculateNormalisedTAT(p));;
            //System.out.println(p);
        }
    }
}
