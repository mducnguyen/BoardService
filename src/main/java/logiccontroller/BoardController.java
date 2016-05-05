package main.java.logiccontroller;

import java.util.List;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
public class BoardController
{
    private final Map<String, Integer> positions;
    private final List<Field> fields;

    public BoardController(List<Field> fields, Map<String,Integer> positions)
    {
        this.fields = fields;
        this.positions = positions;
    }
}
