package main.java.handlers.responseobjects;

import lombok.Data;
import main.java.models.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
@Data
public class PlacesResponseObj
{
    private List<String> places;

    public PlacesResponseObj(String boardId,List<Place> places)
    {
        List<String> placeList = new ArrayList<>();

        for (Place place: places
                ) {
            placeList.add("/boards/"+boardId+"/places/"+place.getId());
        }
        this.places = placeList;
    }
}
