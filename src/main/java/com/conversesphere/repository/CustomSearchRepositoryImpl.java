package com.conversesphere.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.conversesphere.dto.SearchResult;
import com.conversesphere.model.Tweet;
import com.conversesphere.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class CustomSearchRepositoryImpl implements CustomSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SearchResult search(String query) {
        // Search for tweets
        TypedQuery<Tweet> tweetQuery = entityManager.createQuery(
                "SELECT t FROM Tweet t WHERE t.content LIKE :query", Tweet.class);
        tweetQuery.setParameter("query", "%" + query + "%");
        List<Tweet> tweets = tweetQuery.getResultList();

        // Search for users
        TypedQuery<User> userQuery = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.fullName LIKE :query OR u.userName LIKE :query OR u.email LIKE :query",
                User.class);
        userQuery.setParameter("query", "%" + query + "%");
        List<User> users = userQuery.getResultList();

        return new SearchResult(tweets, users);
    }
}
