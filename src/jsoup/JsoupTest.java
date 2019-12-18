package jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;;

public class JsoupTest {

	public static void main(String[] args) {		
		Document doc;
		Document childDoc;
		try {
			doc = Jsoup.connect("https://movie.naver.com/movie/sdb/rank/rmovie.nhn").get();
			System.out.println(doc.title());
			//System.out.println(doc.getAllElements().toString());
			//class=tit3가 여러개
			Elements newsHeadlines = doc.select("div[class=tit3]");
			for (Element headline : newsHeadlines) {
				Element temp = headline.child(0);
				
				//System.out.println(temp.attr("title"));				
				//href태그 들어가서 데이터 가져오기
				//System.out.println(temp.absUrl("href"));			
				
				
				childDoc = Jsoup.connect(temp.absUrl("href")).get();
				//System.out.println(temp.absUrl("href").toString());
				//System.out.println(childDoc);
				Elements temp1 = childDoc.select("p[class=count]"); 
				System.out.println(temp1);
				for (Element count : temp1) { 
					//System.out.println("----------------결과---------------");
					//System.out.println(count.attr("p[class=count]"));
					//System.out.println("----------------결과---------------"); 
				}
					
				//temp1.attr("span[class=count]");
				//Element temp2 = childDoc.attr("class", "count");
				//System.out.println(temp2.toString() + "++++++");
//						.attr("span[class=count]");
	//			temp2.attr
				
				
				//System.out.println(headline.attr("title") + ", " + headline.absUrl("href"));			
			}
			System.out.println("성공!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("끝!");
		}
	}
}
