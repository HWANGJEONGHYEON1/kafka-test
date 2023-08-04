package com.example.producer.producer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeEvent {

    private String userId;
    private String broadcastId;
}
