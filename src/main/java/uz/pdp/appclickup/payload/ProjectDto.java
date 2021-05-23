package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ProjectDto {

    @NotNull
    private String name;

    @NotNull
    private UUID spaceId;

    @NotNull
    private String color;
}
