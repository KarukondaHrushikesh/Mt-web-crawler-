import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebCrawler implements Runnable{ //used to assign threads to the programs
    private static final int MAX_DEPTH = 3; //to get the maximum number of lines
    private Thread thread; //creates a thread
    private String first_link;
    private ArrayList <String> visitedLinks = new ArrayList<String>();
    private int ID;

    public WebCrawler(String link, int num){
        System.out.print("WebCrawler created");
        first_link = link;
        ID = num;

        thread = new Thread(this); //creates a new thread
        thread.start();//when you run the bot it starts the thread
    }

    @Override
    public void run(){
        crawl(1, first_link);
    }

    //to crawl recursively
    private void crawl(int level, String url){
        if(level<=MAX_DEPTH){
            Document doc = request(url);
            if(doc != null){
                for(Element link : doc.select("a[href]")){
                    String next_link = link.absUrl("href"); //used to get rid of href
                    if(!visitedLinks.contains(next_link)){
                        crawl(level++,next_link);
                    }
                }
            }
        }
    }

    private Document request(String url) {
        int retries = 3;
        for (int i = 0; i < retries; i++) {
            try {
                // Make the HTTP request
                Connection con = Jsoup.connect(url)
                        .timeout(5000) // 5 seconds connection timeout
                        .followRedirects(true);
                Document doc = con.get();

                // Process the response
                if (con.response().statusCode() == 200) {
                    // Successful response

                    System.out.println("/n**Bot ID :"+ ID + "Recieved Webpage at " + url);

                    String title = doc.title();
                    System.out.println(title);
                    visitedLinks.add(url);

                    return doc;
                }
            } catch (IOException e) {
                // Log the exception
                System.out.println("Connection failed. Retrying...");
            }
        }
        // If all retries fail, return null
        return null;
    }


    public Thread getThread(){
        return thread;
    }
}
