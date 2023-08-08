package com.example.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class SimpleConsumer {

    public static final Logger logger = LoggerFactory.getLogger(SimpleConsumer.class.getName());
    private static final String LIKE_TOPIC = "like_events"; // 좋아요 이벤트를 보낼 토픽 이름
    public static void main(String[] args) {


        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9091, 127.0.0.1:9092, 127.0.0.1:9093");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "like-group");

        KafkaConsumer<String, Long> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(List.of(LIKE_TOPIC));

        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info(" main program starts to exit by calling wakeup");
            kafkaConsumer.wakeup();

            try {
                mainThread.join();
            } catch(InterruptedException e) { e.printStackTrace();}
        }));

        try {
            while (true) {
                ConsumerRecords<String, Long> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
                if (consumerRecords.count() > 0) {
                    logger.info("counts : {}", consumerRecords.count());
                }
                for (ConsumerRecord record : consumerRecords) {
                    logger.info("record key:{},  partition:{}, record offset:{} record value:{}",
                            record.key(), record.partition(), record.offset(), record.value());
                }

                try {
                    Thread.sleep(500); // 2초간 스레드 sleep
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }catch(WakeupException e) {
            logger.error("wakeup exception has been called");
        }finally {
            logger.info("finally consumer is closing");
            kafkaConsumer.close();
        }
    }
}