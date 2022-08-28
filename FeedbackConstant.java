import java.math.BigInteger;
import java.util.*;

// constructor with 0 arguments
public class FeedbackConstant {

    private ArrayList<Process> processes;
    private ArrayList<ExecOrder> execOrders;

    // 6 level priority starting from 0 is the highest
    private ArrayList<Process> readyQueue0;
    private ArrayList<Process> readyQueue1;
    private ArrayList<Process> readyQueue2;
    private ArrayList<Process> readyQueue3;
    private ArrayList<Process> readyQueue4;
    private ArrayList<Process> readyQueue5;

    private ArrayList<Process> completedQueue;
    private final int timeQuantum = 4;
    private double turnAroundTime;
    private double waitingTime;
    int completeProcesses;
    private int time = 0;
    private final int DISP;


    // constructor with 2 arguments, arraylist of processes and dispatcher time
    public FeedbackConstant(ArrayList<Process> newList, int DISP) {
        this.execOrders = new ArrayList<>();
        this.completedQueue = new ArrayList<>();
        this.processes = newList;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.DISP = DISP;
        this.completeProcesses = 0;
        // Feedback
        this.readyQueue0 = new ArrayList<>();
        this.readyQueue1 = new ArrayList<>();
        this.readyQueue2 = new ArrayList<>();
        this.readyQueue3 = new ArrayList<>();
        this.readyQueue4 = new ArrayList<>();
        this.readyQueue5 = new ArrayList<>();
    }


    // this method will get a process object passed to it
    // and passed on what previous queue the process was it gets
    // allocated otherwise it will be allocated in the highest queue
    private void allocateProcess(Process p) {
        if (p.getCurrentQueue() == -1) {
            p.setCurrentQueue(0);
            readyQueue0.add(p);
            return;
        } else if (p.getCurrentQueue() == 0) {
            p.setCurrentQueue(1);
            readyQueue1.add(p);
            return;
        } else if (p.getCurrentQueue() == 1) {
            p.setCurrentQueue(2);
            readyQueue2.add(p);
            return;
        } else if (p.getCurrentQueue() == 2) {
            p.setCurrentQueue(3);
            readyQueue3.add(p);
            return;
        } else if (p.getCurrentQueue() == 3) {
            p.setCurrentQueue(4);
            readyQueue4.add(p);
            return;
        }
        readyQueue5.add(p);
        p.setCurrentQueue(5);
    }


    // this method will be called in every second and every CPU cycle
    // it takes boolean variable to indicate if the process that passed in
    // is ready to be switched and sets it to be read to processing
    public void checkArrivedProcess(boolean isSwitch) {
        for (Process process : processes) {
            if (isSwitch) {
                if (process.getArriveTime() <= time && process.getArriveTime() >= (time - DISP) && !process.isReady()) { // if process arrived at the current time or earlier then added to the ready queue,
                    process.setReady(true);                                                                              // or if the process arrived at time where the cpu dispatching a new process then
                    process.setCurrentQueue(0);                                                                          // it needs to be added to the ready queue
                    readyQueue0.add(process);
                }
            } else {
                if (process.getArriveTime() == time) {
                    process.setReady(true);
                    process.setCurrentQueue(0);
                    readyQueue0.add(process);
                }
            }
        }
    }


    // this method will be called when cpu finish processing
    // current process and ready to fetch the next process
    // will remove and return the head of the ready queue if
    // the ready queue is not null otherwise will move to lower priority queue and fetch,
    // if all 6 queues finished processing then return null to indicate
    // that the process will be running ideal until next process arrives
    private Process getNextProcess() {
        if (readyQueue0.size() > 0) {
            return readyQueue0.remove(0);
        } else if (readyQueue1.size() > 0) {
            return readyQueue1.remove(0);
        } else if (readyQueue2.size() > 0) {
            return readyQueue2.remove(0);
        } else if (readyQueue3.size() > 0) {
            return readyQueue3.remove(0);
        } else if (readyQueue4.size() > 0) {
            return readyQueue4.remove(0);
        } else if (readyQueue5.size() > 0) {
            return readyQueue5.remove(0);
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
        completeProcesses += 1;
    }

    // this method will return the next process to be processed by calling getNextProcsses()
    // and add dispatching time to the current time
    // then added the process to execuOrder array to print the order of switching process
    private Process switchProcess() {
        Process nextProcess = getNextProcess();
        if (nextProcess != null) {
            time += DISP;
            execOrders.add(new ExecOrder(nextProcess.getId(), time));
        }
        return nextProcess;
    }

    // this method will go through all the
    // 6 queues and check if they all empty then return false
    private boolean anyReadyProcess(){
       if(readyQueue0.size() > 0 || readyQueue1.size() > 0 || readyQueue2.size() > 0 || readyQueue3.size() > 0 || readyQueue4.size() > 0 || readyQueue5.size() > 0){
           return true;
       }
        return false;
    }

    public void FC() {
        boolean isSwitch = false;
        int currentExecTime = 0;
        Process runningProcess = null;
        while (completeProcesses != processes.size()) {
            checkArrivedProcess(isSwitch);
            isSwitch = false;
            if (runningProcess == null) {
                runningProcess = getNextProcess();
                time += 1;
                if (runningProcess != null) {
                    execOrders.add(new ExecOrder(runningProcess.getId(), time));
                }
            } else {
                if (currentExecTime == timeQuantum) {
                    if (runningProcess.isCompleted() && anyReadyProcess()) { // switch
                        time += 1;
                        markProcessAsDone(runningProcess);
                        runningProcess = switchProcess();
                        isSwitch = true;
                        currentExecTime = 0;
                    } else if (anyReadyProcess()) {  //switch
                        allocateProcess(runningProcess);
                        runningProcess = switchProcess();
                        isSwitch = true;
                        currentExecTime = 0;
                    } else if (!runningProcess.isCompleted()) {
                        currentExecTime = 1;
                        time += 1;
                        runningProcess.executeProcess(time);
                    }
                } else if (runningProcess.isCompleted()) { // switch
                    markProcessAsDone(runningProcess);
                    runningProcess = switchProcess();
                    isSwitch = true;
                    currentExecTime = 0;
                } else {
                    currentExecTime += 1;
                    time += 1;
                    runningProcess.executeProcess(time);
                }
            }
//            System.out.println(completedProcesses.size() + " " + processes.size());
        }
    }


    // calculate and returns the average of the turn around time
    public double getTrunaroundtime() {
        return turnAroundTime / processes.size();
    }

    // calculate and returns the average of the waiting time
    public double getWaitingTime() {
        return waitingTime / processes.size();
    }

    // printing the processes by disp
    public void print() {
        for (ExecOrder eo : execOrders) {
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

