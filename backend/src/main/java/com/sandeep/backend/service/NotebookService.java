package com.sandeep.backend.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NotebookService {
    private HashMap<String, String> notebookToContentMap = new HashMap<>();

    public void update(String notebookId, String content) {
        notebookToContentMap.put(notebookId, content);
    }

    public String get(String notebookId) {
        return notebookToContentMap.get(notebookId);
    }
}
