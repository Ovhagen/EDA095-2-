package monothread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AddressQueue {

	private Queue<URL> urlQueue;
	private int limit;

	public AddressQueue(int limit, String startURL) {
		urlQueue = new LinkedList<URL>();

		try {
			urlQueue.offer(new URL(startURL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		this.limit = limit;
	}

	/** URL queue implementation */

	public boolean addURL(URL url) {
		if (sizeStatus() == -1)
			return false;
		else
			urlQueue.offer(url);
		return true;
	}

	public URL getURL() {
		return urlQueue.poll();
	}

	public int sizeStatus() {
		if (urlQueue.size() < limit) {
			return urlQueue.size();
		}
		return -1;
	}

}
