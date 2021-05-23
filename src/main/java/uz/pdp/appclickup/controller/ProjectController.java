package uz.pdp.appclickup.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.Project;
import uz.pdp.appclickup.entity.Space;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectDto;
import uz.pdp.appclickup.payload.SpaceDto;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.ProjectServiceImpl;
import uz.pdp.appclickup.service.SpaceServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    final ProjectServiceImpl projectService;
    final SpaceServiceImpl spaceService;

    public ProjectController(ProjectServiceImpl projectService, SpaceServiceImpl spaceService) {
        this.projectService = projectService;
        this.spaceService = spaceService;
    }

    @PostMapping
    public HttpEntity<?> addProject(@RequestBody ProjectDto projectDto) {
        ApiResponse apiResponse = projectService.addProject(projectDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editProject(@PathVariable UUID id,@RequestBody ProjectDto projectDto) {
        ApiResponse apiResponse = projectService.editProject(id,projectDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProject(@PathVariable UUID id) {
        ApiResponse apiResponse = projectService.deleteProject(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllProjects(){
        List<Project> projectList=projectService.getAllProjects();
        return ResponseEntity.ok(projectList);
    }


}
