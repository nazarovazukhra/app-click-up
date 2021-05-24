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

    @GetMapping
    public HttpEntity<?> getSpaceList() {
        List<Space> spaceList = spaceService.getSpaceList();
        return ResponseEntity.ok(spaceList);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSpace(@PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.deleteSpace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);


    }


    @GetMapping("/{id}")
    public HttpEntity<?> getOneSpaceById(@PathVariable UUID id) {

        Space space = spaceService.getOneSpaceById(id);
        return ResponseEntity.status((space==null)?HttpStatus.NO_CONTENT:HttpStatus.OK).body(space);
    }
}
