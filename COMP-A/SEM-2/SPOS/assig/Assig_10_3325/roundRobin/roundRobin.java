import java.io.*;
import java.util.*;

class RoundRobin { 
	
	static void findWaitingTime(int n, int bt[], int wt[], int tat[]) { 
		for(int i = 0 ; i < n ; i++){
			wt[i] = tat[i] - bt[i];
		}
	} 

	static void findTurnAroundTime(int n, int ct[], int tat[], int at[]) { 
		for (int i = 0; i < n; i++) { 
			tat[i] = ct[i] - at[i];
		} 
	} 

	static void findPreemptiveAvgTime(int pr[], int n, int bt[], int at[], int quantum) { 
		int[] order = new int[n]; // tells if the process is completed or not
		int[] ct = new int[n]; // stores the completion time for each process
		int[] copyBt = new int[n]; // copy of burst time
		
		Queue<Integer> readyQueue = new LinkedList<>(); // stores the process ready for processing at the given time
		int[] flag = new int[n]; // tells if the process is already added to the ready queue
		
		for(int i = 0 ; i < n ; i++){
			flag[i] = -1; // shows that the process is yet to be completed
			order[i] = -1;
			copyBt[i] = bt[i];
		}
		
		int time = 0; // time at which the process has to be selected
		int cnt = 0; // tells us the no. of process completed
		int index = -1; // process with the shortest burst time selected from the available process each second
		int tempBurst = 100000; // current shortest burst time selected
		
		Vector<Integer> processOrder = new Vector<Integer>();
		Vector<Integer> processTime = new Vector<Integer>();
		
		int prevIndex = -1;
		int debug = 0;
		while(true){
			int execute = -1;
				
			// processing the first process in the ready queue for 'quantum' time if available
			if(readyQueue.size() != 0){
				execute = readyQueue.remove();
				int tempTime = Math.min(copyBt[execute], quantum);
				time += tempTime;
				copyBt[execute] -= tempTime;
				
				processOrder.add(execute);
				processTime.add(time);
				
				if(copyBt[execute] == 0){
					ct[execute] = time;
					order[execute] = cnt;
					cnt++;
				}
			}
			
			if(cnt == n){
				break;
			}
			
			// adding element to the ready queue
			for(int i = 0 ; i < n ; i++){
				if(flag[i] == -1 && at[i] <= time){
					readyQueue.add(i);
					flag[i] = 1;
				}
			}
			
			if(execute > -1 && copyBt[execute] != 0){
				readyQueue.add(execute);
			}
			
			if(readyQueue.size() == 0){
				time++;
			}
				/*
				// debugging 
				if(debug == 10)
					break;
				System.out.println(index+1 + " --> " +copyBt[index] + "  " + flag[index]);
				debug++;
			//------------- end debugging
			*/
			
		}
		
		int[] tat = new int[n];
		findTurnAroundTime(n, ct, tat, at);
		
		int[] wt = new int[n];
		findWaitingTime(n, bt, wt, tat);
		
		int total_wt = 0, total_tat = 0;
		for(int i = 0 ; i < n ; i++){
			total_wt += wt[i];
			total_tat += tat[i];
		}
		
		System.out.println("Quantum : " + quantum + "\n");
		
		System.out.println(String.format("%-7s  %-12s  %-10s  %-15s %-12s  %-16s" , "Process",  "Arrival_time", "Burst_time",  "Completion_time", "Waiting_time", "Turn_around_time"));

		for (int i = 0; i < n; i++) { 
			System.out.println(String.format("%-7d  %-12d  %-10d  %-15d  %-12d  %-16d", (i+1), at[i], bt[i], ct[i], wt[i], tat[i]));
		} 
		
		System.out.print("\nProcess Order :  \n\n");
		
		System.out.print(String.format("%-11s", "Process :"));
		int m = processOrder.size();
		for(int i = 0 ; i < m-1 ; i++){
			System.out.print(String.format("P%-3d-->  ", processOrder.get(i)+1));
		}
		System.out.print(String.format("P%-3d\n", processOrder.get(m-1)+1));
		
		int x = 2;
		while(x != 0){
			System.out.print(String.format("%-11s", ""));
			for(int i = 0 ; i < m ; i++){
				System.out.print(String.format("%-4s     ", "|"));
			}
			System.out.println();
			x--;
		}
		
		System.out.print(String.format("%-11s", "End-Time :"));
		for(int i = 0 ; i < m ; i++){
			System.out.print(String.format("%-4d     ", processTime.get(i)));
		}
		System.out.println("\n\n");
		
		float s = (float)total_wt /(float) n; 
		float t = (float)total_tat /(float)n; 
		System.out.printf("Average waiting time = %f", s); 
		System.out.printf("\n"); 
		System.out.printf("Average turn around time = %f \n\n", t); 
	} 
	
	public static void main(String[] args){ 
		int pr[] = {1, 2, 3, 4}; 
		int n = pr.length; 

		int bt[] = {5, 3, 8, 6}; 
		int at[] = {0, 1, 2, 3};
		int quantum = 3;
	
		System.out.println("************************* PREEMPTIVE ************************\n\n");
		findPreemptiveAvgTime(pr, n, bt, at, quantum); 
	} 
} 

