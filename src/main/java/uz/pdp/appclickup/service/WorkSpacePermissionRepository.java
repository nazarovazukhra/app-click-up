package uz.pdp.appclickup.service;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.WorkSpacePermission;
import uz.pdp.appclickup.entity.WorkSpaceRole;

import java.util.Optional;
import java.util.UUID;

public interface WorkSpacePermissionRepository extends JpaRepository<WorkSpacePermission, UUID> {

    Optional<WorkSpacePermission> findWorkSpacePermissionByWorkSpaceRole(WorkSpaceRole workSpaceRole);
}
