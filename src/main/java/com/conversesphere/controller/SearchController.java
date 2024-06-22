package com.conversesphere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.conversesphere.dto.SearchResult;
import com.conversesphere.service.SearchService;

@RestController
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public SearchResult search(@RequestParam String query, @RequestHeader("Authorization") String jwt) {
        return searchService.search(query);
    }
}
