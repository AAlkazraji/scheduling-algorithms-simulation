import java.util.*;

// constructor with 0 arguments
    public class FirstComeFirstServe {
    private ArrayList<Process> processes;
    private ArrayList<ExecOrder> execOrders;
    private ArrayList<Process> readyQueue;
    private ArrayList<Process> completedQueue;
    private final int timeQuantum= 4;
    private double turnAroundTime;
    private double waitingTime;
    int completeProcesses;
    private int time = 0;
    private final int DISP;


    // constructor with 2 arguments, arraylist of processes and dispatcher time
    public FirstComeFirstServe(ArrayList<Process> newList, int DISP)
    {
        this.execOrders = new ArrayList<>();
        this.readyQueue = new ArrayList<>();
        this.completedQueue = new ArrayList<>();
        this.processes = newList;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.DISP = DISP;
        this.completeProcesses = 0;
    }

    // this method will be called in every second and every CPU cycle
    // it takes boolean variable to indicate if the process that passed in
    // is ready to be switched and sets it to be read to processing
    public void checkArrivedProcess(boolean isSwitch){
        for (Process process : processes) {
            if(isSwitch){
                if (process.getArriveTime()  <= time && process.getArriveTime() >= (time - DISP)) { // if process arrived at the current time or earlier then added to the ready queue,
                    process.setReady(true);                                                         // or if the process arrived at time where the cpu dispatching a new process then
                    readyQueue.add(process);                                                        // it needs to be added to the ready queue
                }
            } else {
                if (process.getArriveTime()  == time) {    // if process arrived at the current time then added to the ready queue
                    process.setReady(true);
                    readyQueue.add(process);
                }
            }
        }
    }

    // this method will be called when cpu finish processing
    // current process and ready to fetch the next process
    // will remove and return the head of the ready queue if
    // the ready queue is not null other wise will return null to indicate
    // that the process will be running ideal until next process arrarives
    private Process getNextProcess() {
        if(readyQueue.size() > 0) {
            return readyQueue.remove(0);
        } else {
            return null;
        }
    }

    // this methode will be called when the passed in process has finished
    // marks the process as its not ready to be processed
    // sets the turn around time and waiting time
    // and then adds it to the completed process array to be printed
    private void markProcessAsDone(Process p) {
        p.setReady(false);
        completedQueue.add(p);
        turnAroundTime += p.getTurnAroundTime();
        waitingTime += p.getWaitingTime();
        completeProcesses +=1;
    }

    // this method will return the next process to be processed by calling getNextProcsses()
    // and add dispatching time to the current time
    // then added the process to execuOrder array to print the order of switching process
    private Process switchProcess(){
        Process nextProcess = getNextProcess();
        if(nextProcess != null) {
            time+=DISP;
            execOrders.add(new ExecOrder(nextProcess.getId(), time));
        }
        return nextProcess;
    }

    public void FCFS() {
        boolean isSwitch = false;
        int currentExecTime = 0;
        Process runningProcess = null;

        // here the programme will stop when the size of the
        // completed = to the size of the initial size of the array that contains process that passed to this class
        while(completeProcesses != processes.size()){

            // checking for the arrived process
            checkArrivedProcess(isSwitch);
            isSwitch = false;

            // if there are no process to fetch then increase the time by 1
            if(runningProcess == null) {
                runningProcess = getNextProcess();
                time+=1;

                // if there are processes to fetch then add them to the exucOrder array
                if(runningProcess != null) {
                    execOrders.add(new ExecOrder(runningProcess.getId(), time));
                }

            // if there are process to fetch then check if they completed
            // if they not completed then execute the process
            // then increase the time
            } else {
                if (runningProcess.isCompleted()) { // switch
                    markProcessAsDone(runningProcess);
                    runningProcess = switchProcess();
                    isSwitch = true;
                    currentExecTime = 0;
                } else {
                    currentExecTime+=1;
                    time+=1;
                    runningProcess.executeProcess(time);
                }
            }
        }
    }

    // calculate and returns the average of the turn around time
    public double getTrunaroundtime()
    {
        return turnAroundTime /processes.size();
    }

    // calculate and returns the average of the waiting time
    public double getWaitingTime()
    {
        return waitingTime/processes.size();
    }

    // printing the processes by disp
    public void print()
    {
        for(ExecOrder eo: execOrders){
            System.out.println("T" + eo.getTime() + ":" + " " + eo.getpId());
        }
    }

    // printing the waiting time and turnaround time
    public void print1()
    {
        System.out.println(" ");
        System.out.println("Process\t\tTurnaround Time\t\t    Waiting Time");
        Collections.sort(completedQueue);
        for (Process process : completedQueue) {
            System.out.println(process.getId() +  "\t\t\t" + Math.round(process.getTurnAroundTime()) +  "            \t\t\t" + Math.round(process.getWaitingTime()));
        }
    }
}

