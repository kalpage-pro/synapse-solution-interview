package lottery.results.repository;

import lottery.results.entity.LotteryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LotteryResultRepository extends JpaRepository<LotteryResult, Long> {

    // Find results by lottery name
    List<LotteryResult> findByLotteryName(String lotteryName);

    // Find recent results (last 24 hours)
    @Query("SELECT lr FROM LotteryResult lr WHERE lr.scrapedDate >= :since ORDER BY lr.scrapedDate DESC")
    List<LotteryResult> findRecentResults(@Param("since") LocalDateTime since);

    // Find results by lottery name and date range
    @Query("SELECT lr FROM LotteryResult lr WHERE lr.lotteryName = :lotteryName AND lr.scrapedDate BETWEEN :startDate AND :endDate ORDER BY lr.scrapedDate DESC")
    List<LotteryResult> findByLotteryNameAndDateRange(
        @Param("lotteryName") String lotteryName,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    // Check result already exists
    boolean existsByLotteryNameAndResultNumberAndScrapedDateBetween(
        String lotteryName,
        String resultNumber,
        LocalDateTime startDate,
        LocalDateTime endDate
    );
}
