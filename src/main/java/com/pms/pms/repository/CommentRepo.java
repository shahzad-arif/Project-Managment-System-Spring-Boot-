package com.pms.pms.repository;

import com.pms.pms.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByIssueId(Long issueId);

}

