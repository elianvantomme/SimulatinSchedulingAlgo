import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;

public class Main {

    static void graphNormalisedTAT(String name){
        processList.sort((p1, p2) -> p1.getServiceTime() - p2.getServiceTime());
        XYSeries series = new XYSeries(name);
        double total=0;
        int teller=0;
        for(int j=0; j<100; j++){
            while (teller < 50000){
                total += processList.get(teller).getNormalisedTurnaroundTime();
                teller++;
                if(teller % 500 == 0){
                    break;
                }
            }
            series.add(j,total/500);
            total=0;
        }
        datasetNormalisedTAT.addSeries(series);
    }

    static void graphWaitTime(String name){
        processList.sort((p1, p2) -> p1.getServiceTime() - p2.getServiceTime());
        XYSeries series = new XYSeries(name);
        double total=0;
        int teller=0;
        for(int j=0; j<100; j++){
            while (teller < 50000){
                total += processList.get(teller).getWaitingTime();
                teller++;
                if(teller % 500 == 0){
                    break;
                }
            }
            series.add(j,total/500);
            total=0;
        }
        datasetWaitTime.addSeries(series);
    }

    static XMLparser parser = new XMLparser("processen50000.xml");
    static ArrayList<Process> processList;
    static XYSeriesCollection datasetNormalisedTAT = new XYSeriesCollection();
    static XYSeriesCollection datasetWaitTime = new XYSeriesCollection();

    public static void main(String[] args) {

        processList = parser.readProcesses();
        FirstComeFirstServe fcfs = new FirstComeFirstServe();
        fcfs.process(processList);
        graphNormalisedTAT("FCFS");
        graphWaitTime("FCFS");
        processList.clear();

        processList = parser.readProcesses();
        ShortestJobFirst spn = new ShortestJobFirst();
        spn.process(processList);
        processList.clear();

        processList = parser.readProcesses();
        RoundRobin rr = new RoundRobin();
        rr.process(processList);
        graphNormalisedTAT("RR");
        graphWaitTime("RR");
        processList.clear();

        processList = parser.readProcesses();
        HighestResponseRatioNext hrrn = new HighestResponseRatioNext();
        hrrn.process(processList);
        graphNormalisedTAT("HRRN");
        graphWaitTime("HRRN");
        processList.clear();

        //normalisedTAT plot
        JFreeChart chartNormalisedTAT = ChartFactory.createXYLineChart(
                "normalised TAT in function of service time",
                "service time",
                "normalised TAT",
                datasetNormalisedTAT,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartFrame frameNormalisedTAT = new ChartFrame("Test", chartNormalisedTAT);
        frameNormalisedTAT.pack();
        frameNormalisedTAT.setVisible(true);

        //wait time plot
        JFreeChart chartWaitTime = ChartFactory.createXYLineChart(
                "wait time in function of service time",
                "service time",
                "wait time",
                datasetWaitTime,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartFrame frameWaitTime = new ChartFrame("Test", chartWaitTime);
        frameWaitTime.pack();
        frameWaitTime.setVisible(true);
    }
}
