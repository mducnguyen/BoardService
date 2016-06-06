package handlers.responseobjects;

import lombok.Data;
import models.Place;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
@Data
public class PlaceResponseObj
{
    private String name;

    private String broker;

    private String id;

    public PlaceResponseObj(String boardId,Place place)
    {
        this.id = "/boards/"+boardId+"/places/"+place.getId();
        this.name = place.getName();
        this.broker = place.getBroker();
    }
}
