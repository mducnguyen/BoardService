package handlers.requestforms;

import lombok.Data;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
@Data
public class MoveForm
{
    private int move;

    public int getMove()
    {
        return move;
    }
}
