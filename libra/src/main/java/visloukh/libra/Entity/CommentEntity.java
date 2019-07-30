package visloukh.libra.Entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor


@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column()
    private int parent;



    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
