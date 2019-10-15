import java.util.HashMap;
import java.util.Set;
 
public class Start{
    
    public static void main(String[] args){
        System.out.println("hello Graham");
        Dump sampleFile1 = new Dump();
        System.out.println("# deadlocks found = "+findDeadLock(sampleFile1));
    }

    // return thread IDs involved in deadlock OR null if no deadlock found
    /**
     * 
     * @param d Dump file to examine
     * @return Returns a collection of ThreadIDs involved in Deadlock, null otherwise
     */
    public static MyThread[] findDeadLock(Dump d)
    {
        /*
        Overall algorithm: 
        keep track of dependencies in a HashMap<Current Thread, Waiting On Thread>
        loop through all threads in dumpfile 
            for each thread add add it's dependency to the HashMap
            if dependency exists in the map - follow it and check if the current thread exists 
                in following dependencies if you find current thread, return the map and current thread
        Once you finish the loop and you haven't returned anything - you are guaranteed that 
        a dependency does not exist in the given dump file.
        */ 

        // null checks and boundary checks 
        if(d == null || d.allThreads.length == 1) /* if you only have 1 thread, there's no deadlock */
            return null;

        // core scenario: dump file >= 2 threads which could be involved in a deadlock
        HashMap<MyThread,MyThread> map = new HashMap<MyThread,MyThread>();
        for(int i = 0; i < d.allThreads.length; i++)
        {
            MyThread t = d.allThreads[i];
            MyThread w = MyLib.getDependentThread(t);
            if(!map.containsKey(w))
                map.put(t, w);
            else
            {
                MyThread cursor = map.get(w);
                while(cursor != null)
                {
                    if(cursor == t)
                    {
                        map.put(t,w); // put the current thread, and deadlock into map
                        
                        // massage HashMap into a well-formed array with deadlocks
                        Set<MyThread> keys = map.keySet();
                        MyThread[] deadLocks = keys.toArray(new MyThread[keys.size()]);
                        return deadLocks;
                    }
                }
            }
        } // end for loop

        // At this point all threads have been examined for their dependencies
        // No Dependencies found, hence return null
        return null;
    }

}