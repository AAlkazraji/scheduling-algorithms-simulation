public class ExecOrder {
    String pId;
    int time;

    public ExecOrder(String pId, int time) {
        this.pId = pId;
        this.time = time;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
