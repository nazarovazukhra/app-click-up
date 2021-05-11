package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.enums.WorkSpaceRoleName;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkSpaceRole extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private WorkSpace workSpaceId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private WorkSpaceRoleName extendsRole;
}
