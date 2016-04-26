package multithread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class AddressHandler {

	private Queue<URL> remainingUrls;
	private int limit;
	private Set<URL> visitedLinks;
	private Set<URL> mails;
	private Set<URL> frames;

	public AddressHandler(int limit, String startURL) {
		this.limit = limit;
		remainingUrls = new LinkedList<URL>();
		mails = new HashSet<URL>();
		frames = new HashSet<URL>();
		visitedLinks = new HashSet<URL>(limit);

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
		if(visitedLinks.size() < limit){
			return false;
		}
		return true;
	}

	public synchronized void addURL(URL url) {
		if(!remainingUrls.contains(url)){
			remainingUrls.add(url);
		}
	}

	public synchronized URL registerVisit(){
		URL url = remainingUrls.poll();
			visitedLinks.add(url);
			return url;
	}

	public synchronized void addMail(URL mailAddress) {
		mails.add(mailAddress);
	}

	public int mailsSize() {
		return mails.size();
	}

	public synchronized void addFrame(URL frameAddress) {
		mails.add(frameAddress);
	}

	public int framesSize() {
		return frames.size();
	}
	

}
