import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnaroundTime;
    boolean completed;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.completed = false;
    }
}

public class HRRN {
    public static void hrrnScheduling(List<Process> processes) {
        int time = 0;
        int completedProcesses = 0;
        int n = processes.size();
        final double AGING_WEIGHT = 0.5;

        while (completedProcesses < n) {
            Process target = null;
            double highestResponseRatio = -1;

            for (Process process : processes) {
                if (process.arrivalTime <= time && !process.completed) {
                    double responseRatio = calculateResponseRatio(process, time, AGING_WEIGHT);
                    if (responseRatio > highestResponseRatio) {
                        highestResponseRatio = responseRatio;
                        target = process;
                    }
                }
            }

            if (target == null) {
                time++;
                continue;
            }

            target.waitingTime = time - target.arrivalTime;
            time += target.burstTime;
            target.turnaroundTime = target.waitingTime + target.burstTime;
            target.completed = true;
            completedProcesses++;

            System.out.println("Process " + target.pid);
            System.out.println("Execution Time: " + time);
            System.out.println("Turnaround Time: " + target.turnaroundTime + ", Waiting Time: " + target.waitingTime);
            System.out.println();
        }

        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        for (Process process : processes) {
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
        }

        System.out.println("평균 Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("평균 Turnaround Time: " + (double) totalTurnaroundTime / n);
    }

    public static double calculateResponseRatio(Process process, int currentTime, double aging) {
        int waitingTime = currentTime - process.arrivalTime;
        return (waitingTime + process.burstTime + (waitingTime * aging)) / (double) process.burstTime;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        System.out.print("전체 프로세스의 개수: ");
        int numProcesses = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("각 프로세스의 입력 요소(ID, Arrival Time, Burst Time): ");
            String[] processInfo = sc.nextLine().split(" ");
            int pid = Integer.parseInt(processInfo[0]);
            int arrivalTime = Integer.parseInt(processInfo[1]);
            int burstTime = Integer.parseInt(processInfo[2]);
            processes.add(new Process(pid, arrivalTime, burstTime));
        }
        System.out.println();
        System.out.println("====================================");

        hrrnScheduling(processes);
        sc.close();
    }
}

//pid arrivalTime burstTime
//1 0 8
//2 1 4
//3 2 9
//4 3 5

//        1. class Process 내 필드(각 정책에서 필요한 필드), 생성자 작성
//        2. 각 프로세스를 담기 위한 ArrayList 생성 -> 각 Process add.
//        3. 각 프로세스 정보 입력 받아서 생성자를 통해 Process 객체에 저장.
//        4. 각 정책별 기준에 맞는 Comparator생성 -> 이를 사용하여 ArrayList 정렬.
//        5. 정책별 Scheduling 동작.
//        6. 각 프로세스별 ProcessID, TurnaroundTime, Waiting Time 출력.
//        7. 전체 실행 시간, 평균 Waiting Time 출력.
