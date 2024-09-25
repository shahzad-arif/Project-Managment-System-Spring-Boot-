package com.pms.pms.service;

import com.pms.pms.model.Chat;
import com.pms.pms.model.Project;
import com.pms.pms.model.User;
import com.pms.pms.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setName(project.getName());
        createdProject.setOwner(user);
        createdProject.setDescription(project.getDescription());
        createdProject.setCategory(project.getCategory());
        createdProject.setTags(project.getTags());
        createdProject.getTeam().add(user);

        Project savedProject = projectRepo.save(createdProject);


        Chat chat = new Chat();
        chat.setProject(savedProject);;
        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);
        return savedProject;
    }

    @Override
    public List<Project> getProjectsByTeam(User user, String tag, String category) throws Exception {
        List<Project> project = projectRepo.findByNameContainingOrOwner(user , user);
        if (category!=null) {
            project = project.stream().filter(project1 -> project1.getCategory().equals(category)).toList();
        }
        if (tag!=null) {
            project = project.stream().filter(project1 -> project1.getTags().contains(tag)).toList();
        }

        return project;
    }


    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> project = projectRepo.findById(projectId);
        if (project.isEmpty()) {
            throw new Exception("Project not found");
        }
        return project.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        //getChatByProjectId(projectId);
//       User user = userService.findUserById(userId);
//
//       if (user.getId() != userId) {
//           throw  new Exception("You cannot delete this project");
//       }
//
        projectRepo.deleteById(projectId);
    }


    @Override
    public Project updateProject(Long id, Project project) throws Exception {
        Project projectToBeUpdated = getProjectById(id);
        projectToBeUpdated.setName(project.getName());
        projectToBeUpdated.setDescription(project.getDescription());
        projectToBeUpdated.setCategory(project.getCategory());
        projectToBeUpdated.setTags(project.getTags());

        return projectRepo.save(projectToBeUpdated);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if (!project.getTeam().contains(user)) {
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);

        }
        projectRepo.save(project);

    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if (project.getTeam().contains(user)) {
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }

    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        Chat chat = project.getChat();
        return chat;
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        String partialName = "%"+keyword+"%";
        return projectRepo.findByNameContainingAndTeamContains(keyword, user);

    }
}
