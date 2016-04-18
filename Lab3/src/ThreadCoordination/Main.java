package ThreadCoordination;

public class Main {
	
	public static void main(String[] args) {
		Mailbox mb = new Mailbox();
		for(int i = 0; i < 10; i++){
			new ThreadWriter(i, mb).start();
		}
		new ThreadReader(mb).start();
	}

}
