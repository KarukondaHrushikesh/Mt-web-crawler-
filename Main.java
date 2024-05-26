import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<WebCrawler> bots = new ArrayList<>();
        bots.add(new WebCrawler("https://www.nytimes.com",1));
        bots.add(new WebCrawler("https://www.apple.com",2));
        bots.add(new WebCrawler("https://www.linkedin.com",3));
        bots.add(new WebCrawler("https://www.google.com",4));

        for(WebCrawler w : bots){
            try{
                w.getThread().join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }



}