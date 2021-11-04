package be.mbict.resilience.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "slowing-svc")
public interface MessageClient {

    @GetMapping("/message")
    Object getAll();
}
