package visloukh.libra.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor


@Entity
@Table(name= "user_details")
public class UserDetailsEntity extends BaseEntity {

    @Column (nullable = false, length = 30)
    private String firstName;

    @Column (nullable = false , length = 30)
    private String lastName;

    @Column (nullable = false)
    private Date birthDay;

    @Column (length = 100)
    private String location;

    @Column (columnDefinition = "TEXT")
    private String aboutMe;

    @Column ()
    private String contactInfo;

    @OneToOne ()//fetch = FetchType.LAZY
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    public UserEntity user;
}
