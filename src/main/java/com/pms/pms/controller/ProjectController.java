package com.pms.pms.controller;


import com.pms.pms.model.Chat;
import com.pms.pms.model.Invitation;
import com.pms.pms.model.Project;
import com.pms.pms.model.User;
import com.pms.pms.request.InvitationRequest;
import com.pms.pms.response.MessageResponse;
import com.pms.pms.service.InvitationService;
import com.pms.pms.service.ProjectService;
import com.pms.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")

public class ProjectController {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private  ProjectService projectService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Project>>   getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tags,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectsByTeam(user , category, tags);
        return new ResponseEntity<>(projects , HttpStatus.OK);
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<Project>   getProjectById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project>  createProject(
            @RequestBody Project project,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject , HttpStatus.CREATED);
    }
    @PatchMapping("/{projectId}")
    public ResponseEntity<Project>  updateProject(
            @PathVariable Long projectId,
            @RequestBody Project project,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updateProject(projectId , project);
        return new ResponseEntity<>(updatedProject , HttpStatus.CREATED);
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse>  deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId , user.getId());
        MessageResponse messageResponse = new MessageResponse("Project deleted successfully");
        return new ResponseEntity<>(messageResponse ,  HttpStatus.CREATED);

    }
    @GetMapping("/search")
    public ResponseEntity<List<Project>>   searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects , HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat>   getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat , HttpStatus.OK);
    }
    @PostMapping("/invite")
    public ResponseEntity<MessageResponse>  inviteProject(
            @RequestBody Project project,
            @RequestBody InvitationRequest invitationRequest,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(invitationRequest.getEmail() , invitationRequest.getProjectId());
//
        MessageResponse messageResponse = new MessageResponse("Invitation sent successfully");

        return new   ResponseEntity<>(messageResponse , HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation>  acceptInvitationProject(
            @RequestBody Project project,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.AcceptInvitation(jwt, user.getId());
        projectService.addUserToProject(invitation.getProjectId() , user.getId());
        return new   ResponseEntity<>(invitation , HttpStatus.ACCEPTED);
    }




}
