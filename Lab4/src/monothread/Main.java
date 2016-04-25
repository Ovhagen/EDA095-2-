package monothread;

public class Main {
	
	public static void main(String[] args) {
		String url = "http://cs.lth.se/eda095/";
		AddressHandler ah = new AddressHandler(80, url);
		Crawler cw = new Crawler(ah);
		cw.run();
		System.out.println("Email addresses found: " + ah.mailsSize() + " URLs");
		System.out.println("Frame urls found: " + ah.framesSize() + " URLs");
	}

}
