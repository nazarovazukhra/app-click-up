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
public class CategoryUser extends AbsUUIDEntity {

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @Enumerated(EnumType.STRING)
    private WorkSpacePermissionName taskPermission;
}
