package com.conversesphere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conversesphere.dto.SearchResult;
import com.conversesphere.repository.CustomSearchRepository;

@Service
public class SearchService {

    @Autowired
    private CustomSearchRepository customSearchRepository;

    public SearchResult search(String query) {
        return customSearchRepository.search(query);
    }
}
