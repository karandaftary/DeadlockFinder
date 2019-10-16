class MyThread extends Thread{
    MyLock ownsLock;
    MyLock waitingOnLock;
    MyLock lock1, lock2;


    /**
     * 
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
        this.acquireLock(lock1);

        compute();

        this.acquireLock(lock2);
        this.releaseLock(lock2);

        this.releaseLock(lock1);
    }

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