package multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	
	public static void main(String[] args) {
		String url = "http://cs.lth.se/eda095/";
		AddressHandler ah = new AddressHandler(80, url);
		int poolSize = 10;
		ExecutorService service = Executors.newFixedThreadPool(poolSize);
		for(int i = 0; i <= poolSize; i++){
			service.submit(new Crawler(ah));
		}
		service.shutdown();
		
		try{
			service.awaitTermination(1, TimeUnit.MINUTES);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		System.out.println("Email addresses found: " + ah.mailsSize() + " URLs");
		System.out.println("Frame urls found: " + ah.framesSize() + " URLs");
	}

}
