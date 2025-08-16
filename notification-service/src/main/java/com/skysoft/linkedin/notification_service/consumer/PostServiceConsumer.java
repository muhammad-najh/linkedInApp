package com.skysoft.linkedin.notification_service.consumer;

import com.skysoft.linkedin.event.PostCreatedEvent;
import com.skysoft.linkedin.event.PostLikedEvent;
import com.skysoft.linkedin.notification_service.clients.ConnectionClient;
import com.skysoft.linkedin.notification_service.dto.PersonDto;
import com.skysoft.linkedin.notification_service.entity.NotificationEntity;
import com.skysoft.linkedin.notification_service.repository.NotificationRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceConsumer {
    private static final String POST_CREATED_TOPIC = "post-created-topic";
    private static final String POST_LIKED_TOPIC = "post-liked-topic";

    private final ConnectionClient connectionClient;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = POST_CREATED_TOPIC)
    public void handlePostCreated(PostCreatedEvent postCreatedEvent) {
        log.info("Send Notification : handlePostCreated {}", postCreatedEvent);
        List<PersonDto> connections = null;
        try {
            connections = connectionClient.getFirstConnections(postCreatedEvent.getCreatorId());
        } catch (FeignException e) {
            log.error("Failed to fetch first connections", e);
        }

        for (PersonDto connection : connections) {
            sendNotification(connection.getUserId(), "Your Connection " + postCreatedEvent.getCreatorId() + " has been created a post" +
                    " check it out");
        }
    }

    @KafkaListener(topics = POST_LIKED_TOPIC)
    public void handlePostLiked(PostLikedEvent postLikedEvent) {
        log.info("Send Notification : handlePostLiked {}", postLikedEvent);

        String message = String.format("Your post, %d has been Liked by %s", postLikedEvent.getPostId(), postLikedEvent.getCreatorId());
        sendNotification(postLikedEvent.getCreatorId(), message);


    }

    public void sendNotification(Long userId, String message) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setMessage(message);
        notificationEntity.setUserId(userId);

        notificationRepository.save(notificationEntity);

    }
}
