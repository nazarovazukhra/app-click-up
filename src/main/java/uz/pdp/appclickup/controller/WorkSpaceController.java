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
     * @param id           UUID ID
     * @param workSpaceDto WORK_SPACE_DTO
     * @return RESPONSE_ENTITY
     */
    @PutMapping("/{id}")
    public HttpEntity<?> editWorkSpace(@PathVariable UUID id, @RequestBody WorkSpaceDto workSpaceDto, @CurrentUser User user) {

        ApiResponse apiResponse = workSpaceService.editWorkSpace(id, workSpaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * @param id      UUID ID
     * @param ownerId UUID ID
     * @return RESPONSE_ENTITY
     */
    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?> changeOwnerOfWorkSpace(@PathVariable UUID id, @RequestParam UUID ownerId) {

        ApiResponse apiResponse = workSpaceService.changeOwnerOfWorkSpace(id, ownerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * ISHXONANI O'CHIRISH
     *
     * @param workSpaceId UUID ID
     * @param ownerId     UUID ID
     * @return RESPONSE_ENTITY
     */
    @DeleteMapping
    public HttpEntity<?> deleteWorkSpace(@RequestParam UUID workSpaceId, @RequestParam UUID ownerId) {

        ApiResponse apiResponse = workSpaceService.deleteWorkSpace(workSpaceId, ownerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * ISHXONA A'ZOLARINI O'ZGARTIRISH
     *
     * @param id        UUID ID
     * @param memberDto MEMBER_DTO
     * @return RESPONSE_ENTITY
     */
    @PostMapping("/addOrEditOrDelete/{id}")
    public HttpEntity<?> addOrEditOrDelete(@PathVariable UUID id, @RequestBody MemberDto memberDto) {

        ApiResponse apiResponse = workSpaceService.addOrEditOrDelete(id, memberDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * ISHXONAGA QO'SHILISH
     *
     * @param id   UUID ID
     * @param user @CURRENT_USER
     * @return RESPONSE_ENTITY
     */
    @PutMapping("/join")
    public HttpEntity<?> joinToWorkSpace(@RequestParam UUID id, @CurrentUser User user) {

        ApiResponse apiResponse = workSpaceService.joinToWorkSpace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * ISHXONALAR RO'YXATINI OLISH CLICK_UP OWNER I UCHUN
     *
     * @return LIST<WORK_SPACE>
     */
    @GetMapping
    public HttpEntity<?> getAllWorkSpace() {
        List<WorkSpace> workSpaceList = workSpaceService.getAllWorkSpace();
        return ResponseEntity.ok(workSpaceList);
    }

    /**
     * WORK_SPACE_OWNER GA TEGISHLI BARCHA WORK_SPACE LARNI OLISH
     *
     * @param ownerId UUID ID
     * @return LIST<WORK_SPACE>
     */
    @GetMapping("/byOwnerId")
    public HttpEntity<?> getAllWorkSpaceByOwnerId(@RequestParam UUID ownerId) {
        List<WorkSpace> workSpaceList = workSpaceService.getAllWorkSpaceByOwnerId(ownerId);
        return ResponseEntity.ok(workSpaceList);
    }

}
