package handlers.requestforms;

import lombok.Data;

import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
@Data
public class FieldForm
{
    private String place;

    private List<String> pawns;

    public String getPlace()
    {
        return place;
    }

    public List<String> getPawns()
    {
        return pawns;
    }
}
