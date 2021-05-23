package uz.pdp.appclickup.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.WorkSpaceRoleDto;
import uz.pdp.appclickup.service.WorkSpaceRoleService;

@RestController
@RequestMapping("/api/workSpaceRole")
public class WorkSpaceRoleController {

    final WorkSpaceRoleService workSpaceRoleService;

    public WorkSpaceRoleController(WorkSpaceRoleService workSpaceRoleService) {
        this.workSpaceRoleService = workSpaceRoleService;
    }

    @PostMapping
    public HttpEntity<?> addWorkSpaceRole(@RequestBody WorkSpaceRoleDto workSpaceRoleDto){
        ApiResponse apiResponse=workSpaceRoleService.addWorkSpaceRole(workSpaceRoleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
}
