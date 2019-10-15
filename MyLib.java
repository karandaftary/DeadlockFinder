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

    

}