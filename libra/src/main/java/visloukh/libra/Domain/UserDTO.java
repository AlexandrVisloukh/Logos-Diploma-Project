package visloukh.libra.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import visloukh.libra.Entity.RoleEntity;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class UserDTO {

    @NotNull
    @Size (min=2, max=50)
    private String name;

    @NotNull
    @Size (min=2, max=100)
    private String email;

    @NotNull
    @Size (min=2, max=100)
    private String password;

    @NotNull
    @Size (min=2, max=100)
    private String confirmPassword;
    @Size (max=10)
    private String sexType;
    private String image;

      public List<RoleDTO> roles;
    private int id;
    private String status;
    private Date createDate;
    private Date redactDate;

}
