import java.util.ArrayList;

public class FirstComeFirstServe extends Scheduler {
    public void process(ArrayList<Process> processList){
        Process firstProcess = processList.get(0);
        firstProcess.setStartTime(firstProcess.getArrivaltime());
        firstProcess.setEndTime(firstProcess.getArrivaltime() + firstProcess.getServiceTime());
        firstProcess.setTurnaroundTime(calculateTAT(firstProcess));
        firstProcess.setNormalisedTurnaroundTime(calculateNormalisedTAT(firstProcess));


        for(int i=1; i<processList.size(); i++) {
            Process prevProcess = processList.get(i-1);
            Process currentProcess = processList.get(i);
            int temp = prevProcess.getArrivaltime() + prevProcess.getServiceTime() + prevProcess.getWaitingTime();
            if(temp >= currentProcess.getArrivaltime()) {
                currentProcess.setWaitingTime(temp-currentProcess.getArrivaltime());
            }
            currentProcess.setStartTime(currentProcess.getArrivaltime() + currentProcess.getWaitingTime());
            currentProcess.setEndTime(currentProcess.getStartTime() + currentProcess.getServiceTime());
            currentProcess.setTurnaroundTime(calculateTAT(currentProcess));
            currentProcess.setNormalisedTurnaroundTime(calculateNormalisedTAT(currentProcess));
        }
        System.out.println("\n First come first serve:");
        System.out.println("\t mean TAT: " + calculateMeanTAT(processList));
        System.out.println("\t mean normalised TAT: " + calculateMeanNormalisedTAT(processList));
        System.out.println("\t mean waiting time: " + calculateMeanWaitingTime(processList));
    }
}
