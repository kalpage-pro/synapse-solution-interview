package lottery.results.service;

import lottery.results.entity.LotteryResult;
import lottery.results.repository.LotteryResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryResultService {

    @Autowired
    private LotteryResultRepository repository;

    // Save a single lottery result
    public LotteryResult saveResult(LotteryResult result) {
        return repository.save(result);
    }

    // Get all results
    public List<LotteryResult> getAllResults() {
        return repository.findAll();
    }

    // Get results by lottery name
    public List<LotteryResult> getResultsByLottery(String lotteryName) {
        return repository.findByLotteryName(lotteryName);
    }
}
