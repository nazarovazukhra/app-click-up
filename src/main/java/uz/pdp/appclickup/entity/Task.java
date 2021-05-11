package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task extends AbsUUIDEntity {

    @Column(nullable = false,unique = true)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Status statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Priority priorityId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task parentTaskId;

    private Date startedDate;

    private boolean startTimeHas;

    private Date dueDate;

    private boolean dueTimeHas;

    private long estimateTime;

    private Date archivedDate;


}
