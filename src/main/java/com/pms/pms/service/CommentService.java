package com.pms.pms.service;

import com.pms.pms.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Long userId , Long issueId , String comment) throws Exception;

    void deleteComment(Long commentId , Long userId) throws Exception ;

    List<Comment> findCommentByIssueId(Long issueId) throws Exception;
}
