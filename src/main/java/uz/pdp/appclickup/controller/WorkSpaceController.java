package uz.pdp.appclickup.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.MemberDto;
import uz.pdp.appclickup.payload.WorkSpaceDto;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.WorkSpaceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workSpace")
public class WorkSpaceController {

    final WorkSpaceService workSpaceService;

    public WorkSpaceController(WorkSpaceService workSpaceService) {
        this.workSpaceService = workSpaceService;
    }


    @PostMapping
    public HttpEntity<?> addWorkSpace(@Valid @RequestBody WorkSpaceDto workSpaceDto, @CurrentUser User user) {

        ApiResponse apiResponse = workSpaceService.addWorkSpace(workSpaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * NAME, COLOR, AVATAR O'ZGARISHI MUMKIN
     *
     * @param id
     * @param workSpaceDto
     * @return
     */
    @PutMapping("/{id}")
    public HttpEntity<?> editWorkSpace(@PathVariable UUID id, @RequestBody WorkSpaceDto workSpaceDto, @CurrentUser User user,Integer editingField) {

        //  editingField=1  workSpace name is changing
        //  editingField=2  workSpace color is changing
        //  editingField=3  workSpace avatar is changing

        ApiResponse apiResponse = workSpaceService.editWorkSpace(id, workSpaceDto, user,editingField);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * @param id
     * @param ownerId
     * @return
     */
    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?> changeOwnerOfWorkSpace(@PathVariable UUID id, @RequestParam UUID ownerId) {

        ApiResponse apiResponse = workSpaceService.changeOwnerOfWorkSpace(id, ownerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * ISHXONANI O'CHIRISH
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkSpace(@PathVariable UUID id) {

        ApiResponse apiResponse = workSpaceService.deleteWorkSpace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * ISHXONA A'ZOLARINI O'ZGARTIRISH
     *
     * @param id
     * @param memberDto
     * @return
     */
    @PostMapping("/addOrEditOrDelete/{id}")
    public HttpEntity<?> addOrEditOrDelete(@PathVariable UUID id, @RequestBody MemberDto memberDto) {

        ApiResponse apiResponse = workSpaceService.addOrEditOrDelete(id, memberDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * ISHXONAGA QO'SHILISH
     *
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/join")
    public HttpEntity<?> joinToWorkSpace(@RequestParam UUID id, @CurrentUser User user) {

        ApiResponse apiResponse = workSpaceService.joinToWorkSpace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * ISHXONALAR RO'YXATINI OLISH
     *
     * @return
     */
    @GetMapping
    public HttpEntity<?> getAllWorkSpace() {
        List<WorkSpace> workSpaceList = workSpaceService.getAllWorkSpace();
        return ResponseEntity.ok(workSpaceList);
    }

}
