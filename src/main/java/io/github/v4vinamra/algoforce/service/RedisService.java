package io.github.v4vinamra.algoforce.service;


import io.github.v4vinamra.algoforce.entities.Contest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String UPCOMING_CONTEST_KEY = "contests:upcoming";


    // CREATE / UPDATE
    public void saveUpcomingContests(List<Contest> contests) {
        redisTemplate.opsForValue().set(UPCOMING_CONTEST_KEY, contests, 15, TimeUnit.MINUTES);
    }

    // READ
    @SuppressWarnings("unchecked")
    public List<Contest> getUpcomingContests() {
        Object obj = redisTemplate.opsForValue().get(UPCOMING_CONTEST_KEY);
        List<Contest> list = (List<Contest>) obj;
        return list;
    }

    // DELETE
    public void clearUpcomingContests() {
        redisTemplate.delete(UPCOMING_CONTEST_KEY);
    }

    // EXISTS
    public boolean exists() {
        return Boolean.TRUE.equals(redisTemplate.hasKey(UPCOMING_CONTEST_KEY));
    }


}