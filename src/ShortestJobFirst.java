import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ShortestJobFirst extends Scheduler{
    public ArrayList<Process> process(ArrayList<Process> processList){
        List<Process> waitingList = new ArrayList<>();
        ArrayList<Process> alreadyDone = new ArrayList<>();

        Process current;
        waitingList.add(processList.get(0));
        int time = processList.get(0).getArrivaltime();

        while(0 != processList.size()){
            if(!waitingList.isEmpty()) {
                current = waitingList.remove(0);
                current.setStartTime(time);
                time = time + current.getServiceTime();
                current.setEndTime(time);
                alreadyDone.add(current);
                processList.remove(current);
            }else{
                time = processList.get(0).getArrivalTime();
            }
            for (Process process : processList) {
                if (time >= process.getArrivaltime() && !alreadyDone.contains(process) && !waitingList.contains(process)) {
                    waitingList.add(process);
                }
            }
            waitingList.sort(Comparator.comparingInt(Process::getServiceTime));
        }

        calculateWaitingTime(alreadyDone);
        System.out.println("\n Shortest Job First");
        System.out.println("\t mean TAT: " + calculateMeanTAT(alreadyDone));
        System.out.println("\t mean normalised TAT: " + calculateMeanNormalisedTAT(alreadyDone));
        System.out.println("\t mean waiting time: " + calculateMeanWaitingTime(alreadyDone));
        return alreadyDone;
    }
}
