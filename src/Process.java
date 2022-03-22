public class Process {
    private int pId;
    private int arrivalTime;
    private int serviceTime;
    private int waitingTime;
    private int startTime;
    private int endTime;
    private int turnaroundTime;
    private double normalisedTurnaroundTime;
    private int executionTime;

    public Process(){
        pId = 0;
        arrivalTime = 0;
        serviceTime = 0;
        startTime = 0;
        endTime = 0;
        turnaroundTime = 0;
        normalisedTurnaroundTime = 0.0;
        waitingTime=0;
    }

    public Process(int pId, int arrivalTime, int serviceTime) {
        this.pId = pId;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.executionTime = 0;
        this.waitingTime = 0;
        this.startTime = 0;
        this.endTime = 0;
        this.turnaroundTime = 0;
        this.normalisedTurnaroundTime = 0.0;
    }



    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public double getNormalisedTurnaroundTime() {
        return normalisedTurnaroundTime;
    }

    public void setNormalisedTurnaroundTime(double normalisedTurnaroundTime) {
        this.normalisedTurnaroundTime = normalisedTurnaroundTime;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void setArrivaltime(int arrivaltime) {
        this.arrivalTime = arrivaltime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }



    @Override
    public String toString() {
        return "Process [pId=" + pId + ", arrivaltime=" + arrivalTime + ", serviceTime=" + serviceTime + ", waitingTime=" + waitingTime + ", startTime="
                + startTime + ", endTime=" + endTime + ", turnaroundTime=" + turnaroundTime
                + ", normalisedTurnaroundTime=" + normalisedTurnaroundTime  + "]";
    }



    public int getpId() {
        return pId;
    }

    public int getArrivaltime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }


    public void calculateTAT() {
        turnaroundTime = serviceTime + waitingTime;
    }

    public void calculateNormalisedTAT() {
        normalisedTurnaroundTime = (double) turnaroundTime / serviceTime;
    }

    public void calculateCurrentWaitingTime(int currentTime) {
        waitingTime = currentTime - arrivalTime;
    }
}
