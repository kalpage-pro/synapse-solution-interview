import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class LotteryResultFetcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
// use scanner to get input
        System.out.println("Enter lottery name (jayoda/jovisetha):");
        String lottery = scanner.nextLine().trim().toLowerCase();

        if (!lottery.equals("jayoda") && !lottery.equals("jovisetha")) {
            System.out.println("Error: Invalid lottery name. Please enter either 'jayoda' or 'jovisetha'.");
            return;
        }

        System.out.println("Enter the site URL displaying results:");
        String siteUrl = scanner.nextLine().trim();
// for io exception
        try {
            Document doc = Jsoup.connect(siteUrl).get();
            Elements results = doc.select(".lottery-results");

            System.out.println("=== " + lottery.substring(0, 1).toUpperCase() + lottery.substring(1) + " Lottery Results ===");
            if (results.isEmpty()) {
                System.out.println("No results found on the site (check the selector).");
            } else {
                for (Element result : results) {
                    System.out.println(result.text());
                }
            }
        } catch (IOException e) {
            System.out.println("Error fetching or parsing the site: " + e.getMessage());
        }
    }
}
