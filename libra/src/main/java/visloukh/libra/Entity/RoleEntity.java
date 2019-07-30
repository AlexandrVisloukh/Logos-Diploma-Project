package visloukh.libra.Entity;

        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;
        import lombok.ToString;

        import javax.persistence.*;
        import java.util.ArrayList;
        import java.util.List;

@Getter
@Setter
@NoArgsConstructor


@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 30)
    private String role;

//      @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
//        private List<UserEntity> users = new ArrayList<>();

//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private List<UserEntity> users;
}

