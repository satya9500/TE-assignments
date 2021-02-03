import java.io.*;
import java.util.*;

class PRIORITY { 
	
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

	static void findNonPreemptiveAvgTime(int pr[], int n, int bt[], int at[], int priority[]) { 
		int[] processOrder = new int[n]; // stores the order of the process
		int[] flag = new int[n]; // tells if the process is completed or not
		int[] ct = new int[n]; // stores the completion time for each process
		
		for(int i = 0 ; i < n ; i++)
			flag[i] = -1; // shows that the process is yet to be completed
		
		int time = 0; // time at which the process has to be selected
		int cnt = 0; // tells us the no. of process completed
		while(true){
			int index = -1; // process with the shortest burst time selected from the available process according to the current time
			int tempPriority = -1; // tells us the current burst time selected
			int tempArrival = -1;
			for(int i = 0 ; i < n ; i++){
				if(flag[i] == -1 && (at[i] <= time && (index == -1 || priority[i] > tempPriority || (priority[i] == tempPriority && at[i] < tempArrival)))){
					index = i;
					tempPriority = bt[i];
					tempArrival = at[i];
				}
			}
			
			if(index == -1){
				if(cnt == n)
					break;
				time++;
			}
			else{
				ct[index] = time + bt[index];
				time += bt[index];
				flag[index] = cnt;
				cnt++;
			}
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
		
		System.out.println(String.format("%-7s  %-12s  %-10s  %-8s  %-15s %-12s  %-16s" , "Process",  "Arrival_time", "Burst_time", "Priority",  "Completion_time", "Waiting_time", "Turn_around_time"));

		for (int i = 0; i < n; i++) { 
			System.out.println(String.format("%-7d  %-12d  %-10d  %-8d  %-15d  %-12d  %-16d", (i+1), at[i], bt[i], priority[i], ct[i], wt[i], tat[i]));
		} 
		
		System.out.print("\nProcess Order :  \n\n");
		System.out.print(String.format("%-11s", "Process :"));
		for(int i = 0 ; i < n-1 ; i++){
			for(int j = 0 ; j < n ; j++){
				if(flag[j] == i)
					System.out.print(String.format("P%-3d-->  ", (j+1)));
			}
		}
		
		for(int j = 0 ; j < n ; j++){
				if(flag[j] == n-1)
					System.out.println(String.format("P%-3d", (j+1)));
		}
		
		int x = 2;
		while(x != 0){
			System.out.print(String.format("%-11s", ""));
			for(int i = 0 ; i < n ; i++){
				System.out.print(String.format("%-4s     ", "|"));
			}
			System.out.println();
			x--;
		}
		
		System.out.print(String.format("%-11s", "End-Time :"));
		for(int i = 0 ; i < n-1 ; i++){
			for(int j = 0 ; j < n ; j++){
				if(flag[j] == i)
					System.out.print(String.format("%-4d     ", ct[j]));
			}
		}
		
		for(int j = 0 ; j < n ; j++){
				if(flag[j] == n-1)
					System.out.println(String.format("%-4d", ct[j]));
		}
		
		System.out.println("\n");
		
		float s = (float)total_wt /(float) n; 
		float t = (float)total_tat /(float)n; 
		System.out.printf("Average waiting time = %f", s); 
		System.out.printf("\n"); 
		System.out.printf("Average turn around time = %f \n\n", t); 
	} 
	
	public static void main(String[] args){ 
		int pr[] = {1, 2, 3, 4, 5}; 
		int n = pr.length; 

		int bt[] = {4, 3, 1, 5, 2}; 
		int at[] = {0, 1, 2, 3, 4};
		int priority[] = {2, 3, 4, 5, 5};

		System.out.println("********************** NON - PREEMPTIVE *********************\n\n");
		findNonPreemptiveAvgTime(pr, n, bt, at, priority); 
	} 
} 

