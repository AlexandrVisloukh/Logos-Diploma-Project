package visloukh.libra.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import visloukh.libra.Entity.UserEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDetailsDTO {

    @NotNull
    @Length(max=30)
    private String firstName;
    @NotNull
    @Length(max=30)
    private String lastName;
    @NotNull

    private Date birthDay;
    private String location;
    private String aboutMe;
    private String contactInfo;
    private UserDTO user;

    private String status;
    private Date createDate;
    private Date redactDate;
}
