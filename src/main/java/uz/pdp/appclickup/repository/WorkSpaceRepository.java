package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpace;

import java.util.UUID;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace, UUID> {

    boolean existsByOwnerIdAndName(User ownerId, String name);

}
