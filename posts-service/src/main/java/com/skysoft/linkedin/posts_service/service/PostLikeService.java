package com.skysoft.linkedin.posts_service.service;

import com.skysoft.linkedin.event.PostLikedEvent;
import com.skysoft.linkedin.posts_service.auth.UserContextHolder;
import com.skysoft.linkedin.posts_service.entity.Post;
import com.skysoft.linkedin.posts_service.exception.ResourceNotFoundException;
import com.skysoft.linkedin.posts_service.entity.PostLike;
import com.skysoft.linkedin.posts_service.exception.BadRequestException;
import com.skysoft.linkedin.posts_service.repository.PostLikeRepository;
import com.skysoft.linkedin.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;
    private final KafkaTemplate<Long,PostLikedEvent> kafkaTemplate;

    @Value("${kafka.topic.post-liked-topic}")
    private String postLikedTopic;

    public void likePost(Long postId) {
        log.info("Attempting to like the post with id: {}", postId);

        Long userId= UserContextHolder.getCurrentUserId();
        boolean exists = postsRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id: "+postId);

        Post post = postsRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: "+postId)
        );

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(alreadyLiked) throw new BadRequestException("Cannot like the same post again.");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Post with id: {} liked successfully", postId);

        PostLikedEvent postLikedEvent = new PostLikedEvent();
        postLikedEvent.setPostId(postId);
        postLikedEvent.setLikedByUserId(userId);
        postLikedEvent.setCreatorId(post.getUserId());

        //postId as key it guarantee that notification all post will be same order
        kafkaTemplate.send(postLikedTopic,postId,postLikedEvent);


    }

    public void unlikePost(Long postId) {
        log.info("Attempting to unlike the post with id: {}", postId);

        Long userId= UserContextHolder.getCurrentUserId();

        boolean exists = postsRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id: "+postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(!alreadyLiked) throw new BadRequestException("Cannot unlike the post which is not liked.");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        log.info("Post with id: {} unliked successfully", postId);
    }
}
