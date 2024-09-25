package com.pms.pms.service;

import com.pms.pms.model.Issue;
import com.pms.pms.model.User;
import com.pms.pms.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue getIssueById(Long issueId) throws Exception;
    List<Issue> getIssuesByProjectId(Long projectId) throws Exception;
    Issue createIssue(IssueRequest issue_req , User user) throws Exception;
    void deleteIssue(Long issueId , Long userId ) throws Exception;
    Issue addUserToIssue(Long issueId, Long userId) throws Exception;
    Issue updateStatus(Long issueId , String status) throws Exception;
}
