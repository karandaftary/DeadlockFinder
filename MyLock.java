
public class MyLock{
    private MyThread owner;

	public MyThread getOwner() {
		return owner;
	}
	public void setOwner(MyThread owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "owner = "+getOwner();
	}
    
}