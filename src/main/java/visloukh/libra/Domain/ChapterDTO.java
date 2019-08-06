package visloukh.libra.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor


public class ChapterDTO {


    private String ChapterName;
    private String Description;
    private String Path;

    private int id;
    private String status;
    private Date createDate;
    private Date redactDate;

}
