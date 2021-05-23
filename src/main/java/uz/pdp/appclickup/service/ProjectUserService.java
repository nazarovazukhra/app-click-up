package uz.pdp.appclickup.service;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectUserDto;

import java.util.UUID;


public interface ProjectUserService {

    ApiResponse addProjectUser(ProjectUserDto projectUserDto);

    ApiResponse editProjectUser(UUID id, ProjectUserDto projectUserDto);

    ApiResponse deleteProjectUser(UUID id);
}
