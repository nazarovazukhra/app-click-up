package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace, UUID> {

    boolean existsByOwnerIdAndName(User ownerId, String name);

    boolean existsByIdAndOwnerId(UUID id, UUID ownerId);

    void deleteByIdAndOwnerId(UUID id, UUID ownerId);

    List<WorkSpace> findAllByOwnerId(UUID ownerId);
}
