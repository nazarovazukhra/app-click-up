package uz.pdp.appclickup.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.Space;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.SpaceDto;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.SpaceServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/space")
public class SpaceController {

    final SpaceServiceImpl spaceService;

    public SpaceController(SpaceServiceImpl spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping
    public HttpEntity<?> addSpace(@RequestBody SpaceDto spaceDto, @CurrentUser User user) {
        ApiResponse apiResponse = spaceService.addSpace(spaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editSpace(@PathVariable UUID id, @RequestBody SpaceDto spaceDto, @CurrentUser User user) {
        ApiResponse apiResponse = spaceService.editSpace(id, spaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/{worSpaceId}")
    public HttpEntity<?> getSpaceList(@PathVariable UUID worSpaceId) {
        List<Space> spaceList = spaceService.getSpaceList(worSpaceId);
        return ResponseEntity.ok(spaceList);
    }

    @GetMapping("/oneSpaceByWorkSpaceId")
    public HttpEntity<?> getOneSpaceByWorkSpaceId(@RequestParam UUID workSpaceId, @RequestParam UUID spaceId) {

        Space space = spaceService.getOneSpaceByWorkSpaceId(workSpaceId, spaceId);
        return ResponseEntity.status((space == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(space);
    }

    @DeleteMapping
    public HttpEntity<?> deleteSpace(@RequestParam UUID spaceId, @RequestParam UUID workSpaceId) {
        ApiResponse apiResponse = spaceService.deleteSpace(spaceId, workSpaceId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }

}
