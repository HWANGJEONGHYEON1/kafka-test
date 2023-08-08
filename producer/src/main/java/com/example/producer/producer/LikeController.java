package com.example.producer.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LikeController {


    private final LikeEventProducer likeEventProducer;

    @GetMapping("/like")
    public ResponseEntity<String> like(@RequestParam String broadcastId) {
        // 사용자가 좋아요를 누른 경우 좋아요 이벤트를 Kafka 토픽에 전송
        likeEventProducer.sendLikeEvent(broadcastId);

        return ResponseEntity.ok("Liked!");
    }
}
