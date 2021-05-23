package uz.pdp.appclickup.service;


import uz.pdp.appclickup.entity.Project;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectDto;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    ApiResponse addProject(ProjectDto projectDto);

    ApiResponse editProject(UUID id, ProjectDto projectDto);

    ApiResponse deleteProject(UUID id);

    List<Project> getAllProjects();
}
