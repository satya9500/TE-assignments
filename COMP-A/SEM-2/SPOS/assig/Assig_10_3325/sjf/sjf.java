import java.io.*;
import java.util.*;

class SJF { 
	
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

	static void findNonPreemptiveAvgTime(int pr[], int n, int bt[], int at[]) { 
		int[] processOrder = new int[n]; // stores the order of the process
		int[] flag = new int[n]; // tells if the process is completed or not
		int[] ct = new int[n]; // stores the completion time for each process
		
		for(int i = 0 ; i < n ; i++)
			flag[i] = -1; // shows that the process is yet to be completed
		
		int time = 0; // time at which the process has to be selected
		int cnt = 0; // tells us the no. of process completed
		while(true){
			int index = -1; // process with the shortest burst time selected from the available process according to the current time
			int tempBurst = 100000; // tells us the current burst time selected
			for(int i = 0 ; i < n ; i++){
				if(flag[i] == -1 && (at[i] <= time && (index == -1 || bt[i] < tempBurst))){
					index = i;
					tempBurst = bt[i];
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
		
		System.out.println(String.format("%-7s  %-12s  %-10s  %-15s %-12s  %-16s" , "Process",  "Arrival_time", "Burst_time",  "Completion_time", "Waiting_time", "Turn_around_time"));

		for (int i = 0; i < n; i++) { 
			System.out.println(String.format("%-7d  %-12d  %-10d  %-15d  %-12d  %-16d", (i+1), at[i], bt[i], ct[i], wt[i], tat[i]));
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
	
	static void findPreemptiveAvgTime(int pr[], int n, int bt[], int at[]) { 
		int[] flag = new int[n]; // tells if the process is completed or not
		int[] ct = new int[n]; // stores the completion time for each process
		int[] copyBt = new int[n]; // copy of burst time
		
		for(int i = 0 ; i < n ; i++){
			flag[i] = -1; // shows that the process is yet to be completed
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
			index = -1;
			for(int i = 0 ; i < n ; i++){
				if(flag[i] == -1 && (at[i] <= time && (index == -1 || copyBt[i] < tempBurst))){
					index = i;
					tempBurst = bt[i];
				}
			}
				/*
				// debugging 
				if(debug == 10)
					break;
				System.out.println(index+1 + " --> " +copyBt[index] + "  " + flag[index]);
				debug++;
			//------------- end debugging
			*/
			if(index == -1){
				if(cnt == n)
					break;
			}
			else{
				copyBt[index]--;
				
				if(prevIndex != -1 && prevIndex != index && copyBt[prevIndex] != 0){
					processOrder.add(prevIndex);
					processTime.add(time-1);
				}
				
				if(copyBt[index] == 0){
					flag[index] = 1;
					ct[index] = time+1;
					cnt++;
					processOrder.add(index);
					processTime.add(time);
				}
				
				prevIndex = index;
			}
			
			time++;
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
			System.out.print(String.format("%-4d     ", processTime.get(i)+1));
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

		int bt[] = {7, 4, 1, 4}; 
		int at[] = {0, 2, 4, 5};

		System.out.println("********************** NON - PREEMPTIVE *********************\n\n");
		findNonPreemptiveAvgTime(pr, n, bt, at); 

		System.out.println("\n\n");
	
		System.out.println("************************* PREEMPTIVE ************************\n\n");
		findPreemptiveAvgTime(pr, n, bt, at); 
	} 
} 

