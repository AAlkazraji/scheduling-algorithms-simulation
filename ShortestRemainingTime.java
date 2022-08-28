// all the required libraries

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.String;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;


    // constructor with 0 arguments
    public class ShortestRemainingTime {

    private ArrayList<Process> processes;
    private final ArrayList<Process> readyP = new ArrayList<>();
    private final ArrayList<Process> print = new ArrayList<>();
    private double trunAroundTime;
    private double waitingTime;
    private int time=0;
    private int counter=0;
    private Process runningProcess = null;


    // constructor with 2 arguments, arraylist of processes and dispatcher time
    public ShortestRemainingTime(ArrayList<Process> newList, int DISP)
    {
        this.processes = newList;
        this.trunAroundTime = 0;
        this.waitingTime = 0;

        // this variables acts like counter to break the while loop
        this.counter = newList.size();
    }

    //precondition: arraylist readyP loaded with process
    //description: returns the process wth least remaining time
    //post-condition: process with least remaining time returned

    public Process selectP(){
        Process min = null;

        if(runningProcess == null)
        {
            runningProcess = readyP.get(0);
        }

        min = runningProcess;

        // for loop that loops through the processes and compare the remaining time
        for (Process process : readyP) {

            if (min.getTimeRemaining() > process.getTimeRemaining()) {
                min = process;
            }

        }
        return min;
    }


    //precondition: arraylist readyP loaded with process
    //description: returns the process that came at the time
    //post-condition: arraylist of processes returned
    public void checkArrivedProcess(){

        ArrayList<Process> newProcesses = new ArrayList<Process>();

        for (Process process : processes) {
            if (process.getArriveTime() <= time) {
                readyP.add(process);
            } else {
                newProcesses.add(process);
            }
        }

        processes = newProcesses;
    }

    public void SRT() throws InterruptedException {

        //initialising processes to be used for simulating srt
        Process lastRuningProc = null;
        Process runningProc = null;

        // int i is another counter to break the while loop when its size == to the size of the arraylist before processing
        int i=0;
        while(i != counter)
        {
            //starts by checking the arrived processes
            checkArrivedProcess();

            //assigning runing process to least remaining time process
            runningProc = selectP();

            // dispatch
            if (lastRuningProc != runningProc) {
                time++;
            }

            // if the remaining time of a running process == 0 the the process has completed
            // add this process to another arraylist to print the results
            // and remove it from the ready processes
            // increase the counter by one
            if(runningProc.getTimeRemaining() == 0)
            {
                runningProc.setCompletionTime(time);

                print.add(runningProc);
                readyP.remove(runningProc);
                i++;
            }
            else {
                // if the remaing time of process is not 0 then decrees it by one and increase the time by one
                runningProc.setTimeRemaining(runningProc.getTimeRemaining() - 1);
                time++;
            }
            lastRuningProc = runningProc;
        }

        // for loop that loops through the processes to calculate the turnaround time and waiting time of the process
        for (Process process : print) {
            process.setTurnAroundTime(process.getCompletionTime() - process.getArriveTime());
            process.setWaitingTime(process.getTurnAroundTime() - process.getExecSize());
            trunAroundTime += process.getTurnAroundTime();
            waitingTime += process.getWaitingTime();

        }
    }

    // calculate and returns the average of the turn around time
    public double getTrunAroundTime()
    {
        return trunAroundTime/print.size();
    }

    // calculate and returns the average of the waiting time
    public double getWaitingTime()
    {
        return waitingTime/print.size();
    }

    // printing the processes by disp
    public void print()
    {
        for (Process process : print) {
            System.out.println("T" + process.getWaitingTime() + ":" + " " + process.getId());
        }
    }

    // printing the waiting time and turnaround time
    public void print1()
    {
        System.out.println(" ");
        System.out.println("Process\tTurnaround Time\t    Waiting Time");
        for (Process process : print) {
            System.out.println(process.getId()+"     " + "\t" + process.getTurnAroundTime()+"          " + "\t\t\t" + process.getWaitingTime());
        }
    }
}







