package uz.pdp.appclickup.service;


import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpaceUser;
import uz.pdp.appclickup.payload.MemberDto;
import uz.pdp.appclickup.payload.WorkSpaceDto;

import java.util.List;
import java.util.UUID;

public interface WorkSpaceUserService {

    List<WorkSpaceUser> getWorkSpaceMembersList(UUID id);

    List<WorkSpaceUser> getWorkSpaceGuestsList(UUID id);

    List<MemberDto> getMemberAndGuest(UUID id);

    List<WorkSpaceDto> getMyWorkSpaces(User user);
}
