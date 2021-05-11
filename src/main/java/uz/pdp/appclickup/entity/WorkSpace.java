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
public class WorkSpace extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User ownerId;

    @Column(nullable = false)
    private String initialLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment avatar;
}
