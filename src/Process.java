public class Process {
    private int pId;
    private int arrivaltime;
    private int serviceTime;

    public Process(){
        pId = 0;
        arrivaltime = 0;
        serviceTime = 0;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void setArrivaltime(int arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
}
