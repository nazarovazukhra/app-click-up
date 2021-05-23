package uz.pdp.appclickup.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class WorkSpaceRoleDto {

    private UUID workSpaceId;

    private String roleName;

    private String extendsRole;
}
