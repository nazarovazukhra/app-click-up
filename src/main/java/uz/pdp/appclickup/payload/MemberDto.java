package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.enums.ActionType;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class MemberDto {

   private UUID id;

   private String fullName;

   private String email;

   private String roleName;

   private Timestamp lastActive;

   private UUID roleId;

   private ActionType actionType;

}
