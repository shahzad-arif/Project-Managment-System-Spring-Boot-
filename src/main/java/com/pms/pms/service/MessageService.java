package com.pms.pms.service;

import com.pms.pms.model.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long projectId, Long senderId, String message) throws Exception;
    List<Message> getMessagesByProjectId(Long projectId) throws Exception;

}
