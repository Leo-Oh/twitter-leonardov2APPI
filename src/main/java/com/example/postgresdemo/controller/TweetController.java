package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Tweet;
import com.example.postgresdemo.repository.TweetRepository;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/tweets")
    public List<Tweet> getTweetsByUserId(@PathVariable Long userId) {
        return tweetRepository.findByUserId(userId);
    }

    @PostMapping("/users/{userId}/tweets")
    public Tweet addTweet(@PathVariable Long userId,
                            @Valid @RequestBody Tweet tweet) {
        return userRepository.findById(userId)
                .map(user -> {
                    tweet.setUser(user);
                    return tweetRepository.save(tweet);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PutMapping("/users/{userId}/tweets/{tweetId}")
    public Tweet updateTweet(@PathVariable Long userId,
                               @PathVariable Long tweetId,
                               @Valid @RequestBody Tweet tweetRequest) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        return tweetRepository.findById(tweetId)
                .map(tweet -> {
                    tweet.setTweetw(tweetRequest.getTweetw());
                    //tweet.setTweetw(tweetRequest.getTweetw());
                    
                    tweet.setUrlimage(tweetRequest.getUrlimage());
                    return tweetRepository.save(tweet);
                }).orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id " + tweetId));
    }

    @DeleteMapping("/users/{userId}/tweets/{tweetId}")
    public ResponseEntity<?> deleteTweet(@PathVariable Long userId,
                                          @PathVariable Long tweetId) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        return tweetRepository.findById(tweetId)
                .map(tweet -> {
                    tweetRepository.delete(tweet);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id " + tweetId));

    }
}