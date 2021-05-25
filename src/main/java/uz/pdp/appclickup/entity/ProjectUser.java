package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.enums.WorkSpacePermissionName;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProjectUser extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Project projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @Enumerated(EnumType.STRING)
    private WorkSpacePermissionName taskPermission;

}
