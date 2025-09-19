package lottery.results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lottery.results.service.LotteryResultService;
import lottery.results.service.LotteryScraperService;
import lottery.results.entity.LotteryResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ResultsApplication implements CommandLineRunner {

	@Autowired
	private LotteryResultService lotteryResultService;

	@Autowired
	private LotteryScraperService lotteryScraperService;

	public static void main(String[] args) {
		SpringApplication.run(ResultsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("=".repeat(80));
		System.out.println("           SRI LANKAN LOTTERY RESULTS SCRAPER");
		System.out.println("=".repeat(80));
		System.out.println("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		System.out.println();

		// Step 1: Select lotteries and scrape results
		System.out.println("STEP 1: Scraping lottery results from websites...");
		System.out.println();

		// Scrape Jayoda and Jowisetha lottery results
		lotteryScraperService.scrapeAllLotteries();

		System.out.println();
		System.out.println("STEP 2: Displaying all lottery results from database...");
		System.out.println();

		// Display all results from database
		displayAllLotteryResults();

		System.out.println();
		System.out.println("STEP 3: Displaying results by lottery type...");
		System.out.println();

		// Display results by lottery type
		displayResultsByLottery("Jayoda");
		displayResultsByLottery("Jowisetha");

		System.out.println("=".repeat(80));
		System.out.println("           LOTTERY RESULTS SCRAPING COMPLETED");
		System.out.println("=".repeat(80));
	}

	/**
	 * Display all lottery results from the database
	 */
	private void displayAllLotteryResults() {
		System.out.println("📊 ALL LOTTERY RESULTS FROM DATABASE:");
		System.out.println("-".repeat(50));

		List<LotteryResult> allResults = lotteryResultService.getAllResults();

		if (allResults.isEmpty()) {
			System.out.println("No lottery results found in database.");
			return;
		}

		for (LotteryResult result : allResults) {
			System.out.println("🎲 Lottery: " + result.getLotteryName());
			System.out.println("   Number: " + result.getResultNumber());
			System.out.println("   Scraped: " + result.getScrapedDate());
			System.out.println("   Source: " + result.getSourceUrl());
			if (result.getAdditionalInfo() != null && !result.getAdditionalInfo().isEmpty()) {
				System.out.println("   Info: " + result.getAdditionalInfo());
			}
			System.out.println("   Database ID: " + result.getId());
			System.out.println();
		}

		System.out.println("Total results in database: " + allResults.size());
		System.out.println();
	}

	/**
	 * Display results for a specific lottery
	 */
	private void displayResultsByLottery(String lotteryName) {
		System.out.println("🎯 " + lotteryName.toUpperCase() + " LOTTERY RESULTS:");
		System.out.println("-".repeat(30));

		List<LotteryResult> results = lotteryResultService.getResultsByLottery(lotteryName);

		if (results.isEmpty()) {
			System.out.println("No " + lotteryName + " results found.");
			System.out.println();
			return;
		}

		for (LotteryResult result : results) {
			System.out.println("✅ Result: " + result.getResultNumber());
			System.out.println("   Date: " + result.getScrapedDate());
			System.out.println("   Source: " + result.getSourceUrl());
			if (result.getAdditionalInfo() != null && !result.getAdditionalInfo().isEmpty()) {
				System.out.println("   Additional Info: " + result.getAdditionalInfo());
			}
			System.out.println();
		}

		System.out.println("Total " + lotteryName + " results: " + results.size());
		System.out.println();
	}
}
