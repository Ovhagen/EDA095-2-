package monothread;

import java.net.URL;

public class Main {
	
	public static void main(String[] args) {
		String url = "http://cs.lth.se/pierre_nugues/";
		Crawler cw = new Crawler(new AddressQueue(1000, url));
		cw.run();
		System.out.println("The program traversed " + cw.visited() + " URLs");
	}

}
