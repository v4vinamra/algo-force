package io.github.v4vinamra.algoforce.service;

import io.github.v4vinamra.algoforce.entities.Contest;
import io.github.v4vinamra.algoforce.entities.Root;
import io.github.v4vinamra.algoforce.entities.dtos.ContestDto;
import io.github.v4vinamra.algoforce.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContestFetchingService {


    private final ContestRepository contestRepository;


    public List<Contest> getAllUpcomingContests(){
        return contestRepository.findByStartAfterOrderByStartAsc(LocalDateTime.now());
    }


    public void saveUpcomingContests(Root root) {
        List<Contest> newContests = root.getObjects().stream()
                .map(this::mapFields)
                .filter(contest -> !contestRepository.existsByContestId(contest.getContestId()))
                .toList();

        if (!newContests.isEmpty()) {
            contestRepository.saveAll(newContests);
            System.out.println("New contests saved: " + newContests.size());
        } else {
            System.out.println("No new contests to save.");
        }
    }



    private Contest mapFields(ContestDto contestDto) {
        Contest contest = new Contest();
        contest.setContestId(contestDto.getId());
        contest.setTitle(contestDto.getEvent());
        contest.setPlatformUrl(contestDto.getHost());
        contest.setRegistrationUrl(contestDto.getHref());
        contest.setDuration(contestDto.getDuration() / 60);
        contest.setPlatform(extractPlatform(contestDto.getHref()));

        // Convert ISO UTC time to IST
        LocalDateTime utcLocal = contestDto.getStart();
        ZonedDateTime utcZoned = utcLocal.atZone(ZoneId.of("UTC"));
        ZonedDateTime istZoned = utcZoned.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
        contest.setStart(istZoned.toLocalDateTime());

        return contest;
    }

    public String extractPlatform(String url) {
        if (url == null) return "Unknown";

        url = url.toLowerCase();
        if (url.contains("leetcode.com")) return "LeetCode";
        if (url.contains("codeforces.com")) return "Codeforces";
        if (url.contains("atcoder.jp")) return "Atcoder";

        return "Other";
    }
}
