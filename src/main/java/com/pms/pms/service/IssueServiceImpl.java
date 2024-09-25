package com.pms.pms.service;

import com.pms.pms.model.Issue;
import com.pms.pms.model.Project;
import com.pms.pms.model.User;
import com.pms.pms.repository.IssueRepo;
import com.pms.pms.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class IssueServiceImpl implements IssueService {
    @Autowired
    private  UserService userService;
    @Autowired
    private  ProjectService projectService;
    @Autowired
    private IssueRepo issueRepo;
    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepo.findById(issueId);
        if (issue.isEmpty()) {
            throw new Exception("Issue not found with this Id" + issueId);
        }
        return issue.get();


    }

    @Override
    public List<Issue> getIssuesByProjectId(Long projectId) throws Exception {
        return issueRepo.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issue_req, User user) throws Exception {
        Project project = projectService.getProjectById(issue_req.getProjectId());
        Issue issue = new Issue();
        issue.setProject(project);
        issue.setTitle(issue_req.getTitle());
        issue.setDescription(issue_req.getDescription());
        issue.setStatus(issue_req.getStatus());
        issue.setPriority(issue_req.getPriority());
        issue.setDueDate(issue_req.getDueDate());

        return  issueRepo.save(issue);


    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        Issue issue = getIssueById(issueId);
        if (issue == null) {
            throw new Exception("Issue not found with this Id" + issueId);

        }


        issueRepo.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issueById = getIssueById(issueId);
        if (user == null) {
            throw new Exception("User not found with this Id" + userId);
        }
        issueById.setAssignee(user);


        return issueRepo.save(issueById);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepo.save(issue);
    }
}
