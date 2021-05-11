package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.template.AbsLongEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkSpaceUser extends AbsLongEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkSpace workSpaceId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkSpaceRole workSpaceRole;

    @Column(nullable = false)
    private Timestamp dateInvited;

    private Timestamp dateJoined;
}
