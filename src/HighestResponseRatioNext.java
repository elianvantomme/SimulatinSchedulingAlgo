import java.util.ArrayList;

public class HighestResponseRatioNext extends Scheduler{

    public void process(ArrayList<Process> processList){
        ArrayList<Process> readyList = new ArrayList<>();

        int i = 0;
        int finishedProcesses = 0;
        int time = 0;
        while(finishedProcesses != processList.size()) {

            while(i<processList.size()) {
                if(time >= processList.get(i).getArrivalTime()) {
                    readyList.add(processList.get(i));
                    i++;
                }
                else break;
            }

            //for(Process p : processList) System.out.println(p);
            for(Process p : readyList) {
                p.calculateCurrentWaitingTime(time);
                p.calculateTAT();
                p.calculateNormalisedTAT();
            }
            readyList.sort((p1,p2)-> Double.compare(p2.getNormalisedTurnaroundTime(), p1.getNormalisedTurnaroundTime()));


            if(readyList.size() > 0) {
                Process scheduledProcess = readyList.get(0);
                scheduledProcess.setStartTime(time);
                time += scheduledProcess.getServiceTime();
                scheduledProcess.setExecutionTime(scheduledProcess.getServiceTime());
                scheduledProcess.setEndTime(time);
                readyList.remove(scheduledProcess);
                finishedProcesses++;
            }else {
                time++;
            }
        }

        System.out.println("\n Highest Response Ratio Next:");
        System.out.println("\t mean TAT: " + calculateMeanTAT(processList));
        System.out.println("\t mean normalised TAT: " + calculateMeanNormalisedTAT(processList));
        System.out.println("\t mean waiting time: " + calculateMeanWaitingTime(processList));
    }
}
