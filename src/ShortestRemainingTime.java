import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShortestRemainingTime extends Scheduler{
    public void process(ArrayList<Process> processList){
        List<Process> waitingList = new ArrayList<>();
        ArrayList<Process> readyList = new ArrayList<>();

        Process current;
        int counter = 1;
        boolean gaVerder = false;
        int time = processList.get(0).getArrivaltime();
        waitingList.add(processList.get(0));
        while( readyList.size() != processList.size() ) {

            current = waitingList.remove(0);
            if (current.getStartTime() == 0 && current.getArrivalTime() != 0){
                current.setStartTime(time);
            }

            while ((time != processList.get(counter).getArrivalTime() || gaVerder) && current.getRemainingTime() > 0) {
                time++;
                current.descreaseRemainingTime();
            }
            if (current.getRemainingTime() == 0){
                current.setEndTime(time);
                if (waitingList.isEmpty() && readyList.size() != processList.size()-1 ){
                    waitingList.add(processList.get(counter));
                    counter++;
                    time = processList.get(counter).getArrivaltime();
                }
                readyList.add(current);
                waitingList.sort(Comparator.comparingInt(Process::getRemainingTime));

            }else if (time >= processList.get(counter).getArrivalTime()){
                waitingList.add(current);
                waitingList.add(processList.get(counter));
                waitingList.sort(Comparator.comparingInt(Process::getRemainingTime).thenComparing(Process::getArrivalTime));
                if (counter < processList.size()-1){
                    counter++;
                }else{
                    gaVerder = true;
                }
            }
        }
        calculateWaitingTime(readyList);
        System.out.println("\n Shortest Remaining Time");
        System.out.println("\t mean TAT: " + calculateMeanTAT(readyList));
        System.out.println("\t mean normalised TAT: " + calculateMeanNormalisedTAT(readyList));
        System.out.println("\t mean waiting time: " + calculateMeanWaitingTime(readyList));
    }
}
