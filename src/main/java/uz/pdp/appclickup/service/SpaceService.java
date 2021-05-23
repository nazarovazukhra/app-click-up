package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.Space;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.SpaceDto;

import java.util.List;
import java.util.UUID;

public interface SpaceService {

    ApiResponse addSpace(SpaceDto spaceDto, User user);

    ApiResponse editSpace(UUID id,SpaceDto spaceDto, User user);

    List<Space> getSpaceList();

    ApiResponse deleteSpace(UUID id);
}
