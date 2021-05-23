package uz.pdp.appclickup.service;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.WorkSpaceRoleDto;

public interface WorkSpaceRoleService {

    ApiResponse addWorkSpaceRole(WorkSpaceRoleDto workSpaceRoleDto);
}
