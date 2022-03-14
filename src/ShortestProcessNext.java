import java.util.ArrayList;
import java.util.List;


public class ShortestProcessNext extends Scheduler{
    public void process(ArrayList<Process> processList){
        List<Process> waitingList = new ArrayList<>();
        List<Process> alreadyDone = new ArrayList<>();

        int i = 0;
        int time = 0;
        Process current = new Process();

        waitingList.add(processList.get(0));
        time = processList.get(0).getServiceTime();
        while(!waitingList.isEmpty()){
            current = waitingList.remove(0);
            current.setStartTime(time);
            while(time < processList.get(i).getArrivaltime() && !alreadyDone.contains(i)){
                waitingList.add(processList.get(i));
                i++;
            }

        }
    }
}
