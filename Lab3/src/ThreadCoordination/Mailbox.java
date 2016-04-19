package ThreadCoordination;

public class Mailbox {
	private volatile String message;

	public Mailbox() {
	}

	public synchronized void deposit(String message) {
		while (this.message != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.message = message;
		notifyAll();
	}

	public synchronized String clear() {
		while (message == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String temp = message;
		message = null;
		notifyAll();
		return temp;
	}

}
