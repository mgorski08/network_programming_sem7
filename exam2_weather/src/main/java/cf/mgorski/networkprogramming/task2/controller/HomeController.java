package cf.mgorski.networkprogramming.task2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class HomeController {
    @GetMapping("/")
    String root() {
        WebClient client = WebClient.create();

        WebClient.ResponseSpec responseSpec = client.get()
                .uri("http://ittcertex.pl")
                .retrieve();

        String responseBody = responseSpec.bodyToMono(String.class).block();


        return responseBody;
    }
}
