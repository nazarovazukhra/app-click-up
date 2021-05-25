package uz.pdp.appclickup.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.entity.WorkSpaceUser;
import uz.pdp.appclickup.payload.MemberDto;
import uz.pdp.appclickup.payload.WorkSpaceDto;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.WorkSpaceUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workSpaceUser")
public class WorkSpaceUserController {

    final WorkSpaceUserService workSpaceUserService;

    public WorkSpaceUserController(WorkSpaceUserService workSpaceUserService) {
        this.workSpaceUserService = workSpaceUserService;
    }

    @GetMapping("/getWorkSpaceMembers/{id}")
    public HttpEntity<?> getWorkSpaceMembers(@PathVariable UUID id) {
        List<WorkSpaceUser> workSpaceMembersList = workSpaceUserService.getWorkSpaceMembersList(id);
        return ResponseEntity.ok(workSpaceMembersList);

    }

    @GetMapping("/getWorkSpaceGuests/{id}")
    public HttpEntity<?> getWorkSpaceGuests(@PathVariable UUID id) {
        List<WorkSpaceUser> workSpaceGuests = workSpaceUserService.getWorkSpaceGuestsList(id);
        return ResponseEntity.ok(workSpaceGuests);

    }

    // LESSON 10 CLASSWORK METHODS
    @GetMapping("/member/{id}")
    public HttpEntity<?> getMemberAndGuest(@PathVariable UUID id) {
        List<MemberDto> workSpaceUserList = workSpaceUserService.getMemberAndGuest(id);
    }


    @GetMapping
    public HttpEntity<?>getMyWorkSpaces(@CurrentUser User user){
        List<WorkSpaceDto>workSpaces=workSpaceUserService.getMyWorkSpaces(user);

    }
}
