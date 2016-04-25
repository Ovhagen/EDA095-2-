package multithread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler extends Thread{
	private AddressHandler que;

	public Crawler(AddressHandler que) {
		this.que = que;
	}

	public void run() {
		while (!que.isDone()) {
			try {
				URL url = que.registerVisit();

				if (url == null) {
					throw new AddressException("URL " + url + " already visited!");
				}

				System.out.println("--------- Visited size = " + que.visitedSize());

				URLConnection uc = url.openConnection();
				String type = uc.getContentType().toLowerCase();
				if ((type != null) && !type.startsWith("text/html")) {
					String message = url + " ignored. Type " + type;
					throw new AddressException(message);
				}

				InputStream is = url.openStream();
				Document doc = Jsoup.parse(is, "UTF-8", url.toString());
				Elements frames = doc.select("frame[src]");
				Elements links = doc.select("a[href]");

				for (Element frame : frames) {
					String frameAbsHref = frame.attr("abs:href");
					que.addFrame((new URL(frameAbsHref.toString())));
				}

				for (Element link : links) {
					String linkAbsHref = link.attr("abs:href");
					// System.out.println("Added link: " + linkAbsHref);
					if (linkAbsHref.toLowerCase().startsWith("mailto"))
						que.addMail(new URL(linkAbsHref));
					else
						que.addURL(new URL(linkAbsHref));
				}

				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (AddressException e) {
				e.getMessage();
			}
		}
	}

}
