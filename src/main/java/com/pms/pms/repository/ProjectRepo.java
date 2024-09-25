package com.pms.pms.repository;

import com.pms.pms.model.Project;
import com.pms.pms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long > {
    List<Project> findByName(String name);

//    List<Project> findByOwner(User user);
//    @Query("SELECT p FROM Project p JOIN p.team t WHERE t=:user")
//    List<Project> findByTeam(@Param("user") User user);


    List<Project> findByNameContainingAndTeamContains(String name , User user );
    List<Project> findByNameContainingOrOwner(User user , User owner );
}
