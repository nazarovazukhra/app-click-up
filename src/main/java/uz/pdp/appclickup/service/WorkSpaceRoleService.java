package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.WorkSpaceRole;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.WorkSpaceRoleDto;

import java.util.List;
import java.util.UUID;

public interface WorkSpaceRoleService {

    ApiResponse addWorkSpaceRole(WorkSpaceRoleDto workSpaceRoleDto);

    List<WorkSpaceRole> getAll();

    WorkSpaceRole getOneById(UUID id);

    ApiResponse deleteById(UUID workSpaceId, UUID workSpaceRoleId);

    List<WorkSpaceRole> getAllWorkSpaceRoleByWorkSpaceId(UUID workSpaceId);

    WorkSpaceRole getOneWorkSpaceRoleByIdAndWorkSpaceId(UUID workSpaceId, UUID workSpaceRoleId);

}
