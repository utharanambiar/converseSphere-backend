package com.conversesphere.dto;

import com.conversesphere.model.Tweet;
import com.conversesphere.model.User;
import java.util.List;

public class SearchResult {
    private List<Tweet> tweets;
    private List<User> users;

    public SearchResult(List<Tweet> tweets, List<User> users) {
        this.tweets = tweets;
        this.users = users;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
