package com.pms.pms.service;

import com.pms.pms.model.Chat;
import com.pms.pms.model.Project;
import com.pms.pms.model.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project , User user) throws Exception;
    List<Project> getProjectsByTeam(User user , String tag , String category) throws Exception;

    Project getProjectById(Long projectId) throws Exception;

    void deleteProject(Long projectId , Long userId) throws Exception;

    Project updateProject(Long id , Project project ) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;

    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatByProjectId(Long projectId) throws  Exception;

    List<Project> searchProjects (String keyword , User user) throws Exception;
}
