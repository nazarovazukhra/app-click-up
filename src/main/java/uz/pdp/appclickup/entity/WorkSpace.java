package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name","owner_id_id"})})
public class WorkSpace extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User ownerId;

    @Column(nullable = false)
    private String initialLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment avatar;


    @PrePersist
    @PreUpdate
    void setInitialLetter(){
        this.initialLetter=name.substring(0,1);
    }

    public WorkSpace(String name, String color, User ownerId, Attachment avatar) {
        this.name = name;
        this.color = color;
        this.ownerId = ownerId;
        this.avatar = avatar;
    }
}
