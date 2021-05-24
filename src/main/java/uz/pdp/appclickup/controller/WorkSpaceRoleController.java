package uz.pdp.appclickup.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.WorkSpaceRole;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.WorkSpaceRoleDto;
import uz.pdp.appclickup.service.WorkSpaceRoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workSpaceRole")
public class WorkSpaceRoleController {

    final WorkSpaceRoleService workSpaceRoleService;

    public WorkSpaceRoleController(WorkSpaceRoleService workSpaceRoleService) {
        this.workSpaceRoleService = workSpaceRoleService;
    }

    @PostMapping
    public HttpEntity<?> addWorkSpaceRole(@RequestBody WorkSpaceRoleDto workSpaceRoleDto) {
        ApiResponse apiResponse = workSpaceRoleService.addWorkSpaceRole(workSpaceRoleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    // THIS METHOD FOR CLICK_UP OWNER
    @GetMapping
    public HttpEntity<?> getAll() {
        List<WorkSpaceRole> workSpaceRoles = workSpaceRoleService.getAll();
        return ResponseEntity.ok(workSpaceRoles);
    }

    // THIS METHOD FOR CLICK_UP OWNER
    @GetMapping("/{id}")
    public HttpEntity<?> getOneById(@PathVariable UUID id) {
        WorkSpaceRole workSpaceRole = workSpaceRoleService.getOneById(id);
        return ResponseEntity.ok(workSpaceRole);
    }

    // GETTING LIST<WORK_SPACE_ROLE> BY WORK_SPACE_ID
    @GetMapping("/{workSpaceId}")
    public HttpEntity<?> getAllWorkSpaceRoleByWorkSpaceId(@PathVariable UUID workSpaceId) {
        List<WorkSpaceRole> workSpaceRoleList = workSpaceRoleService.getAllWorkSpaceRoleByWorkSpaceId(workSpaceId);
        return ResponseEntity.status((workSpaceRoleList == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(workSpaceRoleList);
    }

    // GETTING ONE WORK_SPACE_ROLE BY WORK_SPACE_ID
    @GetMapping
    public HttpEntity<?> getOneWorkSpaceRoleByIdAndWorkSpaceId(@RequestParam UUID workSpaceId, @RequestParam UUID workSpaceRoleId) {
        WorkSpaceRole workSpaceRole = workSpaceRoleService.getOneWorkSpaceRoleByIdAndWorkSpaceId(workSpaceId, workSpaceRoleId);
        return ResponseEntity.status((workSpaceRole == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(workSpaceRole);
    }


    @DeleteMapping
    public HttpEntity<?> deleteById(@RequestParam UUID workSpaceId, @RequestParam UUID workSpaceRoleId) {
        ApiResponse apiResponse = workSpaceRoleService.deleteById(workSpaceId, workSpaceRoleId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);


    }
}
