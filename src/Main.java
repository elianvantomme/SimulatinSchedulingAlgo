import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        XMLparser parser = new XMLparser("processen50000.xml");
        ArrayList<Process> processList = parser.readProcesses();

        FirstComeFirstServe fcfs = new FirstComeFirstServe();
        fcfs.process(processList);
        processList.clear();

        processList = parser.readProcesses();
        ShortestJobFirst spn = new ShortestJobFirst();
        spn.process(processList);
        processList.clear();

        processList = parser.readProcesses();
        RoundRobin rr = new RoundRobin();
        rr.process(processList);
        processList.clear();


        //processList.sort((p1, p2) -> p1.getArrivaltime() - p2.getArrivaltime());



//		for(int i=0; i<5; i++) {
//			System.out.println(processList.get(i));
//		}

            }

}
