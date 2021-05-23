package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.entity.WorkSpaceRole;

import java.util.Optional;
import java.util.UUID;

public interface WorkSpaceRoleRepository extends JpaRepository<WorkSpaceRole, UUID> {

    boolean existsByNameAndWorkSpaceId(String name, WorkSpace workSpaceId);
    Optional<WorkSpaceRole> findByWorkSpaceId(WorkSpace workSpaceId);
}
