package visloukh.libra.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class BookDTO {


    @NotNull
    private String title;
    private String description;
    private String fileLogo;
    private String fileName;
    private String author;

    @NotNull
    private CategoryDTO category;

    private UserDTO user;
    private List<ChapterDTO> chapters;

    private int id;
    private String status;
    private Date createDate;
    private Date redactDate;
}
