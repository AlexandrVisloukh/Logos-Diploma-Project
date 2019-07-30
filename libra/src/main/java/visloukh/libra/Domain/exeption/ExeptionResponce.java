package visloukh.libra.Domain.exeption;

import lombok.Getter;

import java.util.Date;
@Getter

public class ExeptionResponce {

    private Date time;
    private String message;
    private String details;

    public ExeptionResponce(String message, String details) {
        this.message = message;
        this.details = details;
        this.time= new Date();
    }
}
