package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.enums.WorkSpacePermissionName;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkSpacePermission extends AbsUUIDEntity {


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private WorkSpaceRole workSpaceRole;// O'RINBOSAR

    @Enumerated(EnumType.STRING)
    private WorkSpacePermissionName permission; // add member, remove member
}
