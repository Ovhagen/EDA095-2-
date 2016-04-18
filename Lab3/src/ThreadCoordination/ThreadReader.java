package ThreadCoordination;

public class ThreadReader extends Thread {

	private Mailbox mb;

	public ThreadReader(Mailbox mb) {
		this.mb = mb;
	}

	public void run() {
		try {
			while(true){
				this.sleep((long) Math.random()*1000);
				String message = mb.clear();
				if(message != null)
				System.out.println(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
