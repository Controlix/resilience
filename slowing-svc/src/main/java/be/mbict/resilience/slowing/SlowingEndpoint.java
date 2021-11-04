package be.mbict.resilience.slowing;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Slf4j
public class SlowingEndpoint {

    private final MessageRepository messageRepository;
    private transient int numCalls = 0;

    @GetMapping
    public List<Message> allMessages() {
        log.info("allMessages called");
        slowDown();
        return messageRepository.findAll();
    }

    @SneakyThrows
    private void slowDown() {
        TimeUnit.MILLISECONDS.sleep(numCalls++ * 100L);
        numCalls %= 10;
    }
}
