package com.pms.pms.service;

import com.pms.pms.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public  void sendInvitation(String email , Long projectId) throws MessagingException;
    public  Invitation AcceptInvitation(String token , Long userId);
    public  String getTokenByUserMail(String email);
    void deleteToken(String token);
}
