class MyThread extends Thread{
    MyLock ownsLock;
    MyLock waitingOnLock;
    MyLock lock1, lock2;


    /**
     * For the purposes of this assignment we will construct Threads with 2 locks. This need not be the true implementation of Threads. 
     * @param name Thread name - a user friend representation of thread 
     * @param l1 Lock 1
     * @param l2 Lock 2
     */
    public MyThread(String name, MyLock l1, MyLock l2)
    {
        super(name);
        this.lock1 = l1;
        this.lock2 = l2;
    }
    
    /**
     * 
     * @param name A user friendly name
     */
    public MyThread(String name)
    {
        super(name);
    }

    /**
     * Acquires lock so that no other thread can use this lock
     * @param l the lock to be owned by a thread
     */
    public void acquireLock(MyLock l)
    {
        assert(l != null);
        if(l.getOwner() == null)
        {
            l.setOwner(this);
            this.ownsLock = l;
        }
        else
        {
            while(l.getOwner()!= null)
                this.waitingOnLock = l;
        }

    }

    /**
     * Releases lock so that it can be owned by other threads.  
     * @param l Lock to be released.
     */
    public void releaseLock(MyLock l)
    {
        assert(l != null);
        if(l.getOwner() == this)
        {
            l.setOwner(null);
            this.ownsLock = null;
        }
        
    }


    @Override
    public void run() {
        // acquire Lock 1
        this.acquireLock(lock1);

        // compute
        compute();

        // acquire Lock 2
        this.acquireLock(lock2);   

        // release Lock 2    
        this.releaseLock(lock2);

        // release Lock 1
        this.releaseLock(lock1);
    }

    /**
     * Short computation that prints a statement and waits for 5 seconds
     */
    private void compute(){
        try {
            System.out.println("compute() called from Thread: "+this.getName());
            Thread.sleep(5000); // sleep for 5 sec
            
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.getName();
    }
}