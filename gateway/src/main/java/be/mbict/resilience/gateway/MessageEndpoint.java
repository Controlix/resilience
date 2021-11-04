package be.mbict.resilience.gateway;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@Slf4j
public class MessageEndpoint {

    private MessageClient messageClient;
    private StatsClient statsClient;

    @GetMapping("/message")
    public List<Object> allMessages(@RequestParam(name = "times", defaultValue = "1", required = false) int times) {
        log.info("Call the message client {} times", times);
        return getNResultsFrom(times, messageClient::getAll);
    }

    @GetMapping("/stats")
    public List<Object> allStats(@RequestParam(name = "times", defaultValue = "1", required = false) int times) {
        log.info("Call the stats client {} times", times);
        return getNResultsFrom(times, statsClient::getAll);
    }

    @GetMapping("/message/{id}/stats")
    public List<Object> allMessagesWithStats(@RequestParam(name = "times", defaultValue = "1", required = false) int times, @PathVariable("id") String id) {
        log.info("Call the stats client {} times", times);
        return getNResultsFrom(times, () -> statsClient.findByMessage(id));
    }

    private List<Object> getNResultsFrom(int times, Supplier<Object> supplier) {
        return Stream.generate(() -> CompletableFuture.supplyAsync(supplier))
                .limit(times)
                .collect(Collectors.toList()) // (1)
                .stream()
                .map(CompletableFuture::join)
                .peek(o -> log.info("Got result {}", o))
                .collect(Collectors.toList());
    }
}
