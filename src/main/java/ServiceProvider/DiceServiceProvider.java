package ServiceProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
public class DiceServiceProvider
{
    private static final String DICE_SERVICE_NAME = "gerrit_duc_vsp_Services";

    private static final String DICE_SERVICE_ID = "232";

    public static YellopagesService provide()
    {
        return ServiceProvider.getService(DICE_SERVICE_ID);
    }

    public static YellopagesService provide(String local)
    {
        YellopagesService diceService = new YellopagesService();
        diceService.setUri("http://localhost:5678/");
        return diceService;

    }
}
