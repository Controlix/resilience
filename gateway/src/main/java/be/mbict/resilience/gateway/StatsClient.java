package be.mbict.resilience.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rss-hungry-svc")
public interface StatsClient {

    @GetMapping("/stats")
    Object getAll();

    @GetMapping("/stats/{id}")
    Object findByMessage(@PathVariable("id") String id);
}
