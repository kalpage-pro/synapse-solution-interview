import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class LotteryResultFetcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter lottery name (jayoda/jovisetha):");
        String lottery = scanner.nextLine();

        System.out.println("Enter the site URL displaying results:");
        String siteUrl = scanner.nextLine();

        try {

            Document doc = Jsoup.connect(siteUrl).get();

            Elements results = doc.select(".lottery-results"); // Example selector
            System.out.println("Results for " + lottery + ":");
            for (Element result : results) {
                System.out.println(result.text());
            }
        } catch (IOException e) {
            System.out.println("Error fetching or parsing the site: " + e.getMessage());
        }
    }
}