package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.entity.WorkSpaceRole;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkSpaceRoleRepository extends JpaRepository<WorkSpaceRole, UUID> {

    boolean existsByNameAndWorkSpaceId(String name, WorkSpace workSpaceId);

    List<WorkSpaceRole> findAllByWorkSpaceId(WorkSpace workSpaceId);

    Optional<WorkSpaceRole> findByIdAndWorkSpaceId(UUID id, WorkSpace workSpaceId);

    void deleteByIdAndWorkSpaceId(UUID id, WorkSpace workSpaceId);
}
