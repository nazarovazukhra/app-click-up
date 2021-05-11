package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CheckListItem extends AbsUUIDEntity {

    @Column(nullable = false,unique = true)
    private String checkListItemName;

    @ManyToOne(fetch = FetchType.LAZY)
    private CheckList checkListId;

    private boolean resolved;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedUserId;
}
