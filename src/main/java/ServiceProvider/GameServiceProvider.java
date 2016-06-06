package ServiceProvider;

/**
 * @author DucNguyenMinh
 * @since 08/05/16
 */
public class GameServiceProvider
{
    private static final String Game_SERVICE_NAME ="gerrit_duc_vsp_Services";

    private static final String GAME_SERVICE_ID = "232";

    public static YellopagesService provide(){
        return ServiceProvider.getService(GAME_SERVICE_ID);
    }

    public static YellopagesService provide(String local)
    {
        YellopagesService gameService = new YellopagesService();
        gameService.setUri("http://localhost:6789/");
        return gameService;

    }
}
