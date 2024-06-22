package com.conversesphere.repository;

import com.conversesphere.dto.SearchResult;

public interface CustomSearchRepository {
    SearchResult search(String query);
}
