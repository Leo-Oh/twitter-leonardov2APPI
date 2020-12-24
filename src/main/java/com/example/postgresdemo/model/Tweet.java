package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tweets")
public class Tweet extends AuditModel {
    @Id
    @GeneratedValue(generator = "tweet_generator")
    @SequenceGenerator(
            name = "tweet_generator",
            sequenceName = "tweet_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(name = "tweet",columnDefinition = "text")
    private String tweetw;

    @Column(name = "urlimage",columnDefinition = "text")
    private String urlimage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    // Getters and Setters (Omitted for brevity)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTweetw() {
        return tweetw;
    }

    public void setTweetw(String tweetw) {
        this.tweetw = tweetw;
    }

    public String getUrlimage() {
        return urlimage;
    }

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
