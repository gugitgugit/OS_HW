import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Process {
    int processId;
    int burstTime;
    int remainingTime;
    int startTime = 0;
    int completionTime = 0;
    boolean first = true;

    public Process(int processId, int burstTime) {
        this.processId = processId;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}


class RR {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Number of process: ");
        int num = sc.nextInt();

        List<Process> processes = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            System.out.print("Process " + (i + 1) + " Burst Time? : ");
            int burstTime = sc.nextInt();

            processes.add(new Process(i + 1, burstTime));
        }

        System.out.print("Time Quantum? : ");
        int timeQuantum = sc.nextInt();

        int currentTime = 0; 
        int totalWaitingTime = 0; 

        System.out.println("===============================");
        System.out.println("RR 스케줄링 결과");

        int totalRemainingTime = 0;
        for(Process process : processes)
            totalRemainingTime += process.remainingTime;

        while(totalRemainingTime > 0) {
            for(Process curProcess : processes) {
                if(curProcess.remainingTime != 0) {
                    if(curProcess.first) {
                        curProcess.startTime = currentTime;
                        curProcess.first = false;
                    }
                    if(curProcess.remainingTime >= timeQuantum) {
                        curProcess.setRemainingTime(curProcess.remainingTime-timeQuantum);
                        currentTime += timeQuantum;
                    }
                    else {
                        currentTime += curProcess.remainingTime;
                        curProcess.setRemainingTime(0);
                        totalRemainingTime -= curProcess.burstTime;
                        curProcess.completionTime = currentTime;
                        int waitingTime = currentTime - curProcess.burstTime;
                        totalWaitingTime += waitingTime;

                        int turnaroundTime = curProcess.completionTime - curProcess.startTime;
                        System.out.println("\nProcess " + curProcess.processId + 
                                "\nTurnaround Time : " + turnaroundTime +
                                "  Waiting Time : " + waitingTime);
                    }
                }
            }
        }
        System.out.println("\n전체 실행 시간 : " + (double) currentTime);
        System.out.println("평균 대기 시간: " + (double) totalWaitingTime / num);
        System.out.println("===============================");
        sc.close();
    }
}
