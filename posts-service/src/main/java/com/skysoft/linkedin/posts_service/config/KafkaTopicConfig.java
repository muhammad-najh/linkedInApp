package com.skysoft.linkedin.posts_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.post-created-topic}")
    private String postCreatedTopic;

    @Value("${kafka.topic.post-liked-topic}")
    private String postLikedTopic;


    @Bean
    public NewTopic postCreatedTopic(){
        return new NewTopic(postCreatedTopic, 3, (short) 1);
    }
    @Bean
    public NewTopic postLikedTop(){
        String POST_LIKED_TOPIC_NAME = postLikedTopic;
        return new NewTopic(POST_LIKED_TOPIC_NAME, 3, (short) 1);
    }
}
