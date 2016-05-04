package main.java.handlers.requestforms;

import lombok.Data;

import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
@Data
public class BoardForm
{
    private String game;

    private List<FieldForm> fields;

    private List<PositionForm> positions;

    public List<FieldForm> getFields()
    {
        return fields;
    }

    public List<PositionForm> getPositions()
    {
        return positions;
    }

    public String getGame()
    {
        return this.game;
    }
}
