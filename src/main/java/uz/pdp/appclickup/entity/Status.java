package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Status extends AbsUUIDEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Space spaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryId;

    private String color;

    private String type; // OPEN  CUSTOM  CLOSED
}
