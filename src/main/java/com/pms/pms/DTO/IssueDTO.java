package com.pms.pms.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pms.pms.model.Comment;
import com.pms.pms.model.Project;
import com.pms.pms.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private Long id;

    private String title;
    private String description;
    private String status;
    private Long projectId;
    private String priority;
    private LocalDate dueDate;
    private List<String> tags = new ArrayList<>();

    private User assignee;
    private Project project;
}
