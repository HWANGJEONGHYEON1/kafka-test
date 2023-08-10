package com.example.producer.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LikeController {

    private final LikeEventProducer likeEventProducer;


    @GetMapping("/")
    public ResponseEntity<String> like() {
        log.info("hi");
        return ResponseEntity.ok("HELLO");
    }
    @GetMapping("/like")
    public ResponseEntity<String> like(@RequestParam String userId, @RequestParam(defaultValue = "n") String stop) {
        // 사용자가 좋아요를 누른 경우 좋아요 이벤트를 Kafka 토픽에 전송
        likeEventProducer.sendLikeEvent(userId);

        return ResponseEntity.ok("Liked!");
    }
}
