import java.util.Scanner;

class linkState{
	
	public static void dijkstra(int[][] arr , int[] cost , int[] prev , int source , int nodes){ // updates the cost and the previous node array for a perticular source
	
		int visited[] = new int[nodes+1];
	
		for(int i = 1 ; i <= nodes ; i++){
			cost[i] = prev[i] = visited[i] = -1;
		}
		
		cost[source] = 0;
		prev[source] = source;
		
		while(true){
			int nodeIndex = -1;
			int temp = Integer.MAX_VALUE;
			
			for(int i = 1 ; i <= nodes ; i++){
				if(visited[i] == -1 && cost[i] != -1 && cost[i] < temp){
					temp = cost[i];
					nodeIndex = i;
				}
			}
			
			if(nodeIndex == -1){
				break;
			}
			
			visited[nodeIndex] = 1;
			
			for(int j = 1 ; j <= nodes ; j++){
				if(visited[j] == -1 && arr[nodeIndex][j] != -1 && (arr[nodeIndex][j] + cost[nodeIndex] < cost[j] || cost[j] == -1)){
					cost[j] = arr[nodeIndex][j] + cost[nodeIndex];
					prev[j] = nodeIndex;	
				}
			}
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
	public static int getNext(int[] prev , int j , int source){ // returns the next node in the shortest path from the source
		if(prev[j] == source)
			return j;
			
		return getNext(prev , prev[j] , source);
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
	public static void main(String []args){
		Scanner read = new Scanner(System.in);
		
		// taking the number of nodes as input
		System.out.print("Enter the number of routers in the network : ");
		
		int nodes = read.nextInt();
		
		// taking the number of edges as input
		System.out.print("Enter the number of links in network : ");
		
		int edges = read.nextInt();
		
		int arr[][] = new int[nodes+1][nodes+1];
		
		for(int i = 0 ; i <= nodes ; i++){
			for(int j = 0 ; j <= nodes ; j++){
				arr[i][j] = -1;
				
				if(i == j){
					arr[i][j] = 0;
				}
			}
		}
		
		// taking the input for edge details
		System.out.println("Enter the router pair and the cost to connect them : ");
		
		for(int i = 0 ; i < edges ; i++){
			int x , y , w;
			x = read.nextInt();
			y = read.nextInt();
			w = read.nextInt();
			
			arr[x][y] = w;
			arr[y][x] = w;
		}
		
		// printing the initial routing tables
		
		for(int i = 1 ; i <= nodes ; i++){
		
			System.out.println("-----------------------------------------");
			System.out.println("\nInitial Routing table for router " + i + " : \n");
			
			for(int j = 1 ; j <= nodes ; j++){
				
				if(arr[i][j] != -1){
					System.out.println(i + " -> " + j + " : " + arr[i][j] + " -");
				}
				else{
					System.out.println(i + " -> " + j + " : - -");
				}
			}

		}
		
		// printing the edge details
		System.out.println("\n\nNetwork : \n");
		
		System.out.print("    ");
		for(int i = 1 ; i <= nodes ; i++){
			System.out.print(i + " ");
		}
		
		System.out.println("\n");
		
		for(int i = 1; i <= nodes ; i++){
			System.out.print(i + "   ");
			for(int j = 1 ; j <= nodes ; j++){
				if(arr[i][j] == -1){
					System.out.print("- ");
				}
				
				else{
					System.out.print(arr[i][j] + " ");
				}
			}
			System.out.print("\n");
		}
		
		// displaying the routing table for each router
		
		int cost[] = new int[nodes+1];
		int prev[] = new int[nodes+1];
		
		for(int i = 1 ; i <= nodes ; i++){
		
			System.out.println("-----------------------------------------");
			System.out.println("\nRouting table for router " + i + " : \n");

			dijkstra(arr , cost , prev , i , nodes);
			
			for(int j = 1 ; j <= nodes ; j++){
				
				int next = getNext(prev , j , i);
			
				System.out.print(i + " -> " + j + " : " + cost[j] + " ");
				
				if(next == j){
					System.out.println("-");
				} 
				else{
					System.out.println(next);
				}
			}

		}
	}
	
}

 












