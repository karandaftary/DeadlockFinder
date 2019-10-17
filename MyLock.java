
public class MyLock{
    private MyThread owner;
	private String name;

	public MyLock(String name)
	{
		this.name = name;
	}

	/**
	 * 
	 * @return thread that owns this lock. If no thread owns this lock returns null
	 */
	public MyThread getOwner() {
		return owner;
	}

	/**
	 * Sets owner of the lock. 
	 * @param owner Thread that will own this lock moving forward
	 */
	public void setOwner(MyThread owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return this.name;
	}
    
}