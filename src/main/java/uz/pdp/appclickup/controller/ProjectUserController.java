package uz.pdp.appclickup.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.ProjectUser;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectUserDto;
import uz.pdp.appclickup.service.ProjectUserService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/projectUserController")
public class ProjectUserController {

    final ProjectUserService projectUserService;

    public ProjectUserController(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @PostMapping
    public HttpEntity<?> addProjectUser(@RequestBody ProjectUserDto projectUserDto) {
        ApiResponse apiResponse = projectUserService.addProjectUser(projectUserDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PutMapping
    public HttpEntity<?> editProjectUser(@RequestBody ProjectUserDto projectUserDto) {
        ApiResponse apiResponse = projectUserService.editProjectUser(projectUserDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProjectUser(@PathVariable UUID id) {
        ApiResponse apiResponse = projectUserService.deleteProjectUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @GetMapping
    public HttpEntity<?> getAll(@RequestParam UUID spaceId) {
        List<ProjectUser> projectUsers = projectUserService.getAll(spaceId);
        return ResponseEntity.ok(projectUsers);
    }

    @GetMapping
    public HttpEntity<?> getOne(@RequestParam UUID projectId, @RequestParam UUID userId) {
        ProjectUser projectUser = projectUserService.getOne(projectId, userId);
        return ResponseEntity.ok(projectUser);
    }
}
