package lottery.results.service;

import lottery.results.entity.LotteryResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LotteryScraperService {

    @Autowired
    private LotteryResultService lotteryResultService;

    private static final String LOTTERY_WEBSITE = "https://www.nlb.lk/results";
    private static final String BACKUP_WEBSITE = "https://lotteryresults.lk";

    public void scrapeJayodaResults() {
        scrapeLotteryResults("Jayoda");
    }

    public void scrapeJowisathaResults() {
        scrapeLotteryResults("Jowisetha");
    }

    /**
     * Generic method to scrape a lottery by name
     */
    private void scrapeLotteryResults(String lotteryName) {
        System.out.println("=== SCRAPING " + lotteryName.toUpperCase() + " LOTTERY RESULTS ===");

        try {
            // Primary website
            Document doc = Jsoup.connect(LOTTERY_WEBSITE)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements results = doc.select("*:containsOwn(" + lotteryName + "), .lottery-result:contains(" + lotteryName + "), .result-item:contains(" + lotteryName + ")");

            boolean found = false;

            for (Element element : results) {
                String resultNumber = extractResultNumber(element.text());
                if (resultNumber != null && !resultNumber.isEmpty()) {
                    String additionalInfo = extractAdditionalInfo(element);

                    // Save to DB
                    LotteryResult result = new LotteryResult();
                    result.setLotteryName(lotteryName);
                    result.setResultNumber(resultNumber);
                    result.setScrapedDate(LocalDateTime.now());
                    result.setSourceUrl(LOTTERY_WEBSITE);
                    result.setAdditionalInfo(additionalInfo);

                    lotteryResultService.saveResult(result); // <-- Fixed

                    System.out.println(lotteryName + " Result Found:");
                    System.out.println("  Number: " + resultNumber);
                    System.out.println("  Additional Info: " + additionalInfo);
                    System.out.println("  Source: " + LOTTERY_WEBSITE);
                    System.out.println();

                    found = true;
                }
            }

            if (!found) {
                System.out.println("No results found on primary website. Trying backup...");
                scrapeBackupLotteryResults(lotteryName);
            }

        } catch (IOException e) {
            System.err.println("Error scraping " + lotteryName + ": " + e.getMessage());
            scrapeBackupLotteryResults(lotteryName);
        }
    }

    /**
     * Backup scraping method
     */
    private void scrapeBackupLotteryResults(String lotteryName) {
        try {
            Document doc = Jsoup.connect(BACKUP_WEBSITE)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements elements = doc.select("*:containsOwn(" + lotteryName + "), .lottery-result, .result-item");
            boolean found = false;

            for (Element element : elements) {
                String text = element.text();
                if (text.toLowerCase().contains(lotteryName.toLowerCase())) {
                    String resultNumber = extractResultNumber(text);
                    if (resultNumber != null) {
                        LotteryResult result = new LotteryResult();
                        result.setLotteryName(lotteryName);
                        result.setResultNumber(resultNumber);
                        result.setScrapedDate(LocalDateTime.now());
                        result.setSourceUrl(BACKUP_WEBSITE);
                        result.setAdditionalInfo(extractAdditionalInfo(element));

                        lotteryResultService.saveResult(result);

                        System.out.println(lotteryName + " Backup Result Found:");
                        System.out.println("  Number: " + resultNumber);
                        System.out.println("  Source: " + BACKUP_WEBSITE);
                        System.out.println();

                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                // Generate sample data if scraping fails
                String sampleResult = generateSampleResult(lotteryName);
                LotteryResult sample = new LotteryResult();
                sample.setLotteryName(lotteryName);
                sample.setResultNumber(sampleResult);
                sample.setScrapedDate(LocalDateTime.now());
                sample.setSourceUrl("Sample Data");
                sample.setAdditionalInfo("Generated sample result");

                lotteryResultService.saveResult(sample);

                System.out.println(lotteryName + " Sample Result Generated:");
                System.out.println("  Number: " + sampleResult);
                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("Backup scraping failed for " + lotteryName + ": " + e.getMessage());
        }
    }

    private String extractResultNumber(String text) {
        if (text == null) return null;
        String[] patterns = {"\\b\\d{6}\\b", "\\b\\d{5}\\b", "\\b\\d{4}\\b", "\\b\\d{3}\\b"};
        for (String pattern : patterns) {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(text);
            if (m.find()) return m.group();
        }
        return null;
    }

    private String extractAdditionalInfo(Element element) {
        String text = element.text();
        if (text == null || text.isEmpty()) {
            return "Scraped on " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        return text.length() > 200 ? text.substring(0, 200) : text;
    }

    private String generateSampleResult(String lotteryName) {
        int randomNum = (int) (Math.random() * 900000) + 100000; // 6-digit
        return String.valueOf(randomNum);
    }

    public void scrapeAllLotteries() {
        System.out.println("Starting lottery scraping...");
        scrapeJayodaResults();
        scrapeJowisathaResults();
        System.out.println("Scraping completed!");
    }
}
