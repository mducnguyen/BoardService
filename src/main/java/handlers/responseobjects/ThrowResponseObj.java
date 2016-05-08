package main.java.handlers.responseobjects;

import lombok.Data;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
@Data
public class ThrowResponseObj
{
    private RollResponseObj roll1;
    private RollResponseObj roll2;

    public ThrowResponseObj(RollResponseObj roll1, RollResponseObj roll2)
    {
        this.roll1 = roll1;
        this.roll2 = roll2;
    }
}
