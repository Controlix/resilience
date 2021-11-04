package be.mbict.resilience.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloEndpoint {

    @GetMapping("/hello")
    public Greeting hello() {
        return new Greeting("Hello");
    }
}
