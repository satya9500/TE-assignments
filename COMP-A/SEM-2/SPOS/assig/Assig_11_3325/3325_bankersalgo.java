    import java.util.*;   
    import java.io.*;   
    import java.util.Scanner;  
  
class bankers
{
  
// Function to find the need of each process
static void calculateNeed(int need[][], int maxm[][],
                int allot[][],int P,int R)
{
    // Calculating Need of each P
    for (int i = 0 ; i < P ; i++)
        for (int j = 0 ; j < R ; j++)
  
            // Need of instance = maxm instance -
            //                 allocated instance
            need[i][j] = maxm[i][j] - allot[i][j];
}
  
// Function to find the system is in safe state or not
static boolean isSafe(int processes[], int avail[], int maxm[][],
            int allot[][],int P,int R)
{
    int [][]need = new int[P][R];
  
    // Function to calculate need matrix
    calculateNeed(need, maxm, allot,P,R);
  
    // Mark all processes as infinish
    boolean []finish = new boolean[P];
  
    // To store safe sequence
    int []safeSeq = new int[P];
  
    // Make a copy of available resources
    int []work = new int[R];
    for (int i = 0; i < R ; i++)
        work[i] = avail[i];
  
    // While all processes are not finished
    // or system is not in safe state.
    int count = 0;
    while (count < P)
    {
        // Find a process which is not finish and
        // whose needs can be satisfied with current
        // work[] resources.
        boolean found = false;
        for (int p = 0; p < P; p++)
        {
            // First check if a process is finished,
            // if no, go for next condition
            if (finish[p] == false)
            {
                // Check if for all resources of
                // current P need is less
                // than work
                int j;
                for (j = 0; j < R; j++)
                    if (need[p][j] > work[j])
                        break;
  
                // If all needs of p were satisfied.
                if (j == R)
                {
                    // Add the allocated resources of
                    // current P to the available/work
                    // resources i.e.free the resources
                    for (int k = 0 ; k < R ; k++)
                        work[k] += allot[p][k];
  
                    // Add this process to safe sequence.
                    safeSeq[count++] = p;
  
                    // Mark this p as finished
                    finish[p] = true;
  
                    found = true;
                }
            }
        }
  
        // If we could not find a next process in safe
        // sequence.
        if (found == false)
        {
            System.out.print("System is not in safe state hence deadlock has occured");
            return false;
        }
    }
  
    // If system is in safe state then
    // safe sequence will be as below
    System.out.print("System is in safe state hence no deadlock is there.\nSafe"
        +" sequence is: ");
    for (int i = 0; i < P ; i++)
        System.out.print(safeSeq[i] + " ");
  
    return true;
}
  
// Driver code
public static void main(String[] args) 
{   int P, R;  
              
            //create scanner class object to get input from user  
            Scanner sc = new Scanner(System.in);  
              
            // get total number of resources from the user  
            System.out.println("Enter total number of processes");  
            P = sc.nextInt();  
              
            // get total number of resources from the user  
            System.out.println("Enter total number of resources");  
            R = sc.nextInt();  
              
            int processes[] = new int[P];  
            for(int i = 0; i < P; i++){  
                processes[i] = i;  
            }  
              
            int avail[] = new int[R];  
            for( int i = 0; i < R; i++){  
                System.out.println("Enter the availability of resource"+ i +": ");  
                avail[i] = sc.nextInt();  
            }  	


    // Maximum R that can be allocated
    // to processes
	
    int maxm[][] = new int[P][R];  
            for( int i = 0; i < P; i++){  
                for( int j = 0; j < R; j++){  
                    System.out.println("Enter the maximum resource"+ j +" that can be allocated to process"+ i +": ");  
                    maxm[i][j] = sc.nextInt();  
                }  
            }  
  
    // Resources allocated to processes
    int allot[][] = new int[P][R];  
            for( int i = 0; i < P; i++){  
                for( int j = 0; j < R; j++){  
                    System.out.println("How many instances of resource"+ j +" are allocated to process"+ i +"? ");  
                    allot[i][j] = sc.nextInt();  
                }  
            }  
    // Check system is in safe state or not
    isSafe(processes, avail, maxm, allot,P,R);
}
}