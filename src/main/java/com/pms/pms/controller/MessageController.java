package com.pms.pms.controller;

import com.pms.pms.model.Chat;
import com.pms.pms.model.Message;
import com.pms.pms.model.User;
import com.pms.pms.request.CreateMessageRequest;
import com.pms.pms.service.MessageService;
import com.pms.pms.service.ProjectService;
import com.pms.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody CreateMessageRequest createMessageRequest) throws Exception {

        User sender = userService.findUserById(createMessageRequest.getSenderId());
        if (sender == null) throw new Exception("user not found");
        Chat chat = projectService.getChatByProjectId(createMessageRequest.getProjectId());
        if (chat == null) throw new Exception("chat not found");
        Message message = messageService.sendMessage(createMessageRequest.getProjectId() , createMessageRequest.getSenderId() , createMessageRequest.getContent());
        return new ResponseEntity<>(message , HttpStatus.CREATED);
    }
    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessageByProjectId(@PathVariable Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        if (chat == null) throw new Exception("chat not found");
        List<Message> messageList = messageService.getMessagesByProjectId(projectId);
        return new ResponseEntity<>(messageList, HttpStatus.OK);

    }

}
