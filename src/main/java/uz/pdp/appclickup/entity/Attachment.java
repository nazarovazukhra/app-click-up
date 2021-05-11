package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Entity;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Attachment extends AbsUUIDEntity {

    private String name;           //  file uchun generate qilib beriladigan nom

    private String originalName;   //  user tomonidan qo'yilgan nom

    private Long size;

    private String contentType;

}
