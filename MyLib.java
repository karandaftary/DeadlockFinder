import java.util.HashMap;
import java.util.Set;

public class MyLib 
{
    /**
     * 
     * @param t input thread 
     * @return Returns a lock if t has acquired a lock, null otherwise
     */
    public static MyLock WaitingOn(MyThread t)
    {
        return t.waitingOnLock;
    }

    /**
     * 
     * @param l input lock
     * @return Returns a thread if the lock has been acquired by a thread, null otherwise
     */
    public static MyThread Owner(MyLock l){
        return l.getOwner();
    }

    /**
     * 
     * @param t input thread 
     * @return Returns the thread t is dependent on given that dependent thread owns a lock that is required by t
     */
    public static MyThread getDependentThread(MyThread t)
    {
        MyLock l = WaitingOn(t);
        if(l == null)
            return null;
        else
        {
            return Owner(l);
        }
    }

    
    /**
     * Identifies deadlocks in a given dumpfile. 
     * @param d Dump file to examine
     * @return Returns an array of Threads involved in Deadlock, null otherwise
     */
    public static MyThread[] findDeadLock(Dump d)
    {
        /*
        Overall algorithm: 
        keep track of dependencies in a HashMap<Current Thread, Waiting On Thread>
        loop through all threads in dumpfile 
            for each thread add it's dependency to the HashMap
            if dependency exists in the map - follow it and check if the current thread exists 
                in following dependencies if you find current thread, return the map and current thread
        Once you finish the loop and you haven't returned anything - you are guaranteed that 
        a dependency does not exist in the given dump file.
        */ 

        // null checks and boundary checks 
        if(d == null || d.allThreads.length <= 1) /* if you only have 1 thread, there's no deadlock */
            return null; // return empty array

        // core scenario: dump file >= 2 threads which could be involved in a deadlock
        // A dependency map which stores dependencies in the form of Thread A -> Thread B
        // which means Thread A depends on Thread B. 
        HashMap<MyThread,MyThread> dependencyMap = new HashMap<MyThread,MyThread>();
        for(int i = 0; i < d.allThreads.length; i++)
        {
            // current thread 
            MyThread t = d.allThreads[i];
            // identify dependent thread if it exists. A dependency exists if current thread is 
            // waiting to acquire a lock that is already acquired by another thread
            MyThread w = MyLib.getDependentThread(t);

            // add the dependency if it doesn't exist to dependency map
            if(!dependencyMap.containsKey(w))
                dependencyMap.put(t, w);
            else
            {
                // cycle through the dependencies, checking to see if the dependency is the current thread
                MyThread cursor = dependencyMap.get(w);
                while(cursor != null)
                {
                    if(cursor == t)
                    {
                        // put the current thread, and its dependency into map --> this is the DEADLOCK!
                        dependencyMap.put(t,w); 
                        
                        // massage HashMap into a well-formed array with all threads involved in the deadlock
                        Set<MyThread> keys = dependencyMap.keySet();
                        MyThread[] deadLocks = keys.toArray(new MyThread[keys.size()]);

                        // print dependencies
                        showDependencies(dependencyMap);
                        return deadLocks;
                    }
                    else
                    {
                        cursor = dependencyMap.get(cursor);
                    }
                }
            }
        } // end for loop

        // At this point all threads have been examined for their dependencies
        // No Dependencies found, hence return null
        return null;
    }

    private static void showDependencies(HashMap<MyThread,MyThread> example)
    {
        System.out.println("Deadlock found with the following Dependency Map");
        for (MyThread name: example.keySet()){
            String key = name.toString();
            String value = example.get(name).toString();  
            System.out.println(key + " -> " + value);  
} 
    }

    

}