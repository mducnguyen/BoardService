package handlers.requestforms;

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

    public String getGame()
    {
        return this.game;
    }
}
