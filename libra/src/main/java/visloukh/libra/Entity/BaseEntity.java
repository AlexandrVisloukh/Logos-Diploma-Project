package visloukh.libra.Entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Data

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String status;

    @Column(columnDefinition = "DATETIME")
    private Date createDate;

    @Column(columnDefinition = "DATETIME")
    private Date redactDate;


}