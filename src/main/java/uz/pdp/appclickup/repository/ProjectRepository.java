package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.Project;
import uz.pdp.appclickup.entity.Space;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    boolean existsByNameAndSpaceId(String name, Space spaceId);
}
