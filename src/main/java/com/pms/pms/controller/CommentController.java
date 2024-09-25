package com.pms.pms.controller;

import com.pms.pms.model.Comment;
import com.pms.pms.model.Issue;
import com.pms.pms.model.User;
import com.pms.pms.request.CreateCommentRequest;
import com.pms.pms.response.MessageResponse;
import com.pms.pms.service.CommentService;
import com.pms.pms.service.IssueService;
import com.pms.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private IssueService issueService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest commentReq ,
    @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        Issue issue = issueService.getIssueById(commentReq.getIssueId());
        if (user==null){
            throw  new Exception("User is not Authenticated");

        }
        if (issue==null){
            throw  new Exception("Issue is not found");
        }
        Comment comment = commentService.createComment(
                user.getId(),
                commentReq.getIssueId(),
                commentReq.getContent()
        );

        return new ResponseEntity<>(comment , HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @RequestHeader("Authorization") String token ,
            @PathVariable Long commentId

    )throws Exception{
        User user = userService.findUserProfileByJwt(token);
        if (user==null){
            throw  new Exception("User is not Authenticated");
        }
        commentService.deleteComment(commentId , user.getId());
        return new ResponseEntity<>(new MessageResponse("Comment deleted"), HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(
            @RequestHeader("Authorization") String token ,
            @PathVariable Long issueId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        Issue issue = issueService.getIssueById(issueId);
        if (user==null){
            throw  new Exception("User is not Authenticated");
        }
        if (issue==null){

            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return new ResponseEntity<>(commentService.findCommentByIssueId(issueId) , HttpStatus.OK);
    }
}
