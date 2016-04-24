package ThreadCoordination;

public class ThreadWriter extends Thread{
	
	private Mailbox mb;
	
	public ThreadWriter(int threadNbr, Mailbox mb){
		this.mb = mb;
		this.setName("Thread " + threadNbr);
	}
	
	public void run(){	
		for (int i = 0; i < 5; i++) {
			try {
				this.sleep((long) Math.random()*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mb.deposit(this.getName() + " deposit nbr " + (i+1));
		}
	}

}
