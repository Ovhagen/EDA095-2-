package monothread;

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
		visitedLinks = new ArrayList<URL>();

		try {
			remainingUrls.offer(new URL(startURL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/** URL queue and list implementations */
	
	public int visitedSize(){
		return visitedLinks.size();
	}

	public void addURL(URL url) {
		remainingUrls.add(url);
	}

	public URL registerVisit() {
		URL url = remainingUrls.poll();
		if ((visitedLinks.size() < limit) && !visitedLinks.contains(url)) {
			visitedLinks.add(url);
			return url;
		}
		return null;
	}

	public void addMail(URL mailAddress) {
		mails.add(mailAddress);
	}

	public int mailsSize() {
		return mails.size();
	}

	public void addFrame(URL frameAddress) {
		mails.add(frameAddress);
	}

	public int framesSize() {
		return frames.size();
	}
	

}
