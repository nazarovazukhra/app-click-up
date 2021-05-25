package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.Space;
import uz.pdp.appclickup.entity.WorkSpace;

import java.util.List;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space, UUID> {

    boolean existsByNameAndWorkSpaceId(String name, WorkSpace workSpaceId);

    List<Space> findAllByWorkSpaceId(UUID workSpaceId);

    Space findByIdAndWorkSpaceId(UUID spaceId, UUID workSpaceId);

    void deleteByIdAndWorkSpaceId(UUID spaceId, UUID workSpaceId);

    boolean existsByIdAndWorkSpaceId(UUID id, UUID workSpaceId);
}
