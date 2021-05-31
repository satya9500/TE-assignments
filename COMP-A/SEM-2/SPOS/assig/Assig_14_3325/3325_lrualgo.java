import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
  
class Test
{
    // Method to find page faults using indexes
    static int pageFaults(int pages[], int n, int capacity)
    {
        HashSet<Integer> s = new HashSet<>(capacity);
       
        // To store least recently used indexes
        // of pages.
        HashMap<Integer, Integer> indexes = new HashMap<>();
       
        // Start from initial page
        int page_faults = 0;
        for (int i=0; i<n; i++)
        {
            // Check if the set can hold more pages
            if (s.size() < capacity)
            {
                if (!s.contains(pages[i]))
                {
                    s.add(pages[i]);
       
                    page_faults++;
                }
       
                indexes.put(pages[i], i);
            }
       
            // If the set is full then need to perform lru
            // i.e. remove the least recently used page
            // and insert the current page
            else
            {
                // Check if current page is not already
                // present in the set
                if (!s.contains(pages[i]))
                {
                    // Find the least recently used pages
                    // that is present in the set
                    int lru = Integer.MAX_VALUE, val=Integer.MIN_VALUE;
                      
                    Iterator<Integer> itr = s.iterator();
                      
                    while (itr.hasNext()) {
                        int temp = itr.next();
                        if (indexes.get(temp) < lru)
                        {
                            lru = indexes.get(temp);
                            val = temp;
                        }
                    }
                  
                    // Remove the indexes page
                    s.remove(val);
                   //remove lru from hashmap
                   indexes.remove(val);
                    // insert the current page
                    s.add(pages[i]);
       
                    // Increment page faults
                    page_faults++;
                }
       
                // Update the current page index
                indexes.put(pages[i], i);
            }
        }
       
        return page_faults;
    }
      
    // Driver method
    public static void main(String args[])
    {	
        int n,capacity;
	    Scanner sc = new Scanner(System.in);  
              
            // get total number of page requests
            System.out.println("Enter total number of page requests");  
            n = sc.nextInt();  
              
            // get total number of frames  
            System.out.println("Enter total number of frames");  
            capacity = sc.nextInt();  
            System.out.println("Enter page request sequence");    
            int pages[] = new int[n];  
            for(int i = 0; i < n; i++){  
                pages[i] = sc.nextInt();;  
            }  
              
        System.out.println("Total number of page faults"); 
        System.out.println(pageFaults(pages,n, capacity));
    }
}