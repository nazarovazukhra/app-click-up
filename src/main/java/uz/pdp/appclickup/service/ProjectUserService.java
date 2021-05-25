package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.ProjectUser;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectUserDto;

import java.util.List;
import java.util.UUID;


public interface ProjectUserService {

    ApiResponse addProjectUser(ProjectUserDto projectUserDto);

    ApiResponse editProjectUser(ProjectUserDto projectUserDto);

    ApiResponse deleteProjectUser(UUID id);

    List<ProjectUser> getAll(UUID spaceId);

    ProjectUser getOne(UUID projectId, UUID userId);
}
