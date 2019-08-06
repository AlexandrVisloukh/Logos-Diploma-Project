package visloukh.libra.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor


@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    private String sexType;

//    @Column(nullable = false)
//    private Date registerDate;

//    @Column(name = "is_deleted",
//            columnDefinition = "BOOLEAN DEFAULT FALSE")
//    private boolean isDeleted;
    @Column(unique = true)
    private String image;


    @ManyToMany  (fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public List<RoleEntity> roles = new ArrayList<>();


}
