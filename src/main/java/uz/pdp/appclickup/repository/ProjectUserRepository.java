package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appclickup.entity.Project;
import uz.pdp.appclickup.entity.ProjectUser;
import uz.pdp.appclickup.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {

    boolean existsByProjectIdAndUserId(Project projectId, User userId);

    Optional<ProjectUser> findByProjectIdAndUserId(UUID projectId, UUID userId);

    boolean existsByProjectIdAndUserIdAndIdNot(UUID projectId, UUID userId);

    @Query(value = "select ProjectUser from  ProjectUser join WorkSpace where WorkSpace.id=:workSpaceId and WorkSpace.ownerId.id=:ownerId ")
    List<ProjectUser> getProjectUsers(UUID workSpaceId, UUID ownerId);


    @Query(value = "select ProjectUser from ProjectUser join WorkSpace where ProjectUser.id=:projectUserId and WorkSpace.id=:workSpaceId")
    ProjectUser getOneProjectUser(UUID workSpaceId, UUID projectUserId);
}
