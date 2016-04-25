package multithread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AddressHandler {

	private Queue<URL> remainingUrls;
	private int limit;
	private List<URL> visitedLinks;
	private List<URL> mails;
	private List<URL> frames;

	public AddressHandler(int limit, String startURL) {
		this.limit = limit;
		remainingUrls = new LinkedList<URL>();
		mails = new ArrayList<URL>();
		frames = new ArrayList<URL>();
		visitedLinks = new ArrayList<URL>(limit);

		try {
			remainingUrls.add(new URL(startURL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/** URL queue and list implementations */
	
	public synchronized int visitedSize(){
		return visitedLinks.size();
	}
	
	public synchronized boolean isDone(){
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(visitedLinks.size() < limit){
			return false;
		}
		return true;
	}

	public synchronized void addURL(URL url) {
		remainingUrls.add(url);
		notifyAll();
	}

	public synchronized URL registerVisit(){
		URL url = remainingUrls.poll();
		if (!visitedLinks.contains(url)) {
			visitedLinks.add(url);
			notifyAll();
			return url;
		}
		return null;
	}

	public synchronized void addMail(URL mailAddress) {
		mails.add(mailAddress);
		notifyAll();
	}

	public int mailsSize() {
		return mails.size();
	}

	public synchronized void addFrame(URL frameAddress) {
		mails.add(frameAddress);
		notifyAll();
	}

	public int framesSize() {
		return frames.size();
	}
	

}
