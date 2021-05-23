package uz.pdp.appclickup.service;


import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.entity.WorkSpaceUser;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.MemberDto;
import uz.pdp.appclickup.payload.WorkSpaceDto;

import java.util.List;
import java.util.UUID;

public interface WorkSpaceService {

    ApiResponse addWorkSpace(WorkSpaceDto workSpaceDto, User user);

    ApiResponse editWorkSpace(UUID id, WorkSpaceDto workSpaceDto,User user);

    ApiResponse changeOwnerOfWorkSpace(UUID id, UUID ownerId);

    ApiResponse deleteWorkSpace(UUID id);

    ApiResponse addOrEditOrDelete(UUID id, MemberDto memberDto);

    ApiResponse joinToWorkSpace(UUID id, User user);

    List<WorkSpace> getAllWorkSpace();


}
