package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.entity.WorkSpaceUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkSpaceUserRepository extends JpaRepository<WorkSpaceUser, Long> {

    Optional<WorkSpaceUser> findByWorkSpaceIdAndUserId(WorkSpace workSpaceId, User userId);

    @Transactional
    @Modifying
    void deleteByWorkSpaceIdAndUserId(WorkSpace workSpaceId, User userId);

    @Query("select w  from  WorkSpaceUser w  where  w.workSpaceId.id=:workSpaceId and w.workSpaceRole.id='b7eab1d6-c52e-49d0-80ed-6e4fd8ee6ec4'")
    List<WorkSpaceUser> getAllMembersList(UUID workSpaceId);

    @Query("select wk  from  WorkSpaceUser wk  where  wk.workSpaceId.id=:id and wk.workSpaceRole.id='8a8c8dab-62cd-44d7-8b86-b4b2b47ead1a'")
    List<WorkSpaceUser> getAllGuestsList(UUID id);
}
