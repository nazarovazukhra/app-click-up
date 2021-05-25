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

//    boolean existsByProjectIdAndUserIdAndIdNot(UUID projectId, UUID userId, UUID id);

    @Query(value = "select ProjectUser from ProjectUser join Space where Space.id=:spaceId")
    List<ProjectUser> getAllProjectUsersBySpaceId(UUID spaceId);
}
