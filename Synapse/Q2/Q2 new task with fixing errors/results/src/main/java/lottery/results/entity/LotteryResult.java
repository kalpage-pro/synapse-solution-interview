package lottery.results.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lottery_results")
public class LotteryResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lottery_name", nullable = false)
    private String lotteryName;

    @Column(name = "result_number", nullable = false)
    private String resultNumber;

    @Column(name = "draw_date")
    private LocalDateTime drawDate;

    @Column(name = "scraped_date", nullable = false)
    private LocalDateTime scrapedDate;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "additional_info")
    private String additionalInfo;

    // Constructors
    public LotteryResult() {}

    public LotteryResult(String lotteryName, String resultNumber, String sourceUrl) {
        this.lotteryName = lotteryName;
        this.resultNumber = resultNumber;
        this.sourceUrl = sourceUrl;
        this.scrapedDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getResultNumber() {
        return resultNumber;
    }

    public void setResultNumber(String resultNumber) {
        this.resultNumber = resultNumber;
    }

    public LocalDateTime getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(LocalDateTime drawDate) {
        this.drawDate = drawDate;
    }

    public LocalDateTime getScrapedDate() {
        return scrapedDate;
    }

    public void setScrapedDate(LocalDateTime scrapedDate) {
        this.scrapedDate = scrapedDate;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "LotteryResult{" +
                "id=" + id +
                ", lotteryName='" + lotteryName + '\'' +
                ", resultNumber='" + resultNumber + '\'' +
                ", drawDate=" + drawDate +
                ", scrapedDate=" + scrapedDate +
                ", sourceUrl='" + sourceUrl + '\'' +
                '}';
    }
}
