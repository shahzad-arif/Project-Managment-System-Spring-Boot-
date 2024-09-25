package com.pms.pms.service;

import com.pms.pms.model.Chat;
import com.pms.pms.model.Message;
import com.pms.pms.model.Project;
import com.pms.pms.model.User;
import com.pms.pms.repository.MessageRepo;
import com.pms.pms.repository.ProjectRepo;
import com.pms.pms.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private ProjectService projectService;


    @Override
    public Message sendMessage(Long projectId, Long senderId, String message) throws Exception {
        User sender = userRepo.findById(senderId).orElseThrow(()->new Exception("user not found with this " + senderId));
        Chat chat = projectService.getProjectById(projectId).getChat();
        Message newMessage = new Message();
        newMessage.setSender(sender);
        newMessage.setContent(message);
        newMessage.setChat(chat);
        newMessage.setCreatedAt(LocalDateTime.now());
        return messageRepo.save(newMessage);
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Project project = projectService.getProjectById(projectId);
        return messageRepo.findByChatIdOrderByCreatedAtAsc(project.getChat().getId());
    }
}
