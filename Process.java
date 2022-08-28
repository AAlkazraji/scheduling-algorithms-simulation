public class Process implements Comparable {


    private String Id;
    private int arrive;
    private int execSize;
    private int completionTime;
    private double turnAroundTime;
    private double waitingTime;
    private int startingTime;
    private int executionTime;
    private boolean completed;
    private boolean isReady;
    private int timeQuantum;
    private int currentQueue;
    private int timeRemaining;

    public int getCurrentQueue() {
        return currentQueue;
    }

    public void setCurrentQueue(int currentQueue) {
        this.currentQueue = currentQueue;
    }

    // constructor takes 0 arguments
    public Process() {
        this.Id = "";
        this.arrive = 0;
        this.execSize = 0;
        this.completionTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.executionTime = 0;
        this.completed = false;
        this.isReady = false;
        this.timeQuantum = 4;
        this.currentQueue = -1;
        this.timeRemaining = 0;
    }


    // constructor takes 3 arguments and set these new data to this class variables
    public Process(String newId, int newArr, int newExSize) {
        this.Id = newId;
        this.arrive = newArr;
        this.execSize = newExSize;
        this.completionTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.executionTime = 0;
        this.completed = false;
        this.isReady = false;
        this.timeQuantum = 4;
        this.currentQueue = -1;
        this.timeRemaining = newExSize;
    }


    // setters

    public void setTimeQuantum(int tq) {
        if(this.timeQuantum > 2) {
            this.timeQuantum = tq;
        }
    }

    public void setProcessId(String data){
        Id = data;
    }

    public void setArriveTime(int data){
        arrive = data;
    }

    public void setExecSize(int data){execSize = data;}

    public void setTurnAroundTime(double data){
        turnAroundTime = data;
    }

    public void setWaitingTime(double data){
        waitingTime = data;
    }

    public void setStartingTime(int data){
        startingTime = data;
    }

    public void setCompletionTime(int time) {this.completionTime = time;}

    public void setReady(boolean ready) {isReady = ready;}

    public void setCompleted(boolean completed) {this.completed = completed;}

    public void setTimeRemaining(int data){timeRemaining = data;}


    //getters

    public int getTimeQuantum() {return timeQuantum;}

    public String getId(){
        return Id;
    }

    public int getArriveTime(){
        return arrive;
    }

    public int getExecSize(){ return execSize; }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isReady() {
        return isReady;
    }



    public double getCompletionTime(){
        return completionTime;
    }

    public double getTurnAroundTime(){
        return turnAroundTime;
    }

    public double getWaitingTime(){
        return waitingTime;
    }

    public double getStartingTime(){
        return startingTime;
    }

    public int getTimeRemaining(){return timeRemaining;}

    // executeProcess method increases the time by 1 for dispatching
    // and checks is the exc size is equal to time then marks process as done
    public void executeProcess(int time){
        this.executionTime += 1;
        if(executionTime == execSize){
            this.completed = true;
            this.completionTime = time;
            this.turnAroundTime = completionTime - arrive;
            this.waitingTime = turnAroundTime - execSize;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Process other = (Process) obj;
        if(other.getId().equalsIgnoreCase(this.getId())){
            return true;
        } else {
            return false;
        }
    }

    // this method to compare the process for printing purposes
    @Override
    public int compareTo(Object o) {
        final Process p = (Process) o;
        int p1 = Integer.parseInt(p.getId().substring(1));
        int p2 = Integer.parseInt(this.getId().substring(1));
        if(p1 > p2) {
            return -1;
        } else if(p1 < p2) {
            return 1;
        } else {
            return 0;
        }
    }
}
