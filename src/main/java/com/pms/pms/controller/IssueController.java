package com.pms.pms.controller;

import com.pms.pms.DTO.IssueDTO;
import com.pms.pms.model.Issue;
import com.pms.pms.model.Project;
import com.pms.pms.model.User;
import com.pms.pms.request.IssueRequest;
import com.pms.pms.response.MessageResponse;
import com.pms.pms.service.IssueService;
import com.pms.pms.service.ProjectService;
import com.pms.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/issue")
public class IssueController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;
    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(
            @PathVariable Long issueId) throws  Exception{
        Issue issue = issueService.getIssueById(issueId);

        return new ResponseEntity<>(issue, HttpStatus.OK);
    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(
            @PathVariable Long projectId) throws  Exception{
            return new ResponseEntity<>(issueService.getIssuesByProjectId(projectId), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue (
            @RequestBody IssueRequest issueRequest,
            @RequestHeader("Authorization") String token
    ) throws Exception{
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Issue createdIssue = issueService.createIssue(issueRequest, user);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setAssignee(createdIssue.getAssignee());
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setProjectId(createdIssue.getProject().getId());
        issueDTO.setId(createdIssue.getId());
        issueDTO.setTags(createdIssue.getTags());

        return new ResponseEntity<>(issueDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<Issue> updateIssue (
            @PathVariable Long issueId,
            @RequestBody IssueRequest issueRequest,
            @RequestHeader("Authorization") String token
    ) throws Exception{
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());
        Issue issue = issueService.getIssueById(issueId);

        Project project = projectService.getProjectById(issue.getProject().getId());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not found");
        }
        issue.setProject(project);
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());
        issue.setDescription(issueRequest.getDescription());
        issue.setTitle(issueRequest.getTitle());
        issue.setStatus(issueRequest.getStatus());

        return new ResponseEntity<>(issue, HttpStatus.OK);
    }


    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token
    ) throws Exception{
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Issue issue = issueService.getIssueById(issueId);
        if (issue == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue Not found");
        }


        issueService.deleteIssue(issueId, user.getId());
        MessageResponse response = new MessageResponse();
        response.setMessage("Deleted Successfully");

        return new ResponseEntity<>(response ,HttpStatus.OK);
    }
    @PutMapping("{issueId}/assignee/{userId}")
    public  ResponseEntity<?> addUserToIssue(
            @PathVariable Long userId,
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token
    ) throws Exception{
        Issue issue = issueService.getIssueById(issueId);
        if (issue == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue Not found");
        }
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return new ResponseEntity<>( issueService.addUserToIssue(issueId , userId),HttpStatus.OK);

    }
    @PutMapping("/{issueId}/status/{status}")
    public  ResponseEntity<Issue> updateIssueStatus(
            @PathVariable Long issueId,
            @PathVariable String status
    ) throws Exception{
        Issue issue = issueService.getIssueById(issueId);
        if (issue == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue Not found");
        }
        return  new ResponseEntity<>(issueService.updateStatus(issueId , status),HttpStatus.OK);
    }

}
