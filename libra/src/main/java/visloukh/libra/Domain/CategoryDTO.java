package visloukh.libra.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor


public class CategoryDTO {

    private String name;

    private String description;

    private int id;
    private String status;
    private Date createDate;
    private Date redactDate;
}
