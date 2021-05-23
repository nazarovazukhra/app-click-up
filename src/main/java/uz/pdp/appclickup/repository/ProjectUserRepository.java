package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.Project;
import uz.pdp.appclickup.entity.ProjectUser;
import uz.pdp.appclickup.entity.User;

import java.util.UUID;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {

    boolean existsByProjectIdAndUserId(Project projectId, User userId);
}
