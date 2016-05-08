package main.java.ServiceProvider;

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
 * @since 02/05/16
 */
public class EventServiceProvider
{
    private static final String EVENT_SERVICE_NAME ="gerrit_duc_vsp_Services";

    private static final String EVENT_SERVICE_ID = "232";

    public static YellopagesService provide(){
        return ServiceProvider.getService(EVENT_SERVICE_ID);
    }


}
