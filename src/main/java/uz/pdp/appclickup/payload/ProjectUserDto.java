package uz.pdp.appclickup.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectUserDto {


    private UUID projectId;

    private UUID userId;

    private UUID taskPermission;

}
