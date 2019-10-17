
public class Dump{
    MyThread[] allThreads;
    MyLock[] locks;

    /**
     * 
     * @param numLocks # locks the dump file should use
     * @param numThreads # threads the dump file should create and execute
     * @param cycle specifies whether the threads depend on each other or not
     */
    public Dump(int numLocks, int numThreads, boolean cycle)
    {
        populateLocks(numLocks);
        populateThreads(numThreads, cycle);
    }

    /**
     * 
     * @param maxLocks populate locks that will be used by threads
     */
    public void populateLocks(int maxLocks)
    {
        locks = new MyLock[maxLocks];
        for(int i = 0; i < locks.length; i++)
        {
            locks[i] = new MyLock();
        }
    }

    /**
     * 
     * @param maxThreads # threads 
     * @param cycle true = configures threads in a way that forces deadlock; false = no dependency of threads created
     */
    public void populateThreads(int maxThreads, boolean cycle)
    {
        // If no threads provided
        if(maxThreads == 0)
            return;

        allThreads = new MyThread[maxThreads];
        

        for(int i = 0; i < maxThreads-1; i++)
        {
            MyThread customThread;
            // wrap around and re-use locks in the case where we have less # locks than threads               
            char threadName = getChar(i);
            customThread = new MyThread(""+threadName, locks[i % locks.length], locks[i+1 % locks.length]); 
            allThreads[i] = customThread;
        }
        
        // special case for the last one: if user wants a cycle - connect back to first lock, otherwise create a dummy lock.
        
            int last = maxThreads-1;
            if(cycle == true)
            {
                allThreads[last] = new MyThread(""+getChar(last), locks[last], locks[0]);
            }
            else
            {
                allThreads[last] = new MyThread(""+getChar(last), locks[last], new MyLock());
            }
           

    }

    /**
     * 
     * @param i input a number between 0 and 26 
     * @return returns character representation of i from A to Z.
     */
    private char getChar(int i)
    {
        char name = (char)('A' + i);
        return name;
    }

    /**
     * Start thread executions. If threads are cyclic i.e. 2 or more threads have dependency on each other, this will force a deadlock.
     */
    public void startThreads()
    {
        for(int i = 0; i < allThreads.length; i++)
        {
            allThreads[i].start();
        }
    }

}
