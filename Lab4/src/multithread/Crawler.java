package multithread;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	private AddressQueue que;
	private int visited;

	public Crawler(AddressQueue que) {
		this.que = que;
	}

	public void run() {
		while (que.sizeStatus() != -1) {
			URL url = que.getURL();
			try {
				URLConnection uc = url.openConnection();
				String type = uc.getContentType().toLowerCase();
				if ((type != null) && !type.startsWith("text/html")) {
					System.out.println(url + " ignored. Type " + type);
					return;
				}
				
				InputStream is = url.openStream();
				Document doc = Jsoup.parse(is, "UTF-8", url.toString());

				Elements base = doc.getElementsByTag("base");
				System.out.println("Base : " + base);
				Elements links = doc.select("a[href]");

				for (Element link : links) {
					String linkHref = link.attr("href");
					String linkAbsHref = link.attr("abs:href");
					System.out.println("Added link: "
					+ linkAbsHref);

					que.addURL(new URL(link.absUrl("href")));
				}

				is.close();
				
				visited ++;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int visited(){
		return visited;
	}

}
