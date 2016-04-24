package monothread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	private AddressHandler que;

	public Crawler(AddressHandler que) {
		this.que = que;
	}

	public void run() {
		URL url;
		while ((url = que.registerVisit()) != null) {
			try {
				System.out.println("--------- Visited size = " + que.visitedSize());
				
				URLConnection uc = url.openConnection();
				String type = uc.getContentType().toLowerCase();
				if ((type != null) && !type.startsWith("text/html")) {
					System.out.println(url + " ignored. Type " + type);
					return;
				}

				InputStream is = url.openStream();
				Document doc = Jsoup.parse(is, "UTF-8", url.toString());

				Elements links = doc.select("a[href]");

				for (Element link : links) {
					/*
					 * if(links.toString().toLowerCase().startsWith("mailto"))
					 * String linkAbsHref = link.absUrl("href");
					 * System.out.println("Added link: " + linkAbsHref);
					 * if(!que.addURL(new URL(linkAbsHref))) break;
					 */

					String linkAbsHref = link.attr("abs:href");
					System.out.println("Added link: " + linkAbsHref);

					que.addURL(new URL(linkAbsHref.toString()));
				}

				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
