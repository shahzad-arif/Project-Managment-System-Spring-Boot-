package com.pms.pms.service;

import com.pms.pms.model.Comment;
import com.pms.pms.model.Issue;
import com.pms.pms.model.User;
import com.pms.pms.repository.CommentRepo;
import com.pms.pms.repository.IssueRepo;
import com.pms.pms.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private IssueRepo issueRepo;

    @Override
    public Comment createComment(Long userId, Long issueId, String comment) throws Exception {
        Optional<Issue> issue = issueRepo.findById(issueId);
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new Exception("User not found");
        }
        if (issue.isEmpty()) {
            throw new Exception("Issue not found");
        }
         Issue issueObj = issue.get();
        User userObj = user.get();
        Comment commentObj = new Comment();
        commentObj.setContent(comment);
        commentObj.setIssue(issueObj);
        commentObj.setUser(userObj);
        commentObj.setCreatedAt(LocalDateTime.now());


        return commentRepo.save(commentObj);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> optionalComment = commentRepo.findById(commentId);
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalComment.isEmpty()) {
            throw new Exception("Comment not found");
        }
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found");
        }
        Comment comment = optionalComment.get();
        User user = optionalUser.get();

        if (comment.getUser().equals(user)) {
            commentRepo.delete(comment);
        }
        else{
            throw new Exception("User does not have permission to delete comment");
        }
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) throws Exception {
        Optional<Issue> byId = issueRepo.findById(issueId);
        if (byId.isEmpty()) {
            throw new Exception("Issue not found");
        }
        Issue issueObj = byId.get();

        return commentRepo.findByIssueId(issueObj.getId());

    }
}
