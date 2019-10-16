
public class Dump{
    MyThread[] allThreads;
    MyLock[] locks;

    
    public Dump(int numLocks, int numThreads)
    {
        boolean defaultCycle = false;
        populateLocks(numLocks);
        populateThreads(numThreads, defaultCycle);
    }

    public Dump(int numLocks, int numThreads, boolean cycle)
    {
        populateLocks(numLocks);
        populateThreads(numThreads, cycle);
    }

    public void populateLocks(int maxLocks)
    {
        locks = new MyLock[maxLocks];
        for(int i = 0; i < locks.length; i++)
        {
            locks[i] = new MyLock();
        }
    }

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

    private char getChar(int i)
    {
        char name = (char)('A' + i);
        return name;
    }

    public void startThreads()
    {
        for(int i = 0; i < allThreads.length; i++)
        {
            allThreads[i].start();
        }
    }

    public void createSimpleDeadlock()
    {
        MyLock lock1 = new MyLock();
        MyLock lock2 = new MyLock();

        
        MyThread threadA = new MyThread("A", lock1, lock2);
        MyThread threadB = new MyThread("B", lock2, lock1);
        

        allThreads = new MyThread[]
        { 
            threadA,
            threadB
        };

    }
    



}
