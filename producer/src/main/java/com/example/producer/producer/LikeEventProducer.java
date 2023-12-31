package com.example.producer.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeEventProducer {

    private final String LIKE_TOPIC = "like-events-user"; // 좋아요 이벤트를 보낼 토픽 이름
    private static final String REDIS_LIKE_COUNTER_KEY_PREFIX = "like_count:";


    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    private Long maxLikeCount = 100000L; // 좋아요 개수 제한 설정값

    public void sendLikeEvent(String userId) {
        // 현재 좋아요 개수 조회 (레디스 등을 이용해서 좋아요 개수를 가져온다고 가정)
//        Long currentLikeCount = getLikeCount(userId);
        // 좋아요 개수가 제한을 초과하는 경우 프로듀싱하지 않음
        // 관리자가 조절한다는 가정을 가짐
//        if (currentLikeCount >= maxLikeCount) {
//            return;
//        }

//        incrementLikeCount(userId);
        // 좋아요 이벤트를 Kafka 토픽에 전송
        kafkaTemplate.send(LIKE_TOPIC, userId);
    }

    public Long incrementLikeCount(String userId) {
        String key = REDIS_LIKE_COUNTER_KEY_PREFIX + userId;
        return redisTemplate.opsForValue().increment(key, 1);
    }

    public Long getLikeCount(String userId) {
        String key = REDIS_LIKE_COUNTER_KEY_PREFIX + userId;
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return 0L;
        }
        return Long.parseLong(value);
    }
}