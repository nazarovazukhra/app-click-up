package uz.pdp.appclickup.service;

import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.entity.WorkSpaceUser;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.MemberDto;
import uz.pdp.appclickup.payload.WorkSpaceDto;
import uz.pdp.appclickup.repository.WorkSpaceRepository;
import uz.pdp.appclickup.repository.WorkSpaceUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkSpaceUserServiceImpl implements WorkSpaceUserService {

    final WorkSpaceUserRepository workSpaceUserRepository;
    final WorkSpaceRepository workSpaceRepository;

    public WorkSpaceUserServiceImpl(WorkSpaceUserRepository workSpaceUserRepository, WorkSpaceRepository workSpaceRepository) {
        this.workSpaceUserRepository = workSpaceUserRepository;
        this.workSpaceRepository = workSpaceRepository;
    }

    @Override
    public List<WorkSpaceUser> getWorkSpaceMembersList(UUID id) {

        return workSpaceUserRepository.getAllMembersList(id);
    }

    @Override
    public List<WorkSpaceUser> getWorkSpaceGuestsList(UUID id) {
        return workSpaceUserRepository.getAllGuestsList(id);
    }


    @Override
    public List<MemberDto> getMemberAndGuest(UUID id) {

        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(id);
        if (!optionalWorkSpace.isPresent())
            return null;
        WorkSpace workSpace = optionalWorkSpace.get();


        List<WorkSpaceUser> workSpaceUserList = workSpaceUserRepository.findAllByWorkSpaceId(workSpace);

//        List<MemberDto> members = new ArrayList<>();
//
//        for (WorkSpaceUser workSpaceUser : workSpaceUserList) {
//            MemberDto memberDto = mapWorkSpaceUserToMemberDto(workSpaceUser);
//            members.add(memberDto);
//        }
//        return members;
//
//        List<MemberDto> memberDtoList = workSpaceUserList.stream().map(workSpaceUser -> mapWorkSpaceUserToMemberDto(workSpaceUser)).collect(Collectors.toList());
//        return memberDtoList;

        return workSpaceUserList.stream().map(this::mapWorkSpaceUserToMemberDto).collect(Collectors.toList());


    }


    @Override
    public List<WorkSpaceDto> getMyWorkSpaces(User user) {

        List<WorkSpaceUser> workSpaceUsers = workSpaceUserRepository.findAllByUserId(user);

        return workSpaceUsers.stream().map(this::mapWorkSpaceUSerToWorkSpaceDto).collect(Collectors.toList());
    }

    public WorkSpaceDto mapWorkSpaceUSerToWorkSpaceDto(WorkSpaceUser workSpaceUser){

        WorkSpaceDto workSpaceDto=new WorkSpaceDto();
        workSpaceDto.setId(workSpaceUser.getWorkSpaceId().getId());
        workSpaceDto.setName(workSpaceUser.getWorkSpaceId().getName());
        workSpaceDto.setInitialLetter(workSpaceUser.getWorkSpaceId().getInitialLetter());
        workSpaceDto.setColor(workSpaceUser.getWorkSpaceId().getColor());
        workSpaceDto.setAvatarId(workSpaceUser.getWorkSpaceId().getAvatar().getId());
        workSpaceDto.setOwnerId(workSpaceUser.getWorkSpaceId().getOwnerId().getId());

        return workSpaceDto;
    }
    public MemberDto mapWorkSpaceUserToMemberDto(WorkSpaceUser workSpaceUser) {

        MemberDto memberDto = new MemberDto();

        memberDto.setId(workSpaceUser.getUserId().getId());
        memberDto.setFullName(workSpaceUser.getUserId().getFullName());
        memberDto.setEmail(workSpaceUser.getUserId().getEmail());
        memberDto.setRoleName(workSpaceUser.getUserId().getSystemRole().name());
        memberDto.setLastActive(workSpaceUser.getUserId().getLastActiveTime());

        return memberDto;
    }
}
