package io.github.v4vinamra.algoforce.scheduler;

import io.github.v4vinamra.algoforce.entities.Root;
import io.github.v4vinamra.algoforce.service.ContestFetchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
@RequiredArgsConstructor
public class ContestFetchingScheduler {


    private final RestTemplate restTemplate;
    private final ContestFetchingService contestFetchingService;

    @Value("${Clist.api_key}")
    private String apiKey;
    @Value("${Clist.username}")
    private String username;

    public String buildUrl(String resource, int limit) {
        return UriComponentsBuilder
                .fromUriString("https://clist.by/api/v2/json/contest/")
                .queryParam("resource", resource) // dynamic platform
                .queryParam("upcoming", "true")
                .queryParam("limit", limit)
                .queryParam("order_by", "start")
                .queryParam("username", username)
                .queryParam("api_key", apiKey)
                .build(true) // encode = true
                .toUriString();
    }


    @Scheduled(cron = "0 0 5 * * *")
    public void fetchContest() {
        fetchAndSave("leetcode.com", 5);
        fetchAndSave("atcoder.jp", 5);
        fetchAndSave("codeforces.com", 5);
    }

    private void fetchAndSave(String platform, int limit) {
        String url = buildUrl(platform, limit);
        try {
            Root root = restTemplate.getForObject(url, Root.class);
            if (root != null) {
                contestFetchingService.saveUpcomingContests(root);
            } else {
                System.out.println("Empty response for: " + platform);
            }
        } catch (Exception e) {
            System.out.println("Error fetching contests for " + platform + ": " + e.getMessage());
        }
    }

}
