class MyThread{
    MyLock ownsLock;
    MyLock waitingOnLock;
    private char name;

    public MyThread(char threadName){
        name = threadName;
    }


    public void acquireLock(MyLock l)
    {
        if(l.getOwner() == null)
        {
            l.setOwner(this);
            this.ownsLock = l;
        }
        else
        {
            this.waitingOnLock = l;
        }

    }

    public void releaseLock(MyLock l)
    {
        if(l.getOwner() == this)
        {
            l.setOwner(null);
            this.ownsLock = null;
        }
        
    }

	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}
}