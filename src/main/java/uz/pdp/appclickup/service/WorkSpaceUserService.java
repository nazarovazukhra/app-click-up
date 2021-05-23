package uz.pdp.appclickup.service;


import uz.pdp.appclickup.entity.WorkSpaceUser;

import java.util.List;
import java.util.UUID;

public interface WorkSpaceUserService {

    List<WorkSpaceUser> getWorkSpaceMembersList(UUID id);

    List<WorkSpaceUser> getWorkSpaceGuestsList(UUID id);
}
