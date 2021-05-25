package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class WorkSpaceDto {

    private UUID id;

    private UUID ownerId;

    @NotNull
    private String name;

    @NotNull
    private String color;

    private UUID avatarId;



}
