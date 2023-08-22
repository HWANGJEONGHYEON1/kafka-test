import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerAsync {

    public static void main(String[] args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(ProducerAsync.class.getName());
        String topic = "simple-topic";

        // 카프카 프로듀서 설정
        Properties props = new Properties();
        // bootstrap.servers, serialization class(k,v),
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, "id-001", "hello world async");

        kafkaProducer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                logger.info("\n ##### record meta received partitions : {}, offset : {}, timestamp: {}", metadata.partition(), metadata.offset(), metadata.timestamp());
            } else {
                logger.error("exception error from broker {}", exception.getMessage());
            }
        });

        Thread.sleep(100);
        kafkaProducer.close();
    }
}
