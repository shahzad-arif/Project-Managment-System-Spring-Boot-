package com.pms.pms.service;

import com.pms.pms.model.Invitation;
import com.pms.pms.repository.InvitationRepo;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private EmailService emailService;

    @Autowired
    private InvitationRepo invitationRepo;
    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        String invitationToken= UUID.randomUUID().toString();
        Invitation invitation = new Invitation();

        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);
        invitationRepo.save(invitation);

        String InvitationLink = "http://localhost:5173/accept_invitation?token=" + invitationToken;
        emailService.sendEmailWithToken(email , InvitationLink);

    }

    @Override
    public Invitation AcceptInvitation(String token, Long userId) {
        Invitation invitation = invitationRepo.findByToken(token);
        if (invitation == null) {
            throw  new RuntimeException("Invallid invitation Token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String email) {
        Invitation invitation = invitationRepo.findByEmail(email);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepo.findByToken(token);
        invitationRepo.delete(invitation);
    }
}
