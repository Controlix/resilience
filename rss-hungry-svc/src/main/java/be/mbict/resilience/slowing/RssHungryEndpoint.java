package be.mbict.resilience.slowing;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.hamcrest.Matchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Slf4j
public class RssHungryEndpoint {

    private static final int MAX_RSS = 25;
    private final MessageStatsRepository messageStatsRepository;
    private transient AtomicInteger rss = new AtomicInteger(0);

    @GetMapping
    @Bulkhead(name = "allStats")
    public List<MessageStats> allStats() {
        log.info("allStats called");
        return doWithRss(1, messageStatsRepository::findAll);
    }

    private <T> T doWithRss(int numRss, Supplier<T> supplier) {
        log.info("Wait for {} rss", numRss);
        Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAtomic(rss, Matchers.lessThanOrEqualTo(MAX_RSS - numRss));
        log.info("Acquire rss"); // (1)
        int rssUsed = rss.addAndGet(numRss);
        log.info("{} rss currently in use", rssUsed);
        slowDown(numRss);
        T result = supplier.get();
        log.info("Release rss");
        rss.addAndGet(-numRss);
        return result;
    }

    @GetMapping("/{id}")
    @Bulkhead(name = "statsForMessage")
    public List<Integer> statsForMessage(@PathVariable("id") String id) {
        log.info("statsForMessage called with id '{}'", id);
        return doWithRss(5, () -> messageStatsRepository.findById(id));
    }

    @SneakyThrows
    private void slowDown(int tenths) {
        TimeUnit.MILLISECONDS.sleep(tenths * 100L);
    }
}
