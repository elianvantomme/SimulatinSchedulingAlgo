import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
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
            while (teller < numberOfProcesses){
                total += processList.get(teller).getNormalisedTurnaroundTime();
                teller++;
                if(teller % (numberOfProcesses / 100) == 0){
                    break;
                }
            }
            double mean = total / (numberOfProcesses / 100);
            series.add(j,mean);
            total=0;
        }
        datasetNormalisedTAT.addSeries(series);
    }

    static void graphWaitTime(String name){
        processList.sort((p1, p2) -> p1.getServiceTime() - p2.getServiceTime());
        XYSeries series = new XYSeries(name);
        double totalJIFFY=0;
        int teller=0;
        for(int j=0; j<100; j++){
            while (teller < numberOfProcesses){
                totalJIFFY += processList.get(teller).getWaitingTime();
                teller++;
                if(teller % (numberOfProcesses / 100) == 0){
                    break;
                }
            }
            double gemiddeldeAantalJIFFY = totalJIFFY / (numberOfProcesses / 100);
            series.add(j,gemiddeldeAantalJIFFY * Math.pow(10, -2)); // 1 jiffy = 10ms
            totalJIFFY=0;
        }
        datasetWaitTime.addSeries(series);
    }


    static ArrayList<Process> processList;
    static XYSeriesCollection datasetNormalisedTAT = new XYSeriesCollection();
    static XYSeriesCollection datasetWaitTime = new XYSeriesCollection();
    static int numberOfProcesses = 10000;
    public static void main(String[] args) {

        String file = "processen" + numberOfProcesses + ".xml";
        XMLparser parser = new XMLparser(file);

//        processList = parser.readProcesses();
//        double total=0.0;
//        for(Process p : processList){
//            System.out.println(p.getServiceTime());
//            total+=p.getServiceTime();
//        }
//        System.out.println("mean service time: "+ total / numberOfProcesses);

        // mediaan
        processList = parser.readProcesses();
        processList.sort((p1, p2) -> p1.getServiceTime() - p2.getServiceTime());
        System.out.println("mediaan: " + processList.get(processList.size() / 2).getServiceTime());


        processList = parser.readProcesses();
        FirstComeFirstServe fcfs = new FirstComeFirstServe();
        fcfs.process(processList);
        graphNormalisedTAT("FCFS");
        graphWaitTime("FCFS");
        processList.clear();


        processList = parser.readProcesses();
        ShortestRemainingTime srt = new ShortestRemainingTime();
        srt.process(processList);
        graphNormalisedTAT("SRT");
        graphWaitTime("SRT");
        processList.clear();

        processList = parser.readProcesses();
        ShortestJobFirst spn = new ShortestJobFirst();
        processList = spn.process(processList);
        graphNormalisedTAT("SJF");
        graphWaitTime("SJF");
        processList.clear();

        processList = parser.readProcesses();
        RoundRobin rr2 = new RoundRobin(2);
        rr2.process(processList);
        graphNormalisedTAT("RR2");
        graphWaitTime("RR2");
        processList.clear();

        processList = parser.readProcesses();
        RoundRobin rr4 = new RoundRobin(4);
        rr4.process(processList);
        graphNormalisedTAT("RR4");
        graphWaitTime("RR4");
        processList.clear();

        processList = parser.readProcesses();
        RoundRobin rr8 = new RoundRobin(8);
        rr8.process(processList);
        graphNormalisedTAT("RR8");
        graphWaitTime("RR8");
        processList.clear();

        processList = parser.readProcesses();
        HighestResponseRatioNext hrrn = new HighestResponseRatioNext();
        hrrn.process(processList);
        graphNormalisedTAT("HRRN");
        graphWaitTime("HRRN");
        processList.clear();


        processList = parser.readProcesses();
        MultilevelFeedback fbV1 = new MultilevelFeedback();
        fbV1.process(processList);
        graphNormalisedTAT("FBV1");
        graphWaitTime("FBV1");
        processList.clear();

        processList = parser.readProcesses();
        MultilevelFeedbackDifferentTimeSlices fbV2 = new MultilevelFeedbackDifferentTimeSlices();
        fbV2.process(processList);
        graphNormalisedTAT("FBV2");
        graphWaitTime("FBV2");
        processList.clear();

        //normalisedTAT plot
        JFreeChart chartNormalisedTAT = ChartFactory.createXYLineChart(
                "normalised TAT in function of service time",
                "Percentile of time required",
                "normalised TAT",
                datasetNormalisedTAT,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot xyPlot = chartNormalisedTAT.getXYPlot();
        xyPlot.getRangeAxis().setRange(0.0, 100);

        ChartFrame frameNormalisedTAT = new ChartFrame("Test", chartNormalisedTAT);
        frameNormalisedTAT.pack();
        frameNormalisedTAT.setVisible(true);


        //wait time plot
        JFreeChart chartWaitTime = ChartFactory.createXYLineChart(
                "wait time in function of service time",
                "Percentile of time required",
                "wait time (s)",
                datasetWaitTime,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        xyPlot = chartWaitTime.getXYPlot();
        xyPlot.getRangeAxis().setRange(0.0, 15);

        ChartFrame frameWaitTime = new ChartFrame("Test", chartWaitTime);
        frameWaitTime.pack();
        frameWaitTime.setVisible(true);
    }
}
