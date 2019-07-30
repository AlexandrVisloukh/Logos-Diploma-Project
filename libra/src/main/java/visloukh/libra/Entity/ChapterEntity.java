package visloukh.libra.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "chapters")
public class ChapterEntity extends BaseEntity {

    @Column (length = 50, nullable = false)
    String chapterName;

    @Column (length = 100)
    String description;

    @Column (length = 50, unique = true)
    String path;


}
