import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Process {
    int processId;
    int arrivalTime;
    int burstTime;

    public Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

class ProcessArrivalTimeComparator implements Comparator<Process> {
    @Override
    public int compare(Process p1, Process p2) {
        if(p1.arrivalTime > p2.arrivalTime) {
            return 1;
        } else if(p1.arrivalTime < p2.arrivalTime) {
            return -1;
        }
        return 0;
    }
}

class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Number of process: ");
        int num = sc.nextInt();

        List<Process> processes = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            System.out.print("Process " + (i + 1) + " Arrival Time? : ");
            int arrivalTime = sc.nextInt();
            System.out.print("Process " + (i + 1) + " Burst Time? : ");
            int burstTime = sc.nextInt();

            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }

        Collections.sort(processes, new ProcessArrivalTimeComparator());

        
        int currentTime = 0; 
        int totalWaitingTime = 0; 


        System.out.println("===============================");
        System.out.println("FCFS 스케줄링 결과");

        for (Process process : processes) {
            int waitingTime = currentTime - process.arrivalTime;
            int startTime = currentTime;
            int completionTime = currentTime + process.burstTime;
            int turnaroundTime = completionTime - startTime;

            totalWaitingTime += waitingTime;

            System.out.println("\nProcess " + process.processId + 
            "\nTurnaround Time : " + turnaroundTime +
            "  Waiting Time : " + waitingTime);

            currentTime = completionTime;
        }

        System.out.println("\n전체 실행 시간 : " + (double) currentTime);
        System.out.println("평균 대기 시간: " + (double) totalWaitingTime / num);
        System.out.println("===============================");
        sc.close();
    }
}