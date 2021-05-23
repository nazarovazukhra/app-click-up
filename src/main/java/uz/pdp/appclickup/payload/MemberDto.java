package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.enums.ActionType;

import java.util.UUID;

@Data
public class MemberDto {

   private UUID id;

   private UUID roleId;

   private ActionType actionType;

}
