package main.java.models;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
public class Place
{
    private String id;
    private String board;
    private String name;
    private String broker;

    public Place(String board , String name, String broker)
    {
        this.board = board;
        this.name = name;
        this.broker = broker;
    }

    public String getId()
    {
        return id;
    }

    public String getBoard()
    {
        return board;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setBroker(String broker)
    {
        this.broker = broker;
    }

    public String getBroker()
    {
        return broker;
    }

    public void setId(Long id)
    {
        this.id = ""+id;
    }
}
