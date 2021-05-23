package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.WorkSpacePermission;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectUserDto {


    private UUID projectId;

    private UUID userId;

    private List<WorkSpacePermission> taskPermission;

}
