package uz.pdp.appclickup.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.*;
import uz.pdp.appclickup.entity.enums.ActionType;
import uz.pdp.appclickup.entity.enums.WorkSpacePermissionName;
import uz.pdp.appclickup.entity.enums.WorkSpaceRoleName;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.MemberDto;
import uz.pdp.appclickup.payload.WorkSpaceDto;
import uz.pdp.appclickup.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {

    final WorkSpaceRepository workSpaceRepository;
    final AttachmentRepository attachmentRepository;
    final WorkSpaceUserRepository workSpaceUserRepository;
    final WorkSpaceRoleRepository workSpaceRoleRepository;
    final WorkSpacePermissionRepository workSpacePermissionRepository;
    final UserRepository userRepository;
    @Autowired
    JavaMailSender javaMailSender;

    public WorkSpaceServiceImpl(WorkSpaceRepository workSpaceRepository, AttachmentRepository attachmentRepository, WorkSpaceUserRepository workSpaceUserRepository, WorkSpaceRoleRepository workSpaceRoleRepository, WorkSpacePermissionRepository workSpacePermissionRepository, UserRepository userRepository) {
        this.workSpaceRepository = workSpaceRepository;
        this.attachmentRepository = attachmentRepository;
        this.workSpaceUserRepository = workSpaceUserRepository;
        this.workSpaceRoleRepository = workSpaceRoleRepository;
        this.workSpacePermissionRepository = workSpacePermissionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse addWorkSpace(WorkSpaceDto workSpaceDto, User user) {

        boolean exists = workSpaceRepository.existsByOwnerIdAndName(user, workSpaceDto.getName());
        if (exists)
            return new ApiResponse("In this user has such workspace with this  " + workSpaceDto.getName() + "  name", false);

        // WORKSPACE OCHDIK
        WorkSpace workSpace = new WorkSpace(
                workSpaceDto.getName(),
                workSpaceDto.getColor(),
                user,
                workSpaceDto.getAvatarId() == null ? null : attachmentRepository.findById(workSpaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("Attachment not found")));

        workSpaceRepository.save(workSpace);
        // WORKSPACE ROLE OCHDIK
        WorkSpaceRole ownerRole = workSpaceRoleRepository.save(new WorkSpaceRole(
                workSpace,
                WorkSpaceRoleName.ROLE_OWNER.name(),
                null));
        WorkSpaceRole adminRole = workSpaceRoleRepository.save(new WorkSpaceRole(
                workSpace,
                WorkSpaceRoleName.ROLE_ADMIN.name(),
                null));
        WorkSpaceRole memberRole = workSpaceRoleRepository.save(new WorkSpaceRole(
                workSpace,
                WorkSpaceRoleName.ROLE_MEMBER.name(),
                null));
        WorkSpaceRole guestRole = workSpaceRoleRepository.save(new WorkSpaceRole(
                workSpace,
                WorkSpaceRoleName.ROLE_GUEST.name(),
                null));

        // WORKSPACE PERMISSION OCHDIK
        WorkSpacePermissionName[] workSpacePermissionNames = WorkSpacePermissionName.values();
        List<WorkSpacePermission> workSpacePermissionList = new ArrayList<>();
        for (WorkSpacePermissionName workSpacePermissionName : workSpacePermissionNames) {
            WorkSpacePermission workSpacePermission = new WorkSpacePermission(
                    ownerRole, workSpacePermissionName
            );
            workSpacePermissionList.add(workSpacePermission);

            if (workSpacePermissionName.getWorkSpaceRoleNames().contains(WorkSpaceRoleName.ROLE_ADMIN)) {
                workSpacePermissionList.add(new WorkSpacePermission(adminRole, workSpacePermissionName));
            }
            if (workSpacePermissionName.getWorkSpaceRoleNames().contains(WorkSpaceRoleName.ROLE_MEMBER)) {
                workSpacePermissionList.add(new WorkSpacePermission(memberRole, workSpacePermissionName));
            }
            if (workSpacePermissionName.getWorkSpaceRoleNames().contains(WorkSpaceRoleName.ROLE_GUEST)) {
                workSpacePermissionList.add(new WorkSpacePermission(guestRole, workSpacePermissionName));
            }

        }


        workSpacePermissionRepository.saveAll(workSpacePermissionList);

        // WORKSPACE USER OCHDIK
        workSpaceUserRepository.save(new WorkSpaceUser(
                workSpace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())));

        return new ApiResponse("WorkSpace created", true);


    }

    @Override
    public ApiResponse editWorkSpace(UUID id, WorkSpaceDto workSpaceDto, User user) {
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(id);
        if (!optionalWorkSpace.isPresent())
            return new ApiResponse("Such workspace not found", false);

        WorkSpace editingWorkSpace = optionalWorkSpace.get();

        if (workSpaceRepository.existsByOwnerIdAndName(user, workSpaceDto.getName())) {
            editingWorkSpace.setColor(workSpaceDto.getColor());
            editingWorkSpace.setAvatar(workSpaceDto.getAvatarId() == null ? null : attachmentRepository.findById(workSpaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("Attachment not found")));
            workSpaceRepository.save(editingWorkSpace);
            return new ApiResponse("You can not change only workSpace name because  " + workSpaceDto.getName() + " already exists in this workSpace", true);
        }

        editingWorkSpace.setName(workSpaceDto.getName());
        editingWorkSpace.setColor(workSpaceDto.getColor());
        editingWorkSpace.setAvatar(workSpaceDto.getAvatarId() == null ? null : attachmentRepository.findById(workSpaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("Attachment not found")));
        workSpaceRepository.save(editingWorkSpace);

        return new ApiResponse("WorkSpace edited", false);


    }

    @Override
    public ApiResponse changeOwnerOfWorkSpace(UUID id, UUID ownerId) {
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(id);
        if (!optionalWorkSpace.isPresent())
            return new ApiResponse("Such workSpace not found", false);

        Optional<User> optionalUser = userRepository.findById(ownerId);
        if (!optionalUser.isPresent())
            return new ApiResponse("Such user not found", false);

        User user = optionalUser.get();

        WorkSpace workSpace = optionalWorkSpace.get();
        workSpace.setOwnerId(user);
        workSpaceRepository.save(workSpace);
        return new ApiResponse("WorkSpace owner changed", true);
    }

    @Override
    public ApiResponse deleteWorkSpace(UUID workSpaceId, UUID ownerId) {
        try {

            boolean exists = workSpaceRepository.existsByIdAndOwnerId(workSpaceId, ownerId);
            if (!exists)
                return new ApiResponse("Such workSpace not found in this user", false);

            workSpaceRepository.deleteByIdAndOwnerId(workSpaceId, ownerId);
            return new ApiResponse("WorkSpace deleted", true);
        } catch (Exception exception) {
            return new ApiResponse("Error ", false);
        }
    }

    @Override
    public ApiResponse addOrEditOrDelete(UUID id, MemberDto memberDto) {

        if (memberDto.getActionType().equals(ActionType.ADD)) {

            User user = userRepository.findById(memberDto.getId()).orElseThrow(() -> new ResourceNotFoundException("id"));
            WorkSpaceUser workSpaceUser = new WorkSpaceUser(
                    workSpaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id")),
                    user,
                    workSpaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    new Timestamp(System.currentTimeMillis()),
                    null
            );

            workSpaceUserRepository.save(workSpaceUser);
            joiningEmail(user.getEmail());

        } else if (memberDto.getActionType().equals(ActionType.EDIT)) {

            Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(id);
            if (!optionalWorkSpace.isPresent())
                return new ApiResponse("Such workSpace not found", false);
            WorkSpace workSpace = optionalWorkSpace.get();


            Optional<User> optionalUser = userRepository.findById(memberDto.getId());
            if (!optionalUser.isPresent())
                return new ApiResponse("Such user not found", false);
            User user = optionalUser.get();


            WorkSpaceUser workSpaceUser = workSpaceUserRepository.findByWorkSpaceIdAndUserId(workSpace, user).orElseGet(WorkSpaceUser::new);
            WorkSpaceRole workSpaceRole = workSpaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id"));
            workSpaceUser.setWorkSpaceRole(workSpaceRole);
            workSpaceUserRepository.save(workSpaceUser);
        } else if (memberDto.getActionType().equals(ActionType.REMOVE)) {
            workSpaceUserRepository.deleteByWorkSpaceIdAndUserId(workSpaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("workSpace not found")), userRepository.findById(memberDto.getId()).orElseThrow(() -> new ResourceNotFoundException("user not found")));
            return new ApiResponse("Successfully user deleted from this role", true);

        }
        return new ApiResponse("Successfully", true);

    }

    public Boolean joiningEmail(String sendingEmail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("test@Pdp.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Join to WorkSpace");
            mailMessage.setText("<a href='http://localhost:8080/http://localhost:8080/api/workSpace/join'>JOIN</a>");
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ApiResponse joinToWorkSpace(UUID id, User user) {
        Optional<WorkSpaceUser> optionalWorkSpaceUser = workSpaceUserRepository.findByWorkSpaceIdAndUserId(workSpaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id ")), userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("id")));
        if (optionalWorkSpaceUser.isPresent()) {
            WorkSpaceUser workSpaceUser = optionalWorkSpaceUser.get();
            workSpaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workSpaceUserRepository.save(workSpaceUser);
            return new ApiResponse("User joined", true);
        }
        return new ApiResponse("You were not invited to workSpace", false);

    }

    @Override
    public List<WorkSpace> getAllWorkSpace() {
        return workSpaceRepository.findAll();
    }

    @Override
    public List<WorkSpace> getAllWorkSpaceByOwnerId(UUID ownerId) {

        return workSpaceRepository.findAllByOwnerId(ownerId);
    }
}

