import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ShortestJobFirst extends Scheduler{
    public void process(ArrayList<Process> processList){
        List<Process> waitingList = new ArrayList<>();
        List<Process> alreadyDone = new ArrayList<>();

        int counter = 0;
        waitingList.add(processList.get(0));
        while(alreadyDone.size() == processList.size()){

        }



        calculateWaitingTime(processList);

        System.out.println("\n Shortest Job First");
        System.out.println("\t mean TAT: " + calculateMeanTAT(processList));
        System.out.println("\t mean normalised TAT: " + calculateMeanNormalisedTAT(processList));
        System.out.println("\t mean waiting time: " + calculateMeanWaitingTime(processList));
    }
}
