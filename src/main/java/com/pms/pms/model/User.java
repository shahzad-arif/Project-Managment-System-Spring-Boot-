package com.pms.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "assignee" , cascade = CascadeType.ALL)
    private List<Issue> assignedIssues = new ArrayList<>();
    private int projectSize;




}
