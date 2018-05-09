package com.sandeep.backend.controller;

import com.sandeep.backend.model.Notepad;
import com.sandeep.backend.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("http://localhost:4200")
public class NotepadController {
    @Autowired
    NotebookService notebookService;

    @MessageMapping("/notebooks/{notebookId}")
    @SendTo("/topic/notebooks/{notebookId}")
    public String greeting(@PathVariable("notebookId") String notebookId, Notepad message) throws Exception {
        notebookService.update(notebookId, message.getContent());
        System.out.println("called with " + message);
        return notebookService.get(notebookId);
    }
}
