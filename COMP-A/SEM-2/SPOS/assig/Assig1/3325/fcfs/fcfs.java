import java.io.*;

class FCFS { 

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

	static void findAvgTime(int pr[], int n, int bt[], int at[]) { 
		int[] ct = new int[n]; // stores the completion time for each process
		int[] flag = new int[n]; // tells if the process is completed or not

		for(int i = 0 ; i < n ; i++)
			flag[i] = -1; // shows that the process is yet to be completed
		
		int time = 0; // time at which the process has to be selected
		int cnt = 0;
		for(int i = 0 ; i < n ; i++){
			int index = -1, tempArrival = 100000;
			for(int j = 0 ; j < n ; j++){
				if(flag[j] == -1 && (index == -1 || at[j] < tempArrival)){
					index = j;
					tempArrival = at[j];
				}
			}
	
			time  = Math.max(time, at[index]);
			if(index == -1){
					break;
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
	
	public static void main(String[] args){ 
		int pr[] = {1, 2, 3}; 
		int n = pr.length; 

		int bt[] = {24, 3, 3}; 
		int at[] = {0, 2, 4};

		findAvgTime(pr, n, bt, at); 

	} 
} 

