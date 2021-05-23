package uz.pdp.appclickup.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectUserDto;
import uz.pdp.appclickup.service.ProjectUserService;

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


    @PutMapping("/{id}")
    public HttpEntity<?> editProjectUser(@PathVariable UUID id, @RequestBody ProjectUserDto projectUserDto) {
        ApiResponse apiResponse = projectUserService.editProjectUser(id, projectUserDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProjectUser(@PathVariable UUID id) {
        ApiResponse apiResponse = projectUserService.deleteProjectUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
}
