
public class Dump{
    MyThread[] allThreads;
    MyLock lock1 = new MyLock();
    MyLock lock2 = new MyLock();

    public Dump()
    {
        // for the sake of example instantiating an array and populating. Typically this constructor
        // would only have instantiation of the thread array, not population of it.
        populateSampleDumpfile();
        
    }

    public void populateSampleDumpfile()
    {
        // for this example we will have 5 threads 
        allThreads = new MyThread[]
        {
            new MyThread('A'), 
            new MyThread('B'),
            new MyThread('C'),
            new MyThread('D'),
            new MyThread('E')
        };

        threadAProc();
        threadBProc();
    }

    public void threadAProc()
    {
        MyThread a = new MyThread('A');

        a.acquireLock(lock1);
        computeLong(a);
        a.acquireLock(lock2);
        computeShort(a);
        a.releaseLock(lock2);
        a.releaseLock(lock1);
    }

    public void threadBProc()
    {
        MyThread b = new MyThread('B');
        
        b.acquireLock(lock2);
        computeLong(b);
        b.acquireLock(lock1);
        computeShort(b);
        b.releaseLock(lock1);
        b.releaseLock(lock2);   
    }

    private void computeLong(MyThread t)
    {
        System.out.println("Calling computeLong() from Thread:"+t.getName());
    }

    private void computeShort(MyThread t)
    {
        System.out.println("Calling computeShort() from Thread:"+t.getName());
    }



}
