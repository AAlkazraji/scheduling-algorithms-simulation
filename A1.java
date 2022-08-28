// all the required libraries
import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.lang.String;
import java.util.ArrayList;


public class A1
{
    public static void main (String[] args) throws InterruptedException, CloneNotSupportedException {

        // all the required variables to use while reading from the input file
        // and variables to set the new processes to be used in the scheduling algorithms
        String dataFile = args[0];
        String input = null;
        int DISP = 0;

        ArrayList<Process> processes = new ArrayList<Process>();

        String id = "";
        int Arr = 0;
        int Exc = 0;
        int ticket = 0;
        Process proc;

        File file = new File(dataFile);
        try
        {
            // using scanner class to read the input file
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                input = scanner.next();
                if(input.equalsIgnoreCase("BEGIN"))
                {
                    input = scanner.next();
                    while(!input.equalsIgnoreCase("END"))
                    {
                        input = scanner.next();
                        if(input.equalsIgnoreCase("END"))
                        {
                            break;
                        }
                        // reading the dispatcher time
                        DISP = Integer.parseInt(input);
                    }
                }

                else if (input.equalsIgnoreCase("BEGINRANDOM")) {
                    input = scanner.next();
                    while(scanner.hasNext() && !input.equalsIgnoreCase("ENDRANDOM"))
                    {
                        input = scanner.next();
                    }
                } else if(!input.equalsIgnoreCase("EOF")) {


                    if (input.equalsIgnoreCase("ID:")) {
                        input = scanner.next();
                        id = input;
                    } else if (input.equalsIgnoreCase("Arrive:")) {
                        input = scanner.next();
                        Arr = Integer.parseInt(input);
                    } else if (input.equalsIgnoreCase("ExecSize:")) {
                        input = scanner.next();
                        Exc = Integer.parseInt(input);
                    } else if (input.equalsIgnoreCase("Tickets:")) {
                        input = scanner.next();
                        ticket = Integer.parseInt(input);
                    } else if (input.equalsIgnoreCase("END")) {
                        proc = new Process(id, Arr, Exc);
                        processes.add(proc);
                    }
                } else if(input.equalsIgnoreCase("EOF")) {
                    scanner.close();
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("an error occured while taking inputs");
        }

//      initialising instance from class FCFS
//      send an arraylist of processes from the input file with dispatcher time
//      then call methods to print the results
        ArrayList<Process> FCFSP = cloneList(processes);
        FirstComeFirstServe FCFS = new FirstComeFirstServe(FCFSP, DISP);
        FCFS.FCFS();
        System.out.println("FCFS:");
        FCFS.print();
        FCFS.print1();
        System.out.println("\n");


//      initialising instance from class RR
//      send an arraylist of processes from the input file with dispatcher time
//      then call methods to print the results
        ArrayList<Process> RRP = cloneList(processes);
        RoundRobin RR = new RoundRobin(RRP, DISP);
        RR.RR();
        System.out.println("RR:");
        RR.print();
        RR.print1();
        System.out.println("\n");

//      initialising instance from class NRR
//      send an arraylist of processes from the input file with dispatcher time
//      then call methods to print the results
        ArrayList<Process> cloneList = cloneList(processes);
        NarrowRoundRobin NRR = new NarrowRoundRobin(cloneList, DISP);
        NRR.NRR();
        System.out.println("NRR:");
        NRR.print();
        NRR.print1();

        //      initialising instance from class NRR
//      send an arraylist of processes from the input file with dispatcher time
//      then call methods to print the results
        ArrayList<Process> cloneListS = cloneList(processes);
        ShortestRemainingTime SRT = new ShortestRemainingTime(cloneListS, DISP);
        SRT.SRT();
        System.out.println("\n");
        System.out.println("SRT:");
        SRT.print();
        SRT.print1();

//      initialising instance from class NRR
//      send an arraylist of processes from the input file with dispatcher time
//      then call methods to print the results
        ArrayList<Process> cloneFb = cloneList(processes);
        FeedbackConstant FB = new FeedbackConstant(cloneFb, DISP);
        FB.FC();
        System.out.println("\n");
        System.out.println("FB:");
        FB.print();
        FB.print1();


        System.out.println("\nSummary");
        System.out.println("Algorithm\t\t\tAverage Turnaround Time\t    Waiting Time");
        System.out.println("FCFS:        \t\t" + String.format("%4.2f", FCFS.getTrunaroundtime()) + "\t\t\t\t"+"        " + String.format("%4.2f", FCFS.getWaitingTime()));
        System.out.println("RR:   \t\t\t\t" + String.format("%4.2f", RR.getTrunaroundtime()) + "\t\t\t\t"+"        " + String.format("%4.2f", RR.getWaitingTime()));
        System.out.println("SRT:   \t\t\t\t" + String.format("%4.2f", SRT.getTrunAroundTime()) + "\t\t\t\t"+"        " + String.format("%4.2f", SRT.getWaitingTime()));
        System.out.println("NRR:\t\t\t\t" + String.format("%4.2f", NRR.getTrunaroundtime()) + "\t\t\t\t"+"        " + String.format("%4.2f", NRR.getWaitingTime()));
        System.out.println("FB (constant):\t\t" + String.format("%4.2f", FB.getTrunaroundtime()) + "\t\t\t\t"+"        " + String.format("%4.2f", FB.getWaitingTime()));

    }

    //
    private static ArrayList<Process> cloneList(ArrayList<Process> list) throws CloneNotSupportedException {
        ArrayList<Process> clonedList = new ArrayList<>();
        for(Process p: list){
            clonedList.add(new Process(p.getId(), p.getArriveTime(), p.getExecSize()));
        }
        return clonedList;
    }

}
