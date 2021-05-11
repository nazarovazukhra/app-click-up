package uz.pdp.appclickup.entity.template;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class AbsUUIDEntity extends AbsMainEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;


}
