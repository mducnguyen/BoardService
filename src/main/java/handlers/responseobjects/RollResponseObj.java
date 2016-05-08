package main.java.handlers.responseobjects;

import lombok.Data;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
@Data
public class RollResponseObj
{
    private int number;

    public RollResponseObj(int number)
    {
        this.number = number;
    }
}
