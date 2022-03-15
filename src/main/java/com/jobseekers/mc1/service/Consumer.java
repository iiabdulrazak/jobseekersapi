package com.jobseekers.mc1.service;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * Name : Consumer
 * <p>
 * Description : Kafka Consumer
 * <p>
 * Date : 15/03/2022
 * <p>
 * Create by : Abdulrazak A. Osman
 * <p>
 * Mail : a.osman@comptechco.com
 */

@org.springframework.context.annotation.Configuration
public class Consumer {

    final KafkaProperties kafkaProperties;

    public Consumer(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    ConsumerFactory<String, Object> consumerFactory() {
        final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory
            = new ConcurrentKafkaListenerContainerFactory();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        return concurrentKafkaListenerContainerFactory;
    }

}
