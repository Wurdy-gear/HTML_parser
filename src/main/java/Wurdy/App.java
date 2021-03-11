package Wurdy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Locale;

public class App {
    public static void main(String[] args) {

        DownloadHTML DHtmlPage = new DownloadHTML();

        String text = "";

        Document contentHTML;
        try
        {
            contentHTML = Jsoup.connect(args[0]).get();
            text = contentHTML.body().text().trim().toUpperCase(Locale.ROOT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(text);


    }
}
