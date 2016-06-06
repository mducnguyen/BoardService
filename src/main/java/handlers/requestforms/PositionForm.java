package handlers.requestforms;

import lombok.Data;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
@Data
public class PositionForm
{
    private String pawn;

    public int getPosition()
    {
        return position;
    }

    public String getPawn()
    {
        return pawn;
    }

    private int position;
}
