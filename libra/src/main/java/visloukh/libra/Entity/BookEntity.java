package visloukh.libra.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor


@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

//    @Column(nullable = false, unique = true)
//    private String fileName;

    @Column(unique = true)
    private String fileLogo;

    @Column(length = 50)
    private String author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private List<ChapterEntity> chapters;
}
