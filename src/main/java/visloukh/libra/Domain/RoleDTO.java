package visloukh.libra.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class RoleDTO {

    private String role;

    private int id;
    private String status;
    private Date createDate;
    private Date redactDate;
}
